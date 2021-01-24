package ru.itis.antonov.javalab.web.services;

import ru.itis.antonov.javalab.web.models.Profile;

public interface SecurityService {
    boolean authorize(String login, String password, String session);

    boolean isAuthenticated(String sessionId);

    boolean register(String login, String password, String session);

}
