package com.example.demoSpringBoot_MD4.service;

import com.example.demoSpringBoot_MD4.model.User;
import com.example.demoSpringBoot_MD4.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepo userRepo;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepo.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public void delete(long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User findById(long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public ArrayList<User> findByName(String name) {
        return userRepo.findAllByName(name);
    }

    @Override
    public List<User> sortByName() {
        List<User> list = findAll();
        list.sort(Comparator.comparing(User::getUserName));
        return list;

    }
}
