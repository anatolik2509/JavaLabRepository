package ru.itis.antonov.javalab.web.repositories;

import ru.itis.antonov.javalab.web.models.Profile;

public interface SessionRepository{
    void addSession(Profile profile, String sessionId);

    void removeSession(Profile profile);

    void removeSession(Profile profile, String sessionId);

    boolean sessionExist(String sessionId);

    Profile profileBySession(String sessionId);
}
