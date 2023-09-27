package com.example.manageuser.Controller;

import com.example.manageuser.Components.ParamUtil;
import com.example.manageuser.Entity.Order;
import com.example.manageuser.Service.OrderService;
import com.example.manageuser.Utils.PojUtil;
import com.example.manageuser.Utils.validation.NoValidate;
import com.example.manageuser.Utils.validation.Validate;
import com.example.manageuser.bean.Result;
import com.example.manageuser.bean.ValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order_add")
    @ResponseBody
    public Result order_add(@RequestBody Map<String,Object> params, HttpServletRequest request)
    {
        Validate validate=new NoValidate();
        ValidationResult vr= validate.validate(params,request,null);

        if(vr.isSuccess())
        {
            try {

                return orderService.order_add(params,request);

            }catch (ServiceException ex)
            {
                log.error(ParamUtil.request_info_has_log(""),ex);
                return new Result(-1,ex.getMessage(),null);
            }
            catch (Exception e)
            {
                log.error(ParamUtil.request_info_has_log(""),e);
                return new Result(-3,e.getMessage(), PojUtil.getStackTrace(e));
            }

        }

        return new Result(-1,"validation error!",vr);
    }
    @RequestMapping("/order_update")
    @ResponseBody
    public Result order_update(@RequestBody Map<String,Object> params, HttpServletRequest request)
    {
        Validate validate=new NoValidate();
        ValidationResult vr= validate.validate(params,request,null);

        if(vr.isSuccess())
        {
            try {

                return orderService.order_update(params,request);

            }catch (ServiceException ex)
            {
                log.error(ParamUtil.request_info_has_log(""),ex);
                return new Result(-1,ex.getMessage(),null);
            }
            catch (Exception e)
            {
                log.error(ParamUtil.request_info_has_log(""),e);
                return new Result(-3,e.getMessage(),PojUtil.getStackTrace(e));
            }
        }

        return new Result(-1,"validation error!",vr);
    }

    //request_params,request_attr,jwt_payload,Result
    @RequestMapping("/order_delete")
    @ResponseBody
    public String order_delete(@RequestBody Map<String,Object> params, HttpServletRequest request)
    {
        Validate validate=new NoValidate();
        ValidationResult vr= validate.validate(params,request,null);

        if(vr.isSuccess())
        {
            try {

                return orderService.order_delete(params,request);

            }catch (ServiceException ex)
            {
                log.error(ParamUtil.request_info_has_log(""),ex);
                return "-1";
            }
            catch (Exception e)
            {
                log.error(ParamUtil.request_info_has_log(""),e);
                return "-1";
            }


        }
        return "-1";
    }

    @RequestMapping("/order_get")
    @ResponseBody
    public Result order_get(@RequestBody Map<String,Object> params, HttpServletRequest request)
    {
        Validate validate=new NoValidate();
        ValidationResult vr= validate.validate(params,request,null);

        if(vr.isSuccess())
        {
            try {
                return orderService.order_get(params,request);

            }catch (ServiceException ex)
            {
                log.error(ParamUtil.request_info_has_log(""),ex);
                return new Result(-1,ex.getMessage(),null);
            }
            catch (Exception e)
            {
                log.error(ParamUtil.request_info_has_log(""),e);
                return new Result(-3,e.getMessage(),PojUtil.getStackTrace(e));
            }
        }

        return new Result(-1,"validation error!",vr);
    }
    @RequestMapping("/order_get_list")
    @ResponseBody
    public Result order_get_list(@RequestBody Map<String,Object> params, HttpServletRequest request)
    {
        Validate validate=new NoValidate();
        ValidationResult vr= validate.validate(params,request,null);

        if(vr.isSuccess())
        {
            try {
                return new Result(0, "", orderService.order_get_list(params,request));

            }catch (ServiceException ex)
            {
                log.error(ParamUtil.request_info_has_log(""),ex);
                return new Result(-1, "", null);
            }
            catch (Exception e)
            {
                log.error(ParamUtil.request_info_has_log(""),e);
                return new Result(-3,e.getMessage(),PojUtil.getStackTrace(e));
            }
        }

        return new Result(-1,"validation error!",vr);
    }



}
