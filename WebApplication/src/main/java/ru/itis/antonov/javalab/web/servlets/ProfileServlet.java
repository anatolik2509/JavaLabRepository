package ru.itis.antonov.javalab.web.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.itis.antonov.javalab.web.models.Profile;
import ru.itis.antonov.javalab.web.models.User;
import ru.itis.antonov.javalab.web.services.ProfileService;
import ru.itis.antonov.javalab.web.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UsersService usersService;
    private ProfileService profileService;
    private ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        context = config.getServletContext();
        usersService = (UsersService) context.getAttribute("usersService");
        profileService = (ProfileService) context.getAttribute("profileService");

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        long id = -1;
        try{
            id = Long.parseLong(request.getParameter("userId"));
        }
        catch (NumberFormatException e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Profile p = profileService.getById(id);
        if(p == null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("contextPath", context.getContextPath());
        attributes.put("userName", p.getLogin());
        attributes.put("userId", p.getId());
        attributes.put("csrfToken", request.getAttribute("_csrf_token"));
        Configuration configuration = (Configuration) context.getAttribute("freemarkerConfig");
        Template template = configuration.getTemplate("profile.ftlh");
        response.setContentType("text/html; charset=utf-8");
        try {
            template.process(attributes, response.getWriter());
        } catch (TemplateException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("action") != null && request.getParameter("action").equals("delete")) {
            profileService.deleteById(Long.parseLong(request.getParameter("userId")));
        }
        response.sendRedirect("/profile");
    }

}
