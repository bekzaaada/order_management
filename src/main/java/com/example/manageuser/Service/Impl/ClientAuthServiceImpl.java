package com.example.manageuser.Service.Impl;

import com.example.manageuser.Components.JWTUtils;
import com.example.manageuser.Components.JpaUtil;
import com.example.manageuser.Components.ParamUtil;
import com.example.manageuser.Data.UserJpa;
import com.example.manageuser.Utils.PojUtil;
import com.example.manageuser.Entity.User;
import com.example.manageuser.Service.ClientAuthService;
import com.example.manageuser.bean.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ClientAuthServiceImpl implements ClientAuthService {
    @Value("${jwt.refresh_token_expire_second}")
    private int refresh_expire_second;
//    @Autowired
//    private CacheManager cacheManager;
//    @Autowired
//    private GlobalCache globalCache;

    @Autowired
    private JWTUtils jwtUtils;
    @Value("${jwt.token_expire_second}")
    private int token_expire_second;
    @Autowired
    private JpaUtil jpaUtil;
    @Autowired
    private UserJpa userJpa;


    @Override
    public Result login(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        String username = ParamUtil.get_string(params, "username", false);
        String password = ParamUtil.get_string(params, "password", false);
        User user = userJpa.findByUsername(username);
        if(user.getPassword().equals(PojUtil.sha256password_hash(password))){
            user.setLastLogin(new Date());
            String refresh_token = PojUtil.generateRandomString(64);
            int type = user.getType();
            String token = jwtUtils.getToken(Map.of("uid", user.getUid(), "user_type", (type == 0) ? "client" : "admin"));
            user.setLastLogin(new Date());
            user.setRefreshtokenuser(refresh_token);
            user.setExpireSecond(refresh_expire_second);
            userJpa.save(user);
            return new Result(0, "", Map.of(
                    "uid", user.getUid(),
                    "token", token,
                    "refresh_token", refresh_token,
                    "token_expire_second", token_expire_second,
                    "user_info", jpaUtil.getUserInfo(user)
            ));
        }
        return null;
    }

    @Override
    public Result update_password(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public Result reset_password(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public Result refresh_token(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        String refresh_token = ParamUtil.get_string(params, "refresh_token", false);
        User user = userJpa.findByRefreshtokenuser(refresh_token);
        if(user != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(user.getLastLogin());
            calendar.add(Calendar.SECOND, user.getExpireSecond());
            Date end_time = calendar.getTime();
            Date cur_time = new Date();
            if(cur_time.compareTo(end_time)<0){
                int type = user.getType();
                String token = jwtUtils.getToken(Map.of("uid", user.getUid(), "user_type", (type == 0) ? "client" : "admin"));
                return new Result(0,"OK", Map.of("uid", user.getUid(), "token", token, "refresh_token", refresh_token, "token_expire_second", token_expire_second ));
            } else{
                return new Result(-2, "null", null);
            }
        }else{
            return new Result(-2, "null", null);
        }
    }
    @Override
    public Result register(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        String username = ParamUtil.get_string(params, "username", false);
        String password = ParamUtil.get_string(params, "password", false);

        String refresh_token = PojUtil.generateRandomString(64);
        User user = new User();
        user.setUsername(username);
        user.setPassword(PojUtil.sha256password_hash(password));
        user.setCreateTime(new Date());
        user.setType(0);
        user.setExpireSecond(refresh_expire_second);
        user.setUid(UUID.randomUUID().toString());
        userJpa.save(user);
        int type = user.getType();
        String token = jwtUtils.getToken(Map.of("uid", user.getUid(), "user_type", (type == 0) ? "client" : "admin"));
        return new Result(0, "", Map.of(
                "uid", user.getUid(),
                "token", token,
                "refresh_token", refresh_token,
                "token_expire_second", token_expire_second
        ));
    }

    @Override
    public Result get_user_info(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        Map<String, String> jwt_payload = (Map<String, String>) request.getAttribute("jwt_payload");
        String uid = jwt_payload.get("uid");
        User user = userJpa.findByUid(uid);
        Map<String, Object> user_info = jpaUtil.getUserInfo(user);
        return new Result(0, "", Map.of(
                "uid", user.getUid(),
                "refresh_token", user.getRefreshtokenuser(),
                "token_expire_second", token_expire_second,
                "user_info", user_info
        ));
    }

    @Override
    public Result set_firebase_token(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public Result get_client_info(Map<String, Object> params, HttpServletRequest request) throws ServiceException {
        Map<String, String> jwt_payload = (Map<String, String>) request.getAttribute("jwt_payload");
        String uid = jwt_payload.get("uid");
        User user = userJpa.findByUid(uid);
        return new Result(0, "", Map.of("user_info", jpaUtil.getUserInfo(user)));
    }
}
