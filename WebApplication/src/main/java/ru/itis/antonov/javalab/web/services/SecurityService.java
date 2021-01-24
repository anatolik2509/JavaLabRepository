package ru.itis.antonov.javalab.web.services;

public interface SecurityService {
    boolean authorize(String login, String password, String session);

    boolean isAuthenticated(String sessionId);

    boolean register(String login, String password, String session);
}
