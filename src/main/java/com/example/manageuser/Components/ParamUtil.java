package com.example.manageuser.Components;

import com.example.manageuser.bean.TokenInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.Map;

public class ParamUtil {
    public static Object get_object(Map<String, Object> params, String key, boolean allow_null) {
        Object val = params.get(key);
        if (!allow_null && val == null)
            throw new RuntimeException(" Request param " + key + " can't be null!");
        if (val == null)
            return null;
        return val;
    }

    public static String get_string(Map<String, Object> params, String key, boolean allow_null) {
        Object val = params.get(key);
        if (!allow_null && val == null)
            throw new RuntimeException(" Request param " + key + " can't be null!");
        if (val == null)
            return null;
        return val.toString().trim();
    }


    public static String get_string_trim(Map<String, Object> params, String key) {
        Object val = params.get(key);
        if (val == null)
            return "";
        return val.toString().trim();
    }

    public static int get_int(Map<String, Object> params, String key, boolean allow_null) {
        Object val = params.get(key);
        if (!allow_null && val == null)
            throw new RuntimeException(" Request param " + key + " can't be null!");
        if (val == null)
            return 0;
        return Integer.parseInt(val.toString().trim());
    }

    public static long get_long(Map<String, Object> params, String key, boolean allow_null) {
        Object val = params.get(key);
        if (!allow_null && val == null)
            throw new RuntimeException(" Request param " + key + " can't be null!");
        if (val == null)
            return 0L;
        return Long.parseLong(val.toString().trim());
    }

    public static float get_float(Map<String, Object> params, String key, boolean allow_null) {
        Object val = params.get(key);
        if (!allow_null && val == null)
            throw new RuntimeException(" Request param " + key + " can't be null!");
        if (val == null)
            return 0.0f;
        return Float.parseFloat(val.toString().trim());
    }
    public static String getAuthInfo(HttpServletRequest request) {
        try {
            Map<String, String> jwt_payload = (Map<String, String>) request.getAttribute("jwt_payload");
            if (jwt_payload != null) {
                String uid = jwt_payload.get("uid");
                String user_type = jwt_payload.get("user_type");
                String result = null;
                if (uid != null)
                    result = "uid:" + uid + ",";
                if (user_type != null)
                    result += "user_type:" + user_type;
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String request_info_has_log(String log) {
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = null;
            if (servletRequestAttributes != null)
                request = servletRequestAttributes.getRequest();
            if (request != null) {
                String auth_info = getAuthInfo(request);
                String request_body = get_request_body(request);
                if (auth_info == null)
                    auth_info = "null";
                if (request_body == null)
                    request_body = "null";

                return "AuthInfo:" + auth_info + ",LogMsg:" + log + ",RequestBody:" + request_body;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return log;
    }
    public static String get_request_body(HttpServletRequest request) {
       /* try {

            if (request != null) {
                String submitMehtod = request.getMethod();
                if (submitMehtod.equals("GET")) {
                    return new String(request.getQueryString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).replaceAll("%22", "\"");
                } else {
                    return getRequestPostStr(request);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }*/
        return null;
    }
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte[] buffer = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }
    public static TokenInfo getTokenInfo(HttpServletRequest request) {
        Map<String, String> jwt_payload = (Map<String, String>) request.getAttribute("jwt_payload");
        if (jwt_payload == null)
            return null;
        String uid = jwt_payload.get("uid");
        String user_type = jwt_payload.get("user_type");
        String companyid = jwt_payload.get("companyid");
        String jpid = jwt_payload.get("jpid");

        return new TokenInfo(uid, user_type);
    }

}

