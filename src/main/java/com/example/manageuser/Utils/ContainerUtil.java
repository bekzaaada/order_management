package com.example.manageuser.Utils;

import com.example.manageuser.bean.MapBindCallback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ContainerUtil {


    public static List<Map<String,Object>> list_map_filter(List<Map<String,Object>> old,Filter filter)
    {
        List<Map<String,Object>> result=new ArrayList<>();
        for (Map<String,Object> item: old) {
            Map<String,Object> copyted=new HashMap<>();
            copyted.putAll(item);
            if(filter.operation(copyted))
                result.add(copyted);

        }
        return result;
    }

    public static List<Map<String,Object>> list_obj_filter(Object obj,Filter filter)
    {
        List<Map<String,Object>> result=new ArrayList<>();
        List<Map<String,Object>> old=new ObjectMapper().convertValue(obj,List.class);
        for (Map<String,Object> item: old) {
            Map<String,Object> copyted=new HashMap<>();
            copyted.putAll(item);
            if(filter.operation(copyted))
                result.add(copyted);

        }
        return result;
    }

    public static Map<String,Object> object2map(Object obj, MapBindCallback mapBindCallback) {
        Map<String,Object> result_map = new HashMap<>();
        Map<String,Object> parse_map = new ObjectMapper().convertValue(obj,Map.class);
        if(mapBindCallback!=null) {
            parse_map.forEach((key,value)->{
                mapBindCallback.callback(result_map,key,value);
            });
            return result_map;
        }
        return parse_map;
    }

    public static List<Map<String,Object>> list2map(List list)
    {
        List<Map<String,Object>> parse_map= new ObjectMapper().convertValue(list, new TypeReference<List<Map<String, Object>>>() {
        });
        return parse_map;
    }


    public static Map<String,Object> long2timestamp(Object obje,String[] keys)
    {
        if(obje==null)
            return null;
        Map<String,Object> map=new ObjectMapper().convertValue(obje,Map.class);
        if(keys!=null)
        {

            for(int i=0;i<keys.length;++i)
            {
                String key=keys[i];
                Object obj=map.get(key);
                if(obj!=null)
                {
                    map.put(key,(Long)obj/1000);
                }
            }
        }
        return map;
    }

    public static List<Map<String,Object>> long2timestamp(List objlist,String[] keys)
    {
        List<Map<String,Object>> map_list=new ObjectMapper().convertValue(objlist,List.class);
        if(keys!=null)
        {
            map_list.forEach((map)->{

                for(int i=0;i<keys.length;++i)
                {
                    String key=keys[i];
                    Object obj=map.get(key);
                    if(obj!=null)
                    {
                        map.put(key,(Long)obj/1000);
                    }
                }

            });

        }

        return map_list;
    }

    public static Object get_map_value(Map<String,Object> map,String key)
    {
        if(map==null || key==null || "".equals(key))
            return null;
        Map<String,Object> root=map;
        String find=key;
        String next_key="";
        while(true)
        {
            int index=find.indexOf('>');
            if(index!=-1)
            {
                String tmp_find=find.substring(0,index);
                next_key=find.substring(index+1);
                root=(Map<String, Object>) root.get(tmp_find);
                find=next_key;
                if(root==null)
                    return null;
            }
            else
            {
                return root.get(find);
            }
        }

    }

    public static List<String> getListItems(List<Map<String,Object>> list,String item)
    {
        List<String> r=new ArrayList<>();
        for(Map<String,Object> map:list)
        {
            String val=(String) map.get(item);
            if(val!=null)
                r.add(val);
        }

        return r;
    }

    public static String object2jsonStr(Object obj)
    {
        try {
            return  new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <O> O jsonStr2Object(String json,Class<O> clazz)
    {
        try {
            return  new ObjectMapper().readValue(json,clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public static <O> O map2object(Map<String,Object> map,Class<O> clazz)
    {
        return new ObjectMapper().convertValue(map,clazz);
    }


    public static <O> O map2objectAllowUnknownProps(Map<String,Object> map,Class<O> clazz)
    {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return objectMapper.convertValue(map,clazz);
    }


    public static void removeMapProps(Map<String,Object> map,String... keys)
    {
        for(var key:keys)
        {
            map.remove(key);
        }
    }

}
