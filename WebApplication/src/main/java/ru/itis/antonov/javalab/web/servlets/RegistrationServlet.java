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

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    public static final String REGISTRATION_PATH = "/registration.jsp";

    private SecurityService securityService;
    private ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        context = config.getServletContext();
        securityService = (SecurityService)context.getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context.getRequestDispatcher(REGISTRATION_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if(login == null || password == null){
            return;
        }
        UUID id = UUID.randomUUID();
        if(securityService.register(login, password, id.toString())){
            resp.addCookie(new Cookie("session", id.toString()));
            resp.sendRedirect(context.getContextPath() + "/users");
        }
    }
}
