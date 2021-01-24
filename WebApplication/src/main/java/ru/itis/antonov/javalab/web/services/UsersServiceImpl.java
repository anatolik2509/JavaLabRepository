package ru.itis.antonov.javalab.web.services;
import ru.itis.antonov.javalab.web.models.User;
import ru.itis.antonov.javalab.web.repositories.UsersRepository;

import java.util.List;

public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public List<User> getByAge(int age) {
        return usersRepository.findAllByAge(age);
    }
}

