package ru.itis.antonov.javalab.web.servlets;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.itis.antonov.javalab.web.models.User;
import ru.itis.antonov.javalab.web.repositories.UsersRepository;
import ru.itis.antonov.javalab.web.repositories.UsersRepositoryJdbcImpl;
import ru.itis.antonov.javalab.web.services.UsersService;
import ru.itis.antonov.javalab.web.services.UsersServiceImpl;

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

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private UsersService usersService;
    private ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        context = config.getServletContext();
        this.usersService = (UsersService) context.getAttribute("usersService");

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        List<User> userList = usersService.getAllUsers();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("contextPath", context.getContextPath());
        attributes.put("users", userList);
        attributes.put("userName", request.getSession());
        Configuration configuration = (Configuration) context.getAttribute("freemarkerConfig");
        Template template = configuration.getTemplate("users.ftlh");
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
            usersService.deleteById(Long.parseLong(request.getParameter("userId")));
        }
        response.sendRedirect("/profile");
    }

}

