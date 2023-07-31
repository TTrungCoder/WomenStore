package com.web.config;


import com.web.interceptor.AdminAuthenticationInterceptor;
import com.web.interceptor.SiteAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private AdminAuthenticationInterceptor adminAuthenticationInterceptor;

    @Autowired
    private SiteAuthenticationInterceptor siteAuthenticationInterceptor;

    //kiểm tra đường dẫn yêu cầu nếu xuất hiện admin
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAuthenticationInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/vendor/**", "/admin/css/**", "/admin/img/**",
                "/admin/js/**", "/admin/scss/**");

        registry.addInterceptor(siteAuthenticationInterceptor)
                .addPathPatterns("/site/cart/**")
                .excludePathPatterns("/site/css/**", "/site/js/**", "/site/img/**", "/site/fonts/**","/site/sass/**","/site/Source/**","/site/img/product/**","/site/img/blog/**","/site/img/icon/**",
                        "/site/img/shop-details/**","/site/img/shopping-cart/**","/site/img/banner/**");
    }

}
