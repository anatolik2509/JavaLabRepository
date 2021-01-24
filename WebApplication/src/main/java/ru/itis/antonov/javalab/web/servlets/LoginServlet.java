package ru.itis.antonov.javalab.web.servlets;

import ru.itis.antonov.javalab.web.services.SecurityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public static final String LOGIN_PATH = "/login.jsp";

    private SecurityService securityService;
    private ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        context = config.getServletContext();
        securityService = (SecurityService)context.getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("login") == null || req.getParameter("password") == null) {
            context.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
        else {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            UUID id = UUID.randomUUID();

            if (securityService.authorize(login, password, id.toString())) {
                resp.addCookie(new Cookie("session", id.toString()));
                resp.sendRedirect(context.getContextPath() + "/users");
            }
        }
    }
}
