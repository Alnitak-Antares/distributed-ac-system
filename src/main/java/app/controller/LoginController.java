package app.controller;

import app.dao.UserMapper;
import app.entity.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
            //空调管理员
            if (username.equals("admin")) return "admin.html";
            if (username.equals("manager")) return "manager.html";
            if (username.equals("receptionist")) return "receptionist.html";

            return "redirect:/userView/"+nowUser.getRoomid();
        }
        return "error.html";
    }

//    @RequestMapping("/userViem/{roomid}")
//    public String userLoginView(){
//        return "user.html";
//    }
    @RequestMapping("/userView/{roomid}")
    public String userLoginView(){

        return "../user.html";
    }

}
