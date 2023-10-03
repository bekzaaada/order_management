package com.example.manageuser.Controller;

import com.example.manageuser.Components.ParamUtil;
import com.example.manageuser.Service.ClientAuthService;
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


import org.slf4j.event.Level;
import org.slf4j.helpers.CheckReturnValue;
import org.slf4j.spi.DefaultLoggingEventBuilder;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log =  LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private ClientAuthService clientAuthService;

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody HashMap<String, Object> params, HttpServletRequest request){
        Validate validate = new NoValidate();
        ValidationResult vr = validate.validate(params,request,null);
        if(vr.isSuccess()){
            try{
                return clientAuthService.login(params, request);

            }catch (ServiceException ex){
//                log.error(ParamUtil.request_info_has_log("",ex));
                return new Result(-1, null, null);

            }
        }
        return new Result(-1, "validation error", vr);

    }
    @PostMapping("/get_user_info")
    @ResponseBody
    public Result get_user_info(Map<String, Object> params, HttpServletRequest request) {
        Validate validate=new NoValidate();
        ValidationResult vr= validate.validate(params,request,null);

        if(vr.isSuccess()) {
            try {
                return   clientAuthService.get_user_info(params, request);
            }catch (ServiceException ex)
            {
                return new Result(0, null, null);
            }
            catch (Exception e)
            {
                return new Result(-3, e.getMessage(), null);
            }
        }
        return new Result(-1,"validation error!",vr);
    }
    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestBody Map<String,Object> params, HttpServletRequest request) {
        Validate validate=new NoValidate();
        ValidationResult vr= validate.validate(params,request,null);

        if(vr.isSuccess()) {
            try {
                return   clientAuthService.register(params, request);
            }catch (ServiceException ex)
            {
                return new Result(0, null, null);
            }
            catch (Exception e)
            {
                return new Result(0, null, null);
            }
        }

        return new Result(-1,"validation error!",vr);
    }




}