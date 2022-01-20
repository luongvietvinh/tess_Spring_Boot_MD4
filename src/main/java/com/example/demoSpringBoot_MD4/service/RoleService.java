package com.example.demoSpringBoot_MD4.service;

import com.example.demoSpringBoot_MD4.model.Role;
import com.example.demoSpringBoot_MD4.repository.IRolerepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService implements IRoleService{
    @Autowired
    IRolerepo rolerepo;
    @Override
    public List<Role> findall() {
        return (List<Role>) rolerepo.findAll();
    }
}
