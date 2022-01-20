package com.example.demoSpringBoot_MD4.service;

import com.example.demoSpringBoot_MD4.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface IUserService {
    public List<User> findAll();
    public Page<User> findAll(Pageable pageable);
    public void save(User user);
    public void delete(long id);
    public User findById(long id);
    public ArrayList<User> findByName (String name);
    public List<User> sortByName();
}
