package ru.itis.antonov.javalab.web.repositories;

import ru.itis.antonov.javalab.web.models.Profile;
import ru.itis.antonov.javalab.web.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ProfileRepositoryJdbcImpl implements ProfilesRepository{

    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT * FROM profile";

    //language=SQL
    private static final String SQL_INSERT = "INSERT INTO profile(email, password) VALUES (?, ?) RETURNING id";

    //language=SQL
    private static final String SQL_FIND_BY_ID = "SELECT * FROM profile WHERE id=?";

    //language=SQL
    public static final String SQL_FIND_BY_LOGIN = "SELECT * FROM profile WHERE email=?";

    //language=SQL
    public static final String SQL_DELETE = "DELETE FROM profile WHERE id=?";


    private DataSource dataSource;

    private SimpleJdbcTemplate template;

    public ProfileRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        template = new SimpleJdbcTemplate(dataSource);
    }

    private RowMapper<Profile> profileRowMapper = row -> Profile.builder()
            .id(Integer.parseInt(row.getString("id")))
            .login(row.getString("email"))
            .password(row.getString("password")).build();


    @Override
    public void save(Profile entity) {
        Integer id = template.query(SQL_INSERT,(row) ->row.getInt("id"), entity.getLogin(), entity.getPassword()).get(0);
        entity.setId(id);
    }

    @Override
    public void update(Profile entity) {
    }

    @Override
    public void delete(Profile entity) {
        template.update(SQL_DELETE, entity.getId());
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return Optional.of(template.query(SQL_FIND_BY_ID, profileRowMapper, id).get(0));
    }

    @Override
    public List<Profile> findAll() {
        return template.query(SQL_FIND_ALL, profileRowMapper);
    }

    @Override
    public Profile findByLogin(String login) {
        List<Profile> list = template.query(SQL_FIND_BY_LOGIN, profileRowMapper, login);
        if(list.size() > 0) {
            return list.get(0);
        }
        else return null;
    }
}
