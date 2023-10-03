package com.example.manageuser.Utils;

import com.example.manageuser.bean.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;

import java.util.*;

public class SqlUtil {
    public static Page queryPage(EntityManager entityManager, String from_sql, String short_sql, String long_sql, Map<String,Object> params, Map<String,String> param2column, CustomQueryParam qp, QueryCallback callback) {

        Map<String,Object> keyword_map=   qp.getKeyWordMap();
        Map<String,Object> filter_map=   qp.getFilter();
        boolean is_simple= qp.getIsSimple() != 0;
        int start=   qp.getStart();
        int length=   qp.getLength();
        List<CustomSort> sorts=   qp.getSort();
        List<CustomTime> times=   qp.getTime();
        Map<String,Object> query_params=new HashMap<>();
        StringBuilder where_sql=new StringBuilder();
        StringBuilder order_sql=new StringBuilder();
        String query_sql="SELECT %s  "+from_sql;
        String count_sql="SELECT count(*)  "+from_sql;
        if(start<0)
            start=0;
        if(length==0)
            length=20;
        if(length<0)
            length=200;
        if(length>200)
            length=200;

        filter_map.forEach((key,value)->{
            if(value!=null && !"".equals(value.toString().trim()))
            {
                where_sql.append(" and "+param2column.get(key)+"="+":"+key);
                query_params.put(key,value);
            }

        });

        keyword_map.forEach((key,value)->{
            if(value!=null && !"".equals(value.toString().trim()))
            {
                where_sql.append(" and "+param2column.get(key)+" like "+":"+key);
                query_params.put(key,"%"+value+"%");
            }

        });

        times.forEach(customTime -> {
            //unix_timestamp,DATE_ADD(FROM_UNIXTIME(0), INTERVAL -1234567 SECOND)
            String time_name=param2column.get(customTime.getKey());
            if(customTime.getMin()!=0)
                where_sql.append(" and "+time_name+" >= '"+ PojUtil.timestamp_sec_to_str(customTime.getMin())+"'");
            if(customTime.getMax()!=0)
                where_sql.append(" and "+time_name+" <= '"+PojUtil.timestamp_sec_to_str(customTime.getMax())+"'");

        });

        if(sorts.size()>0)
        {
            order_sql.append(" order by");

            for(int i=0;i<sorts.size();++i)
            {
                CustomSort customSort=sorts.get(i);
                String col_name=param2column.get(customSort.getKey());
                if(i==sorts.size()-1)
                {
                    order_sql.append(" "+col_name+" "+((customSort.getIsAsc()==0)?"desc":"asc"));
                }
                else
                {
                    order_sql.append(" "+col_name+" "+((customSort.getIsAsc()==0)?"desc,":"asc,"));
                }

            }

        }

        if(is_simple)
            query_sql=String.format(query_sql,short_sql);
        else
            query_sql=String.format(query_sql,long_sql);

        query_sql+=" "+where_sql+order_sql;
        count_sql+=" "+where_sql;


        query_sql+=" limit :index,:count";
        Query q_query= entityManager.createNativeQuery(query_sql, Tuple.class);
        Query q_count= entityManager.createNativeQuery(count_sql);

        params.forEach((key,value)->{
            q_query.setParameter(key,value);
            q_count.setParameter(key,value);
        });

        query_params.forEach((key,value)->{
            q_query.setParameter(key,value);
            q_count.setParameter(key,value);
        });

        q_query.setParameter("index",start);
        q_query.setParameter("count",length);

        Page page = new Page();
        page.setRows(convertTuplesToMap(q_query.getResultList(),callback));
        page.setRecord_count(Integer.parseInt(q_count.getSingleResult().toString()));

        return page;

    }


