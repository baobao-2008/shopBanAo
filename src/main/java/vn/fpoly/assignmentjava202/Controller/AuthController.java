package vn.fpoly.assignmentjava202.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/register")
public String register(Accounts accounts, Model model) {

        String token = UUID.randomUUID().toString();
        accounts.setToken(token);
        accounts.setActivated(false);

        String verifyLink = "http://localhost:8080/auth/verify?token=" +accounts.getUsername() + "&Token=" + token;

        String subject="Kích hoạt tài khoản Atino";

        String body = "Chào "+ accounts.getUsername() + ",\n\n" + "Vui lòng nhấn vào link sau để kích hoạt tài khoản: \n" +verifyLink;

        mailerService.sendMail(subject,body, verifyLink);

        model.addAttribute("message", "Đăng ký thành công! Hãy kiểm tra email để kích hoạt. ");

        return "redirect:/auth/login";
    }

    @GetMapping("/activate")
    public String activate(@RequestParam("username") String username,@RequestParam("token") String token, Model model) {
   Accounts user = accountDAO.findById(username).orElse(null);

   if(user!=null & token.equals(user.getToken())) {
       user.setActivated(true);
       user.setToken(null);
       accountDAO.save(user);
       model.addAttribute("message","Kích hoạt thành công");
   }else {
       model.addAttribute("message","Link kích hoạt sai hoặc đã hết hạn");
   }
   return "/auth/login";
    }
}
