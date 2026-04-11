package vn.fpoly.assignmentjava202.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.assignmentjava202.DAO.AccountDAO;
import vn.fpoly.assignmentjava202.Entity.Accounts;
import vn.fpoly.assignmentjava202.Service.AccountService;

@Controller
@RequestMapping("/admin")
public class AccontController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountDAO accountDAO;

    @GetMapping("/accounts")
    public String accounts(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        model.addAttribute("page", "admin/accounts");
        return "admin/sidebar";
    }

    @GetMapping("/accounts/delete/{id}")
    public String deleteAccount(@PathVariable String id, Model model) {
        accountService.deleteAccount(id);
        return "redirect:/admin/accounts";
    }

    @GetMapping("/accounts/update/{username}")
    public String updateAccount(@PathVariable String username, Model model) {

        Accounts account =accountService.updateAccount(username);

        model.addAttribute("account", account);
        model.addAttribute("page", "admin/account-form-edit");

        return "admin/sidebar";
    }

    @PostMapping("/accounts/update")
    public String updateProcessAccount(Accounts account, RedirectAttributes redirectAttributes) {
        Accounts existing = accountDAO.findById(account.getUsername()).orElse(null);
        if (existing == null) return "redirect:/admin/accounts";

        existing.setFullname(account.getFullname());
        existing.setEmail(account.getEmail());
        existing.setPassword(account.getPassword());

        accountDAO.save(existing);
        redirectAttributes.addFlashAttribute("message", "Cập nhật thành công!");
        return "redirect:/admin/accounts";
    }



}