    public static List<Map<String,Object>> queryList(EntityManager entityManager, String from_sql, String short_sql, String long_sql, Map<String,Object> params, Map<String,String> param2column, CustomQueryParam qp, QueryCallback callback) {

        Map<String,Object> keyword_map=   qp.getKeyWordMap();
        Map<String,Object> filter_map=   qp.getFilter();
        List<String> groupby = qp.getGroupby();
        boolean is_simple= qp.getIsSimple() != 0;
        int start=   qp.getStart();
        int length=   qp.getLength();
        List<CustomSort> sorts=   qp.getSort();
        List<CustomTime> times=   qp.getTime();
        Map<String,Object> query_params=new HashMap<>();
        StringBuilder where_sql=new StringBuilder();
        StringBuilder order_sql=new StringBuilder();
        StringBuilder groupby_sql=new StringBuilder();
        String query_sql="SELECT %s  "+from_sql;


        filter_map.forEach((key,value)->{
            if(value!=null && !"".equals(value.toString().trim()))
            {
                where_sql.append(" and "+param2column.get(key)+"="+":"+key);
                query_params.put(key,value);
            }

        });

        keyword_map.forEach((key,value)->{
            if(value!=null && !"".equals(value.toString().trim()))
            {
                where_sql.append(" and "+param2column.get(key)+" like "+":"+key);
                query_params.put(key,"%"+value+"%");
            }

        });

        times.forEach(customTime -> {
            //unix_timestamp,DATE_ADD(FROM_UNIXTIME(0), INTERVAL -1234567 SECOND)
            String time_name=param2column.get(customTime.getKey());
            if(customTime.getMin()!=0)
                where_sql.append(" and "+time_name+" >= '"+ PojUtil.timestamp_sec_to_str(customTime.getMin())+"'");
            if(customTime.getMax()!=0)
                where_sql.append(" and "+time_name+" <= '"+PojUtil.timestamp_sec_to_str(customTime.getMax())+"'");

        });

        if(groupby.size()>0) {
            groupby_sql.append(" group by");
            for (int i = 0; i < groupby.size(); i++) {
                groupby_sql.append(" " + groupby.get(i));
                if (i != groupby.size() - 1)
                    groupby_sql.append(",");
            }
        }

        if(sorts.size()>0)
        {
            order_sql.append(" order by");

            for(int i=0;i<sorts.size();++i)
            {
                CustomSort customSort=sorts.get(i);
                String col_name=param2column.get(customSort.getKey());
                if(i==sorts.size()-1)
                {
                    order_sql.append(" "+col_name+" "+((customSort.getIsAsc()==0)?"desc":"asc"));
                }
                else
                {
                    order_sql.append(" "+col_name+" "+((customSort.getIsAsc()==0)?"desc,":"asc,"));
                }

            }

        }

        if(is_simple)
            query_sql=String.format(query_sql,short_sql);
        else
            query_sql=String.format(query_sql,long_sql);

        query_sql+=" "+where_sql+groupby_sql+order_sql;

        Query q_query= entityManager.createNativeQuery(query_sql, Tuple.class);

        params.forEach((key,value)->{
            q_query.setParameter(key,value);
        });

        query_params.forEach((key,value)->{
            q_query.setParameter(key,value);
        });
        return convertTuplesToMap(q_query.getResultList(),callback);

    }






    public static Map<String,List<Map<String,Object>>> queryMapInsideList(EntityManager entityManager, String sql,String key,Map<String,Object> params, QueryCallback callback)
    {
        if(key==null)
            throw new RuntimeException("Query Map Key Can't be null!");
        Query query= entityManager.createNativeQuery(sql, Tuple.class);
        params.forEach((param_key,value)->{
            query.setParameter(param_key,value);
        });
        return convertToMapInsideList(query.getResultList(),key,callback);
    }

    public static Map<String,Map<String,Object>> queryMapInsideMap(EntityManager entityManager, String sql,String key,Map<String,Object> params, QueryCallback callback)
    {
        if(key==null)
            throw new RuntimeException("Query Map Key Can't be null!");
        Query query= entityManager.createNativeQuery(sql, Tuple.class);
        params.forEach((param_key,value)->{
            query.setParameter(param_key,value);
        });
        return convertToMapInsideMap(query.getResultList(),key,callback);
    }

    public static Map<String, Map<String,Object>> convertToMapInsideMap(List<Tuple> tuples,String mapkey, QueryCallback callback)
    {
        Map<String,Map<String,Object>> result=new HashMap<>();
        if(tuples==null)
            return result;
        for (Tuple single : tuples) {
            Map<String, Object> tempMap = new HashMap<>();
            for (TupleElement<?> key : single.getElements()) {

                Object vl= single.get(key);
                if(vl==null)
                    tempMap.put(key.getAlias(), null);
                else if(vl instanceof Date)
                {

                    tempMap.put(key.getAlias(), ((Date)vl).getTime()/1000);
                }

                else
                    tempMap.put(key.getAlias(), single.get(key));
            }

            boolean is_add=false;
            if(callback!=null)
            {
                boolean res=  callback.callback(tempMap);
                if(res)
                    is_add=true;

            }
            else
                is_add=true;

            if(is_add)
            {
                Object key_value_obj= tempMap.get(mapkey);
                if(key_value_obj==null)
                    key_value_obj="null";
                String key_value_string=key_value_obj.toString();
                result.put(key_value_string,tempMap);

            }
        }

        return result;

    }

    public static Map<String, List<Map<String,Object>>> convertToMapInsideList(List<Tuple> tuples,String mapkey, QueryCallback callback)
    {
        Map<String,List<Map<String,Object>>> result=new HashMap<>();
        if(tuples==null)
            return result;
        for (Tuple single : tuples) {
            Map<String, Object> tempMap = new HashMap<>();
            for (TupleElement<?> key : single.getElements()) {

                Object vl= single.get(key);
                if(vl==null)
                    tempMap.put(key.getAlias(), null);
                else if(vl instanceof Date)
                {

                    tempMap.put(key.getAlias(), ((Date)vl).getTime()/1000);
                }

                else
                    tempMap.put(key.getAlias(), single.get(key));
            }

            boolean is_add=false;
            if(callback!=null)
            {
                boolean res=  callback.callback(tempMap);
                if(res)
                    is_add=true;

            }
            else
                is_add=true;

            if(is_add)
            {
                Object key_value_obj= tempMap.get(mapkey);
                if(key_value_obj==null)
                    key_value_obj="null";
                String key_value_string=key_value_obj.toString();
                List<Map<String,Object>> result_value=(List<Map<String, Object>>) result.get(key_value_string);
                if(result_value==null)
                {
                    result_value=new ArrayList<>();
                    result.put(key_value_string,result_value);
                }

                result_value.add(tempMap);
            }
        }

        return result;

    }

