package app.controller;

import app.entity.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    //finish test
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        System.out.println("===========[Debug]:login=========");
        System.out.println("===========Params:(username,"+username+
                "),(password,"+password+")=========");
        User nowUser=userService.selectByUsername(username);

        if (nowUser!=null && nowUser.getPassword().equals(password)) {

            if (username.equals("admin")) return "redirect:http://localhost:8080/adminView/";
            if (username.equals("manager")) return "redirect:http://localhost:8080/managerView/";
            if (username.equals("receptionist")) return "redirect:http://localhost:8080/receptionistView";

            return "redirect:http://localhost:8080/userView/"+nowUser.getRoomid();
        }
        return "redirect:http://localhost:8080/errorView";
    }

    @RequestMapping("/userView/{roomid}")
    public String userLoginView(){

        return "../user.html";
    }

}
