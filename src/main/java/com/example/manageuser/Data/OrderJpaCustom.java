package com.example.manageuser.Data;

import com.example.manageuser.Entity.Category;
import com.example.manageuser.Entity.Order;
import com.example.manageuser.Entity.User;
import com.example.manageuser.bean.CustomQueryParam;
import com.example.manageuser.bean.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
public interface OrderJpaCustom {
    List<Category> getCategoryList();
    Page queryAllOrdersPage(CustomQueryParam qp);
    Page queryAllOrderCategoryPage(CustomQueryParam qp);
    List<Order> getAllOrders(List<String> cids);
    int myOrderCount(String uid, String param_name, String param_value);
    Map<String,Object> get_order_by_category(String category);
    long countOrder(String osid, String statusTimer, int redAmoHour);

}

