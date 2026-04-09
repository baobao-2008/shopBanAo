package vn.fpoly.assignmentjava202.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.fpoly.assignmentjava202.DAO.AccountDAO;
import vn.fpoly.assignmentjava202.DAO.OrderDAO;
import vn.fpoly.assignmentjava202.DAO.ProductDAO;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    OrderDAO orderDAO;

    @GetMapping("/index")
    public String index() {
        return "admin/layout";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productDAO.findAll());
        return "admin/products";
    }

    @GetMapping("/products")
    public String accounts(Model model) {
        model.addAttribute("accounts", accountDAO.findAll());
        return "admin/accounts";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderDAO.findAll());
        return "admin/orders";
    }
}
