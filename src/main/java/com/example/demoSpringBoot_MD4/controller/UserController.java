package com.example.demoSpringBoot_MD4.controller;

import com.example.demoSpringBoot_MD4.model.Role;
import com.example.demoSpringBoot_MD4.model.User;
import com.example.demoSpringBoot_MD4.service.IRoleService;
import com.example.demoSpringBoot_MD4.service.IUserService;
import com.example.demoSpringBoot_MD4.validate_user.Validate_UserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
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
    @Value("${uploadPart}")
    private String uploadPart;

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
        return modelAndView;
    }
    @ModelAttribute ("role")
    public List<Role> show(){
        return roleService.findall();
    }

    @PostMapping("create")
    public Object add(@Valid @ModelAttribute(value = "user") User user, BindingResult bindingResult,@RequestParam MultipartFile uppImg) {
        validate_userName.validate(user, bindingResult);
        if (bindingResult.hasFieldErrors()){
            ModelAndView modelAndView = new ModelAndView("createUser");
            return modelAndView;
        }
        String filename = uppImg.getOriginalFilename();
        try {
            FileCopyUtils.copy(uppImg.getBytes(),new File(uploadPart+"img/" + filename));
            user.setImg("img/" +filename);
            userService.save(user);

        } catch (IOException e) {
            user.setImg("");
            userService.save(user);
            e.printStackTrace();
        }
        return "redirect:/user";
    }

    @GetMapping("edit")
    public ModelAndView edit(@RequestParam long id) {
        ModelAndView modelAndView = new ModelAndView("editUser");
        modelAndView.addObject("user", userService.findById(id));
        return modelAndView;
    }

    @PostMapping("edit")
    public Object editUser(@Valid @ModelAttribute(value = "user") User user, BindingResult bindingResult, @RequestParam ("uppImg") MultipartFile uppImg) {
       validate_userName.validate(user,bindingResult);
        if (bindingResult.hasFieldErrors()){
            ModelAndView modelAndView = new ModelAndView("editUser");
            return modelAndView;
        }
        if (uppImg.getSize() != 0) {
            String file1 = uploadPart + user.getImg();
            File file = new File(file1);
            file.delete();
            String nameFile = uppImg.getOriginalFilename();
            try {
                FileCopyUtils.copy(uppImg.getBytes(), new File(uploadPart +"img/"+ nameFile));
                user.setImg("img/" + nameFile);
                userService.save(user);
            } catch (IOException e) {
                user.setImg("");
                userService.save(user);
                e.printStackTrace();
            }

        }
        return "redirect:/user";
    }

    @GetMapping("/delete")
    public ModelAndView deleteform(@RequestParam long id) {
        ModelAndView modelAndView = new ModelAndView("deleteUser");
        modelAndView.addObject("user", userService.findById(id));
        return modelAndView;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long id) {
        User user = userService.findById(id);
        if(user.getImg().isEmpty()){
            userService.delete(id);
            return "redirect:/product";
        }
        String filedelete = user.getImg().replaceAll("img/","");
        String file1 = uploadPart + "img/" +filedelete;
        File file = new File(file1);
        if(file.exists()){
            file.delete();
        }
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
