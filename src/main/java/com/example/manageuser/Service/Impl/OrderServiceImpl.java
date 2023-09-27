package com.example.manageuser.Service.Impl;

import com.example.manageuser.Components.ParamUtil;
import com.example.manageuser.Data.OrderJpa;
import com.example.manageuser.Entity.Order;
import com.example.manageuser.Service.OrderService;
import com.example.manageuser.bean.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderJpa orderJpa;
    @Autowired
    public OrderServiceImpl(OrderJpa orderJpa) {
        this.orderJpa = orderJpa;
    }

    @Override
    public Result order_add(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
//        String oid = ParamUtil.get_string(params, "oid", false);
        String title = ParamUtil.get_string(params, "title", false);
        int price = ParamUtil.get_int(params, "price", false);

        Order order = new Order();
        order.setOid(UUID.randomUUID().toString());
        order.setTitle(title);
        order.setPrice(price);
        order.setCreateTime(new Date());
        orderJpa.save(order);

        return new Result(0, "Ok", Map.of("oid", order.getOid()));
    }

    @Override
    public Result order_update(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        String oid = ParamUtil.get_string(params, "oid", false);
        String title = ParamUtil.get_string(params, "title", false);
        int price = ParamUtil.get_int(params, "price", false);
        Order order = orderJpa.findOrderByOidAndDeleted(oid, 0);
        if(order == null)
            return new Result(-1, "No such order", Map.of("oid", order.getOid()));
        order.setOid(oid);
        order.setTitle(title);
        order.setPrice(price);
        order.setUpdateTime(new Date());
        orderJpa.save(order);
        return new Result(0, "Ok", Map.of("oid", order.getOid()));

    }

    @Override
    public String order_delete(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        String oid = ParamUtil.get_string(params, "oid", false);
        Order order = orderJpa.findOrderByOid(oid);
        if(order == null)
            return"No such order";
        order.setDeleted(1);
        orderJpa.save(order);

        return "Successfully deleted";
    }

    @Override
    public Result order_get(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        String oid = ParamUtil.get_string(params, "oid", false);
        Order order = orderJpa.findOrderByOidAndDeleted(oid, 0);
        if(order == null)
            return new Result(-1, "No such order", Map.of("oid", order.getOid()));
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        return new Result(0, "Ok", result);
    }
    @Override
    public List<Order> order_get_list(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        return orderJpa.findAll();
    }

}
