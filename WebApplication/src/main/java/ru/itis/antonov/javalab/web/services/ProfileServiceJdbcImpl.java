package ru.itis.antonov.javalab.web.services;

import ru.itis.antonov.javalab.web.models.Profile;
import ru.itis.antonov.javalab.web.repositories.ProfilesRepository;

public class ProfileServiceJdbcImpl implements ProfileService {

    private ProfilesRepository profilesRepository;

    public ProfileServiceJdbcImpl(ProfilesRepository repository){
        this.profilesRepository = repository;
    }

    @Override
    public Profile getById(long id) {
        return profilesRepository.findById(id).orElse(null);
    }

    @Override
    public Profile getByLogin(String login) {
        return profilesRepository.findByLogin(login);
    }

    @Override
    public void deleteById(long id) {
        profilesRepository.delete(Profile.builder().id(id).build());
    }


}
