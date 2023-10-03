package com.example.manageuser.Data;

import com.example.manageuser.Entity.Category;
import com.example.manageuser.Entity.Order;
import com.example.manageuser.Utils.SqlUtil;
import com.example.manageuser.bean.CustomQueryParam;
import com.example.manageuser.bean.Page;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderJpaCustomImpl implements OrderJpaCustom{

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private  OrderJpa orderJpa;
    @Autowired
    private CategoryJpa categoryJpa;

    @Override
    public List<Category> getCategoryList() {
        return null;
    }

    @Override
    public Page queryAllOrdersPage(CustomQueryParam qp) {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> param2column = new HashMap<>();
        param2column.put("category", "category_cid");

        String from_sql = "FROM orders o";
        String short_sql = "* ";
        String long_sql = "* ";

        Page page = SqlUtil.queryPage(entityManager,
                from_sql,
                long_sql,
                short_sql,
                params,
                param2column,
                qp, (map) -> {
            return true;
                });

        return page;
    }

    @Override
    public Page queryAllOrderCategoryPage(CustomQueryParam qp) {
        return null;
    }

    @Override
    public List<Order> getAllOrders(List<String> cids) {
        return null;
    }

    @Override
    public int myOrderCount(String uid, String param_name, String param_value) {
        return 0;
    }

    @Override
    public Map<String, Object> get_order_by_category(String category) {
        return null;
    }

    @Override
    public long countOrder(String osid, String statusTimer, int redAmoHour) {
        return 0;
    }
}
