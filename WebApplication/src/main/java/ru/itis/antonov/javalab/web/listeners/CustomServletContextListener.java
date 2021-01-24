package ru.itis.antonov.javalab.web.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.antonov.javalab.web.models.Profile;
import ru.itis.antonov.javalab.web.repositories.*;
import ru.itis.antonov.javalab.web.services.SecurityService;
import ru.itis.antonov.javalab.web.services.SecurityServiceRepImpl;
import ru.itis.antonov.javalab.web.services.UsersService;
import ru.itis.antonov.javalab.web.services.UsersServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


@WebListener
public class CustomServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        Properties properties = new Properties();
        try {
            properties.load(servletContext.getResourceAsStream("WEB-INF/properties/db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
        hikariConfig.setDriverClassName(properties.getProperty("db.driver.classname"));
        hikariConfig.setUsername(properties.getProperty("db.username"));
        hikariConfig.setPassword(properties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        servletContext.setAttribute("dataSource", dataSource);

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);
        UsersService usersService = new UsersServiceImpl(usersRepository);

        ProfilesRepository profilesRepository = new ProfileRepositoryJdbcImpl(dataSource);
        SessionRepository sessionRepository = new SessionRepositoryJdbcImpl(dataSource);

        SecurityService securityService = new SecurityServiceRepImpl(profilesRepository, sessionRepository);

        servletContext.setAttribute("usersService", usersService);
        servletContext.setAttribute("securityService", securityService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

