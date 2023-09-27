package com.example.manageuser.Components;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JWTInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //WebMvcConfigurer.super.addInterceptors(registry);
        String[] excludePathPatterns = new String[]{
                "/public/**",
                "/admin/auth/login",
                "/admin/auth/get_sms",
                "/admin/auth/refresh_token",
                "/admin/auth/reset_password",
                "/client/auth/login",
                "/client/auth/loginbysms",
                "/client/auth/get_sms",
                "/client/auth/refresh_token",
                "/client/auth/verify_phone",
                "/client/auth/register",
                "/client/auth/reset_password",
                "/favicon.ico",
                "/api-docs",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/error",
                "/kaspi/payment"
        };
/*        String[] adminExcludePathPatterns = new String[]{
                "/public/**",
                "/admin/auth/login",
                "/admin/auth/get_sms",
                "/admin/auth/refresh_token",
                "/admin/auth/reset_password",
                "/client/**",
                "/favicon.ico",
                "/api-docs",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/error",
                "/kaspi/payment"
        };*/
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
        //registry.addInterceptor(authInterceptor).addPathPatterns("/admin/**").excludePathPatterns(excludePathPatterns);

    }
}
