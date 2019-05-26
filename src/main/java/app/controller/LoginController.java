package app.controller;

import app.dao.UserMapper;
import app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

    @Autowired
    private UserMapper userMapper;


    @PostMapping("/login")
    public String login(@RequestParam(value="username") String username, @RequestParam(value="password") String password) {
        //
        User nowUser=new User();
        return username + ".html";
    }
}
