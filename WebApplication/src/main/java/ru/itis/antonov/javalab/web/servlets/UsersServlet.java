package ru.itis.antonov.javalab.web.servlets;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
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

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.usersService = (UsersService) servletContext.getAttribute("usersService");

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        response.getWriter().println(usersService.getAllUsers());
    }
}

