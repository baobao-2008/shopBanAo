package vn.fpoly.assignmentjava202.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
            model.addAttribute("message", "Tên tài khoản này đã được sử dụng!");
            return "register";
        }

        if(accountDAO.findByEmail(accounts.getEmail()).isPresent()) {
            model.addAttribute("message", "Email này đã được sử dụng rồi");
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

    @PostMapping("/forgot-password")
    public String fogotPassword(@RequestParam("email") String email,
                                RedirectAttributes redirectAttributes) {

        Accounts user = accountDAO.findByEmail(email).orElse(null);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Email không tồn tại trong hệ thống!");
            return "redirect:/auth/forgot-password";
        }

        String token = UUID.randomUUID().toString();
        user.setToken(token);
        accountDAO.save(user);

        String resetLink = "http://localhost:8080/auth/reset-password?username="
                + user.getUsername() + "&token=" + token;

        String subject = "Đặt lại mật khẩu";
        String body = "Chào " + user.getUsername() + ",\n\n"
                + "Nhấn vào link sau để đặt lại mật khẩu:\n"
                + resetLink + "\n\n"
                + "Link có hiệu lực trong 15 phút. Nếu bạn không yêu cầu, hãy bỏ qua email này.";

        mailerService.sendMail(email, subject, body);

        redirectAttributes.addFlashAttribute("message", "Đã gửi link đặt lại mật khẩu vào email của bạn!");
        return "redirect:/auth/forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam("username") String username, @RequestParam("token") String token, Model model) {
        Accounts user = accountDAO.findById(username).orElse(null);

        if (user == null || !token.equals(user.getToken())) {
            model.addAttribute("error", "Link không hợp lệ hoặc đã hết hạn!");
            return "login";
        }

        model.addAttribute("token", token);
        model.addAttribute("username", username);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("username") String username,
                                @RequestParam("token") String token,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword,
                                RedirectAttributes redirectAttributes){
        Accounts user = accountDAO.findById(username).orElse(null);

        if (user == null || !token.equals(user.getToken())) {
            redirectAttributes.addFlashAttribute("error", "Link không hợp lệ!");
            return "redirect:/auth/login";
        }
        if(!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error","Mật khẩu xác nhận không khớp");
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("token", token);
            return "redirect:/auth/reset-password?username=" + username + "&token=" + token;
        }

        user.setPassword(password);
        user.setToken(null);
        accountDAO.save(user);

        redirectAttributes.addFlashAttribute("message", "Đặt lại mật khẩu thành công! Hãy đăng nhập");
        return "redirect:/auth/login";

    }

    @GetMapping("/change-password")
    public String changePassword() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword, Authentication authentication, RedirectAttributes redirectAttributes) {

        String username = authentication.getName();
        Accounts user = accountDAO.findById(username).orElse(null);

        if (user == null || !user.getPassword().equals(oldPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu cũ không đúng!");
            return "redirect:/auth/change-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "redirect:/auth/change-password";
        }

        user.setPassword(newPassword);
        accountDAO.save(user);

        redirectAttributes.addFlashAttribute("message","Đổi mật khẩu thành công");
        return "redirect:/auth/change-password";
    }

}
