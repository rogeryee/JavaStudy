package com.yee.study.spring.boot.mvc.controller;

import com.yee.study.spring.boot.mvc.form.GetUserForm;
import com.yee.study.spring.boot.mvc.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * URL Mapping示例
 * <p>
 * RequestMapping
 * RequestMethod
 * PathVariable
 * RequestParam
 *
 * @author Roger.Yi
 */
@RestController
@RequestMapping("/url")
public class UrlMappingController {

    /**
     * curl http://localhost:8080/url/get/Roger.json
     * <p>
     * 也可以使用简化的注释 @GetMapping(value = "/get/{name}.json") 表示HTTP_GET方式
     * <p>
     * 这两种声明参数的方式相同 @PathVariable String name = @PathVariable("name") String userName
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/get/{name}.json", method = RequestMethod.GET)
    public User getByName(@PathVariable String name) {
        return new User(name, "male");
    }

    /**
     * curl http://localhost:8080/url/get/all/Roger.json
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/get/all/*.json", method = RequestMethod.GET)
    public List<User> allUsers() {
        User roger = new User("Roger", "male");
        User phoebe = new User("Phoebe", "female");
        List<User> users = new ArrayList<User>();
        users.add(roger);
        users.add(phoebe);
        return users;
    }

    /**
     * 指定请求参数
     * curl http://localhost:8080/url/getUser?name=Andy&gender=male
     * curl http://localhost:8080/url/getUser?gender=male 缺少name属性会失败
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/getUser")
    public User getUser(@RequestParam(name = "name", required = true) String userName, String gender) {
        return new User(userName, gender);
    }

    /**
     * 使用表单对象请求
     * curl -d "name=Joey&gender=female" http://localhost:8080/url/query
     *
     * @param name
     * @return
     */
    @PostMapping(value = "/query")
    public User getUser(GetUserForm form) {
        return new User(form.getName(), form.getGender());
    }

    /**
     * 提交JSON格式请求
     *
     * curl 'http://localhost:8080/url/update' -H 'Content-Type:application/json' -d '{"name":"Jason", "sex":"unknown"}'
     *
     * @param user
     * @return
     */
    @RequestMapping("/update")
    public String update(@RequestBody User user) {
        System.out.println("user.name = " + user.getName());
        System.out.println("user.sex = " + user.getSex());
        return "success";
    }
}
