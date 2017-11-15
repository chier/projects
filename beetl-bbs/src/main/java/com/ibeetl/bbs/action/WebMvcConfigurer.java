package com.ibeetl.bbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ibeetl.bbs.common.WebUtils;
import com.ibeetl.bbs.model.BbsUser;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Autowired
    private WebUtils webUtils;
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                     Object handler) throws Exception {
                
            		String requestURI = request.getServletPath();
            		if(webUtils.currentUser(request, response)==null){
            			//未登陆用户，记录访问地址，登陆后可以直接跳转到此页面
            			if(!requestURI.contains("/bbs/user/login.html")){
            				request.getSession(true).setAttribute("lastAccess", requestURI);
            			}
            		}
                if(requestURI.contains("/bbs/admin/") || requestURI.contains("/bbs/topic/add")){
                    BbsUser user = webUtils.currentUser(request, response);
                    if(user == null){
                        response.sendRedirect(request.getContextPath()+"/user/loginPage.html");
                        return false;
                    }
                }
                return true;
            }
        }).addPathPatterns("/bbs/**");
    }
}
