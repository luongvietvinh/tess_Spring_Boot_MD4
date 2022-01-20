package com.example.demoSpringBoot_MD4.validate_user;

import com.example.demoSpringBoot_MD4.model.User;
import com.example.demoSpringBoot_MD4.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
@Component
public class Validate_UserName implements Validator {
@Autowired
    IUserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        List<User> users = userService.findAll();
        for (User u: users) {
            if (user.getUserName().equals(u.getUserName()) && (u.getId() != user.getId())){
                errors.rejectValue("userName","","userName da ton tai, moi nhap ten khac");
                return;
            }
        }
    }
    }

