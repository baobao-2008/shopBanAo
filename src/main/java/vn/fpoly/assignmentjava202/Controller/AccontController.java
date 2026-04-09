package vn.fpoly.assignmentjava202.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.fpoly.assignmentjava202.DAO.AccountDAO;

@Controller
@RequestMapping("/admin")
public class AccontController {

    @Autowired
    AccountDAO accountDAO;

    @GetMapping("/accounts")
    public String accounts(Model model) {
        model.addAttribute("accounts", accountDAO.findAll());
        model.addAttribute("page", "admin/accounts");
        return "admin/sidebar";
    }
}
