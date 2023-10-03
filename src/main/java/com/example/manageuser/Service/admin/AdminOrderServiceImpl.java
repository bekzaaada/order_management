package com.example.manageuser.Service.admin;

import com.example.manageuser.Components.ParamUtil;
import com.example.manageuser.Data.CategoryJpa;
import com.example.manageuser.Data.OrderJpa;
import com.example.manageuser.Data.OrderJpaCustom;
import com.example.manageuser.Data.UserJpa;
import com.example.manageuser.Entity.Category;
import com.example.manageuser.bean.CustomQueryParam;
import com.example.manageuser.bean.Page;
import com.example.manageuser.bean.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {
    @Autowired
    private UserJpa userJpa;
    @Autowired
    private OrderJpa orderJpa;
    @Autowired
    private CategoryJpa categoryJpa;
    @Autowired
    private OrderJpaCustom orderJpaCustom;


    @Override
    public Page order_list(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        CustomQueryParam customQueryParam = null;
        try {
            customQueryParam = CustomQueryParam.parse(params);
        } catch (Exception e) {
            throw new ServiceException("query param format error:" + e.getMessage());
        }

        return orderJpaCustom.queryAllOrdersPage(customQueryParam);
    }
    @Override
    public Page order_category_list_page(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        CustomQueryParam customQueryParam = null;
        try {
            customQueryParam = CustomQueryParam.parse(params);
        } catch (Exception e) {
            throw new ServiceException("query param format error:" + e.getMessage());
        }

        return orderJpaCustom.queryAllOrderCategoryPage(customQueryParam);
    }
//    @Override
//    public Result category_list_all(Map<String, Object> params, HttpServletRequest requestq) throws ServiceException {
//        String cid = ParamUtil.get_string(params, "cid", false);
//        Category category = categoryJpa.findAllByCid();
//        if(category == null){
//            return new Result(-2," ",null);
//        }
//        Map<String, Object> data = new HashMap<>();
//        data.put("title", category.getTitle());
//        data.put("cid", category.getCid());
//        return new Result(0, "OK", data);
//    }


    @Override
    public Result consolidation_get_by_id(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public Result consolidation_remove_orders(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public Page consolidation_orders_page(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public Page consolidation_page(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public Result orders_count_by_id(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public Result get_order_by_id(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        return null;
    }
}