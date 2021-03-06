package ru.itis.antonov.javalab.web.filters;

import ru.itis.antonov.javalab.web.services.SecurityService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

    private String[] protectedPaths;
    private SecurityService securityService;
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
        protectedPaths = new String[]{"/login", "/registration","/css"};
        securityService = (SecurityService)context.getAttribute("securityService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        boolean isProtected = false;
        for(String s : protectedPaths){
            if(request.getRequestURI().startsWith(request.getContextPath() + s)){
                isProtected = true;
            }
        }
        if(isProtected){
            filterChain.doFilter(request, response);
        }
        else {
            if(request.getSession(false) != null) {
                filterChain.doFilter(request, response);
            }
            response.sendRedirect(context.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {

    }
}
