package vn.fpoly.assignmentjava202.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.assignmentjava202.DAO.AccountDAO;
import vn.fpoly.assignmentjava202.Entity.Accounts;
import vn.fpoly.assignmentjava202.Service.MailerService;

import java.util.UUID;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    MailerService mailerService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(Accounts accounts,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        if (accountDAO.findById(accounts.getUsername()).isPresent()) {
            model.addAttribute("message", "Tài khoản này đã được sử dụng!");
            return "register";
        }

        String token = UUID.randomUUID().toString();
        accounts.setToken(token);
        accounts.setActivated(false);
        accounts.setAdmin(false);

        String verifyLink = "http://localhost:8080/auth/activate?username="
                + accounts.getUsername() + "&token=" + token;

        String subject = "Kích hoạt tài khoản Atino";
        String body = "Chào " + accounts.getUsername() + ",\n\n"
                + "Vui lòng nhấn vào link sau để kích hoạt tài khoản:\n"
                + verifyLink;

        accountDAO.save(accounts);
        mailerService.sendMail(accounts.getEmail(), subject, body);

        redirectAttributes.addFlashAttribute("message",
                "Đăng ký thành công! Hãy kiểm tra email để kích hoạt.");

        return "redirect:/auth/login";
    }

    @GetMapping("/activate")
    public String activate(@RequestParam("username") String username,@RequestParam("token") String token, Model model) {
   Accounts user = accountDAO.findById(username).orElse(null);

   if(user!=null && token.equals(user.getToken())) {
       user.setActivated(true);
       user.setToken(null);
       accountDAO.save(user);
       model.addAttribute("message","Kích hoạt thành công");
   }else {
       model.addAttribute("message","Link kích hoạt sai hoặc đã hết hạn");
   }
        return "login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

}
