package ru.itis.antonov.javalab.web.repositories;

import ru.itis.antonov.javalab.web.models.Profile;

public interface ProfilesRepository extends CrudRepository<Profile>{
    Profile findByLogin(String login);
}
