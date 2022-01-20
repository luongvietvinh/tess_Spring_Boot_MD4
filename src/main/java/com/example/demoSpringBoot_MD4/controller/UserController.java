package com.example.demoSpringBoot_MD4.controller;

import com.example.demoSpringBoot_MD4.model.User;
import com.example.demoSpringBoot_MD4.service.IRoleService;
import com.example.demoSpringBoot_MD4.service.IUserService;
import com.example.demoSpringBoot_MD4.validate_user.Validate_UserName;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;
    @Autowired
    Validate_UserName validate_userName;

    @GetMapping("/user")
    public ModelAndView showUser(@RequestParam(defaultValue = "0") int page) {
        ModelAndView modelAndView = new ModelAndView("showUser");
        modelAndView.addObject("user", userService.findAll(PageRequest.of(page, 4)));
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("createUser");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("role", roleService.findall());
        return modelAndView;
    }

    @PostMapping("create")
    public ModelAndView add(@Valid @ModelAttribute(value = "user") User user, BindingResult bindingResult,@RequestParam MultipartFile uppImg) {
        validate_userName.validate(user, bindingResult);
        if (bindingResult.hasFieldErrors()){
            ModelAndView modelAndView = new ModelAndView("createUser");
            modelAndView.addObject("role", roleService.findall());
            return modelAndView;
        }
        String filename = uppImg.getOriginalFilename();
        try {
            FileCopyUtils.copy(uppImg.getBytes(),new File("C:/Users/Admind/Desktop/demoSpringBoot_MD4/src/main/resources/static/img/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setImg("/img/" +filename);
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("redirect:/user");
        return modelAndView;
    }

    @GetMapping("edit")
    public ModelAndView edit(@RequestParam long id) {
        ModelAndView modelAndView = new ModelAndView("editUser");
        modelAndView.addObject("user", userService.findById(id));
        modelAndView.addObject("role", roleService.findall());
        return modelAndView;
    }

    @PostMapping("edit")
    public ModelAndView editUser(@Valid @ModelAttribute(value = "user") User user,BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()){
            ModelAndView modelAndView = new ModelAndView("editUser");
            modelAndView.addObject("role", roleService.findall());
            return modelAndView;
        }
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("redirect:/user");
        return modelAndView;
    }

    @GetMapping("/delete")
    public ModelAndView deleteform(@RequestParam long id) {
        ModelAndView modelAndView = new ModelAndView("deleteUser");
        modelAndView.addObject("user", userService.findById(id));
        return modelAndView;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long id) {
        userService.delete(id);
        return "redirect:/user";
    }

    @PostMapping("/search")
    public ModelAndView searchByName(@RequestParam (value = "search") String search) {
        ModelAndView modelAndView = new ModelAndView("search");
        modelAndView.addObject("user", userService.findByName(search));
        return modelAndView;
    }

    @GetMapping("/sortByName")
    public ModelAndView sortsalary() {
        ModelAndView modelAndView = new ModelAndView("sort");
        List<User> sortByName = userService.sortByName();
        modelAndView.addObject("user", sortByName);
        return modelAndView;
    }
    @GetMapping ("detail")
    public ModelAndView detail (@RequestParam long id) {
        ModelAndView modelAndView = new ModelAndView("detailUser");
        modelAndView.addObject("user" , userService.findById(id));
        return modelAndView;
    }
}
