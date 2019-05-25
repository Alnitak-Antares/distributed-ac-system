package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WelcomeController {
    @GetMapping("/html")
    String welcome(HttpServletRequest request) {
        return "/welcome.html";
    }
}
