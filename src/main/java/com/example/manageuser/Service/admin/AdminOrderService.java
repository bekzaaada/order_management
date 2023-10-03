package com.example.manageuser.Service.admin;

import com.example.manageuser.bean.Page;
import com.example.manageuser.bean.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.service.spi.ServiceException;

import java.util.Map;

public interface AdminOrderService {
    Page order_list(Map<String,Object> params, HttpServletRequest request) throws ServiceException;

    Page order_category_list_page(Map<String, Object> params, HttpServletRequest request) throws ServiceException;

//    Result category_list_all(Map<String, Object> params, HttpServletRequest requestq) throws ServiceException;

    Result consolidation_get_by_id(Map<String, Object> params, HttpServletRequest request) throws ServiceException;
    Result consolidation_remove_orders(Map<String, Object> params, HttpServletRequest request) throws ServiceException;
    Page consolidation_orders_page(Map<String, Object> params, HttpServletRequest request) throws ServiceException;
    Page consolidation_page(Map<String, Object> params, HttpServletRequest request) throws ServiceException;
    Result orders_count_by_id(Map<String, Object> params, HttpServletRequest request) throws ServiceException;
    Result get_order_by_id(Map<String, Object> params, HttpServletRequest request)  throws ServiceException;



}
