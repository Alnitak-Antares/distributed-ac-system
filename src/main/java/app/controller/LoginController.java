package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(@RequestParam(value="username") String username, @RequestParam(value="password") String password) {
        return username + ".html";
    }
}
