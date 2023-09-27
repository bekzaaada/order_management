package com.example.manageuser.Components;

import com.example.manageuser.Entity.User;
import com.example.manageuser.Utils.ContainerUtil;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JpaUtil {

    public Map<String, Object> getUserInfo(User user) {

        if(user==null)
            return null;

        return ContainerUtil.object2map(user, (map, key, val) -> {
            if ("password".equals(key) ||
                    "refreshtokenuser".equals(key) ||
                    "refreshtokenadmin".equals(key)
            ) return;

            if ("createtime".equals(key) || "lastlogin".equals(key)) {
                if (val != null) {
                    map.put(key, ((Long) val) / 1000);
                    return;
                }

            }

            map.put(key, val);

        });
    }


    public String getPropertyByLang(Map<String,Object> map,String lang,Map<String,String> lang_map)
    {
        String prop_name= lang_map.get(lang);
        return (String)map.get(prop_name);
    }


}
