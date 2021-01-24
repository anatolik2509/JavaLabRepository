package ru.itis.antonov.javalab.web.repositories;

import ru.itis.antonov.javalab.web.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_FIND_ALL = "SELECT * FROM users";

    //language=SQL
    private static final String SQL_FIND_ALL_BY_AGE = "select * from users where age = ?";

    private DataSource dataSource;

    private SimpleJdbcTemplate template;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        template = new SimpleJdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = row -> User.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .age(row.getInt("age"))
            .build();

    @Override
    public void save(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return template.query(SQL_FIND_ALL, userRowMapper);
    }

    @Override
    public List<User> findAllByAge(int age) {
        return template.query(SQL_FIND_ALL_BY_AGE, userRowMapper, age);
    }
}

