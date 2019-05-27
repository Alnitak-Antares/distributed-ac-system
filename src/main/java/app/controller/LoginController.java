package app.controller;

import app.dao.UserMapper;
import app.entity.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    //finish test
    @GetMapping("/login")
    public String login(@RequestParam(value="username") String username, @RequestParam(value="password") String password) {
        //
        User nowUser=userService.selectByUsername(username);
        if (nowUser!=null && nowUser.getPassword().equals(password)) {
            //空调管理员
            if (username.equals("admin")) return "admin.html";
            if (username.equals("manager")) return "manager.html";
            if (username.equals("receptionist")) return "receptionist.html";
            return "user.html";
        }
        return "error.html";
    }
}
