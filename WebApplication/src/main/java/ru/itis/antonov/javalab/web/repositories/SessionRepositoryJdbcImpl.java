package ru.itis.antonov.javalab.web.repositories;

import ru.itis.antonov.javalab.web.models.Profile;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class SessionRepositoryJdbcImpl implements SessionRepository{

    //language=SQL
    private static final String SQL_INSERT = "INSERT INTO sessions(session_id, profile_id) VALUES (?, ?)";

    //language=SQL
    private static final String SQL_REMOVE_PROFILE_SESSIONS = "DELETE FROM sessions WHERE profile_id=?";

    //language=SQL
    private static final String SQL_REMOVE_SESSION = "DELETE FROM sessions WHERE profile_id=? and session_id=?";

    //language=SQL
    private static final String SQL_SESSION_EXIST = "SELECT * FROM sessions WHERE session_id=?";

    private DataSource dataSource;

    private SimpleJdbcTemplate template;

    public SessionRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        template = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public void addSession(Profile profile, String sessionId) {
        template.update(SQL_INSERT, sessionId, profile.getId());
    }

    @Override
    public void removeSession(Profile profile) {
        template.update(SQL_REMOVE_PROFILE_SESSIONS, profile.getId());
    }

    @Override
    public void removeSession(Profile profile, String sessionId) {
        template.update(SQL_REMOVE_SESSION, profile.getId(), sessionId);
    }

    @Override
    public boolean sessionExist(String sessionId) {
        return template.query(SQL_SESSION_EXIST, (row -> row.getInt("id")), sessionId).size() > 0;
    }

    @Override
    public Profile profileBySession(String sessionId) {
        return null;
    }

}
