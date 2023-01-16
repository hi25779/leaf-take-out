package com.lhy.leaftakeout.filter;

import com.alibaba.fastjson.JSON;
import com.lhy.leaftakeout.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author : Jesse(lhy)
 * @mail : 859028027@qq.com
 * @created : 2022-11-20
 **/
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public  static String[] noFilterUrls = new String[]{
            "/employee/login",
            "/employee/logout",
            "/backend/**",
            "/front/**"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (checkUrl(noFilterUrls, httpServletRequest.getRequestURI())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        HttpSession session = httpServletRequest.getSession();
        if (httpServletRequest.getSession().getAttribute("employee") != null) {
            log.info("User already login, username is: {}", httpServletRequest.getSession().getAttribute("employee"));
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        log.info("User not login");
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean checkUrl(String[] urls, String requestURI) {
        for (String url : urls) {
            if (PATH_MATCHER.match(url, requestURI)) {
                return true;
            }
        }
        return false;
    }
}
