package com.example.manageuser.Service;

import com.example.manageuser.Entity.Order;
import com.example.manageuser.bean.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
public interface OrderService {
    Result order_add(Map<String,Object> params, HttpServletRequest request) throws ServiceException;
    Result order_update( Map<String,Object> params, HttpServletRequest request) throws ServiceException;
    String order_delete( Map<String,Object> params, HttpServletRequest request) throws ServiceException;
    Result order_get( Map<String,Object> params, HttpServletRequest request) throws ServiceException;
    List<Order> order_get_list(Map<String,Object> params, HttpServletRequest request) throws ServiceException;
}
