package com.tyler.configuation;

import com.tyler.interceptor.LoginRequestedInterceptor;
import com.tyler.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by tyler on 2017/4/3.
 */
@Component
public class WebConfiguation extends WebMvcConfigurerAdapter {
    @Autowired
    private PassportInterceptor passportInterceptor;
    @Autowired
    private LoginRequestedInterceptor requestedInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(requestedInterceptor).addPathPatterns("/setting*");
        super.addInterceptors(registry);
    }
}
