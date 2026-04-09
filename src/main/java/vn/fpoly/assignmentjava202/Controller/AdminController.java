package vn.fpoly.assignmentjava202.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("page", "admin/dashboard");
        return "admin/sidebar";
    }

}
