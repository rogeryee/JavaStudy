package com.yee.study.spring.boot.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Freemarker示例Controller
 * @author Roger.Yi
 */
@Controller
@RequestMapping("/freemarker")
public class FreemarkerController {

    /**
     * http://localhost:8080/freemarker/index.fhtml
     * @param model
     * @return
     */
    @RequestMapping("/index.fhtml")
    public String index(Model model) {
        model.addAttribute("name", "Roger");
        return "index";
    }
}
