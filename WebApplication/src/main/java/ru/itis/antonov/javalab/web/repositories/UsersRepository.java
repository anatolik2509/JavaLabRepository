package ru.itis.antonov.javalab.web.repositories;

import ru.itis.antonov.javalab.web.models.User;

import java.util.List;

public interface UsersRepository extends CrudRepository<User> {
    List<User> findAllByAge(int age);
}
