package com.yee.study.spring.boot.samples.mvc.controller;

import com.yee.study.spring.boot.samples.mvc.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * SpringMVC Model、ModelAndView示例Controller
 *
 * Model
 * ModelAndView
 * 
 * @author Roger.Yi
 */
@Controller
@RequestMapping("/mv")
public class ModelAndViewController {

    /**
     * http://localhost:8080/mv/Roger/user.fhtml?gender=female
     * @param model
     * @return
     */
    @RequestMapping("/{name}/user.fhtml")
    public String index(@PathVariable String name, String gender, Model model) {
        User user = new User(name, gender);
        model.addAttribute("user", user);
        return "user";
    }

    /**
     * http://localhost:8080/mv/Roger/user2.fhtml?gender=female
     * @param model
     * @return
     */
    @RequestMapping("/{name}/user2.fhtml")
    public ModelAndView index(@PathVariable String name, String gender, ModelAndView model) {
        User user = new User(name, gender);
        model.addObject("user", user);
        model.setViewName("user");
        return model;
    }
}
