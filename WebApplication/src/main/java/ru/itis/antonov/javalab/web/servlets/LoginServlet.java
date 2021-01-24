package ru.itis.antonov.javalab.web.servlets;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.itis.antonov.javalab.web.repositories.ProfilesRepository;
import ru.itis.antonov.javalab.web.services.ProfileService;
import ru.itis.antonov.javalab.web.services.SecurityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public static final String LOGIN_PATH = "/login.jsp";

    private SecurityService securityService;
    private ProfileService profileService;
    private ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        context = config.getServletContext();
        securityService = (SecurityService)context.getAttribute("securityService");
        profileService = (ProfileService)context.getAttribute("profileService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("login") == null || req.getParameter("password") == null) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("contextPath", context.getContextPath());
            Configuration configuration = (Configuration) context.getAttribute("freemarkerConfig");
            Template template = configuration.getTemplate("login.ftlh");
            resp.setContentType("text/html; charset=utf-8");
            try {
                template.process(attributes, resp.getWriter());
            } catch (TemplateException e) {
                throw new IllegalArgumentException(e);
            }

        }
        else {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            UUID id = UUID.randomUUID();

            if (securityService.authorize(login, password, id.toString())) {
                req.getSession(true).setAttribute("profile", profileService.getByLogin(login));
                resp.sendRedirect(context.getContextPath() + "/users");
            }
        }
    }
}
