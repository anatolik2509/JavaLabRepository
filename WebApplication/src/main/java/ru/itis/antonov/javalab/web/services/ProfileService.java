package ru.itis.antonov.javalab.web.services;

import ru.itis.antonov.javalab.web.models.Profile;

public interface ProfileService {
    Profile getById(long id);

    Profile getByLogin(String login);

    void deleteById(long id);
}
