package ru.itis.antonov.javalab.web.services;

import ru.itis.antonov.javalab.web.models.Profile;
import ru.itis.antonov.javalab.web.repositories.ProfilesRepository;
import ru.itis.antonov.javalab.web.repositories.SessionRepository;

public class SecurityServiceRepImpl implements SecurityService{
    ProfilesRepository profilesRepository;

    SessionRepository sessionRepository;

    public SecurityServiceRepImpl(ProfilesRepository p, SessionRepository s){
        profilesRepository = p;
        sessionRepository = s;
    }

    @Override
    public boolean authorize(String login, String password, String session) {
        Profile p = profilesRepository.findByLogin(login);
        if(p == null){
            return false;
        }
        if(p.getPassword().compareTo(password) == 0){
            sessionRepository.addSession(p, session);
            return true;
        }
        return false;
    }

    @Override
    public boolean isAuthenticated(String sessionId) {
        return sessionRepository.sessionExist(sessionId);
    }

    @Override
    public boolean register(String login, String password, String session) {
        if(profilesRepository.findByLogin(login) != null) return false;
        Profile p = Profile.builder().login(login).password(password).build();
        profilesRepository.save(p);
        sessionRepository.addSession(p, session);
        return true;
    }


}