    public static List<Map<String, Object>> convertTuplesToMap(List<Tuple> tuples, QueryCallback callback) {
        List<Map<String, Object>> result = new ArrayList<>();
        if(tuples==null)
            return result;
        for (Tuple single : tuples) {
            Map<String, Object> tempMap = new HashMap<>();
            for (TupleElement<?> key : single.getElements()) {
                Object vl= single.get(key);
                if(vl==null)
                    tempMap.put(key.getAlias(), null);
                else if(vl instanceof Date)
                {

                    tempMap.put(key.getAlias(), ((Date)vl).getTime()/1000);
                }

                else
                    tempMap.put(key.getAlias(), single.get(key));
            }
            if(callback!=null)
            {
                boolean res=  callback.callback(tempMap);
                if(res)
                    result.add(tempMap);
            }
            else
                result.add(tempMap);
        }
        return result;
    }

    public static List<Map<String, Object>> queryListMap(EntityManager entityManager, String sql,Map<String,Object> params)
    {
        Query query= entityManager.createNativeQuery(sql, Tuple.class);
        params.forEach((param_key,value)->{
            query.setParameter(param_key,value);
        });
        return convertTuplesToMap(query.getResultList(),null);

    }

    public static List<Map<String,Object>> queryList2(EntityManager entityManager, String from_sql, String short_sql, String long_sql, Map<String,Object> params, Map<String,String> param2column, CustomQueryParam qp, QueryCallback callback) {

        Map<String,Object> keyword_map=   qp.getKeyWordMap();
        Map<String,Object> filter_map=   qp.getFilter();
        List<String> groupby = qp.getGroupby();
        boolean is_simple= qp.getIsSimple() != 0;
        int start=   qp.getStart();
        int length=   qp.getLength();
        List<CustomSort> sorts=   qp.getSort();
        List<CustomTime> times=   qp.getTime();
        Map<String,Object> query_params=new HashMap<>();
        StringBuilder where_sql=new StringBuilder();
        StringBuilder order_sql=new StringBuilder();
        StringBuilder groupby_sql=new StringBuilder();
        String query_sql="SELECT %s  "+from_sql;


        filter_map.forEach((key,value)->{
            if(value!=null && !"".equals(value.toString().trim()))
            {
                where_sql.append(" and "+param2column.get(key)+"="+":"+key);
                query_params.put(key,value);
            }

        });

        keyword_map.forEach((key,value)->{
            if(value!=null && !"".equals(value.toString().trim()))
            {
                where_sql.append(" and "+param2column.get(key)+" like "+":"+key);
                query_params.put(key,"%"+value+"%");
            }

        });

        times.forEach(customTime -> {
            //unix_timestamp,DATE_ADD(FROM_UNIXTIME(0), INTERVAL -1234567 SECOND)
            String time_name=param2column.get(customTime.getKey());
            if(customTime.getMin()!=0)
                where_sql.append(" and "+time_name+" >= '"+ PojUtil.timestamp_sec_to_str(customTime.getMin())+"'");
            if(customTime.getMax()!=0)
                where_sql.append(" and "+time_name+" <= '"+PojUtil.timestamp_sec_to_str(customTime.getMax())+"'");

        });

        if(groupby.size()>0) {
            groupby_sql.append(" group by");
            for (int i = 0; i < groupby.size(); i++) {
                groupby_sql.append(" " + groupby.get(i));
                if (i != groupby.size() - 1)
                    groupby_sql.append(",");
            }
        }

        if(sorts.size()>0)
        {
            order_sql.append(" order by");

            for(int i=0;i<sorts.size();++i)
            {
                CustomSort customSort=sorts.get(i);
                String col_name=param2column.get(customSort.getKey());
                if(i==sorts.size()-1)
                {
                    order_sql.append(" "+col_name+" "+((customSort.getIsAsc()==0)?"desc":"asc"));
                }
                else
                {
                    order_sql.append(" "+col_name+" "+((customSort.getIsAsc()==0)?"desc,":"asc,"));
                }

            }

        }

        if(is_simple)
            query_sql=String.format(query_sql,short_sql);
        else
            query_sql=String.format(query_sql,long_sql);

        query_sql+=" "+where_sql+groupby_sql+order_sql;

        query_sql+=" limit :index,:count";
        Query q_query= entityManager.createNativeQuery(query_sql, Tuple.class);

        params.forEach((key,value)->{
            q_query.setParameter(key,value);
        });

        query_params.forEach((key,value)->{
            q_query.setParameter(key,value);
        });
        q_query.setParameter("index",start);
        q_query.setParameter("count",length);

        return convertTuplesToMap(q_query.getResultList(),callback);
    }

}
