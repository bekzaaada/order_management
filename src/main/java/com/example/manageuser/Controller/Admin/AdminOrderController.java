package com.example.manageuser.Controller.Admin;

import com.example.manageuser.Components.ParamUtil;
import com.example.manageuser.Data.CategoryJpa;
import com.example.manageuser.Data.OrderJpa;
import com.example.manageuser.Data.OrderJpaCustom;
import com.example.manageuser.Data.UserJpa;
import com.example.manageuser.Service.admin.AdminOrderService;
import com.example.manageuser.Utils.PojUtil;
import com.example.manageuser.Utils.validation.NoValidate;
import com.example.manageuser.Utils.validation.Validate;
import com.example.manageuser.bean.Result;
import com.example.manageuser.bean.ValidationResult;
import com.example.manageuser.bean.CustomQueryParam;
import com.example.manageuser.bean.Page;
import com.example.manageuser.bean.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/order/")
public class AdminOrderController {
    private static final Logger log = LoggerFactory.getLogger(AdminOrderController.class);
    @Autowired
    private AdminOrderService adminOrderService;
    @Autowired
    private UserJpa userJpa;
    @Autowired
    private OrderJpa orderJpa;
    @Autowired
    OrderJpaCustom orderJpaCustom;
    @Autowired
    CategoryJpa categoryJpa;

    @RequestMapping("/order_list")
    @ResponseBody
    public Result order_list(@RequestBody Map<String,Object> params, HttpServletRequest request)
    {
        Validate validate=new NoValidate();
        ValidationResult vr= validate.validate(params,request,null);

        if(vr.isSuccess())
        {
            try {
                var c = CustomQueryParam.parse(params);
                return new Result(0, "Success!", orderJpaCustom.queryAllOrdersPage(c));

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
    @RequestMapping("/get_category_list")
    @ResponseBody
    public Result get_category_list(@RequestBody Map<String,Object> params, HttpServletRequest request)
    {
        Validate validate=new NoValidate();
        ValidationResult vr= validate.validate(params,request,null);

        if(vr.isSuccess())
        {
            try {
                var c = CustomQueryParam.parse(params);
                return new Result(0, "Success!",  categoryJpa.findAll());

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
}


