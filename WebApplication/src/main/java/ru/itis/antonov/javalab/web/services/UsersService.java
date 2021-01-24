package ru.itis.antonov.javalab.web.services;
import ru.itis.antonov.javalab.web.models.User;

import java.util.List;

public interface UsersService {
    List<User> getAllUsers();

    List<User> getByAge(int age);

    void deleteById(long id);
}
