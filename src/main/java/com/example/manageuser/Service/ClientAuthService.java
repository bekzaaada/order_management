package com.example.manageuser.Service;

import com.example.manageuser.bean.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.service.spi.ServiceException;

import java.util.HashMap;
import java.util.Map;

public interface ClientAuthService {
    Result login(Map<String,Object> params, HttpServletRequest request) throws ServiceException;
    Result update_password( Map<String,Object> params, HttpServletRequest request)throws ServiceException;
    Result reset_password( Map<String,Object> params, HttpServletRequest request)throws ServiceException;
    Result refresh_token( Map<String,Object> params, HttpServletRequest request)throws ServiceException;

    Result register(Map<String, Object> params, HttpServletRequest request) throws ServiceException;
    Result get_user_info(Map<String, Object> params, HttpServletRequest request) throws ServiceException;
    Result set_firebase_token(Map<String, Object> params, HttpServletRequest request) throws ServiceException;


    Result get_client_info(Map<String, Object> params, HttpServletRequest request)throws ServiceException;
}
