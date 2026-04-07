package vn.fpoly.assignmentjava202.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
    @RequestMapping("/auth/login")
    public String login() {
        return "login";
    }
}
