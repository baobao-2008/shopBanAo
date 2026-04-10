package vn.fpoly.assignmentjava202.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.fpoly.assignmentjava202.DAO.AccountDAO;
import vn.fpoly.assignmentjava202.Entity.Products;
import vn.fpoly.assignmentjava202.Service.ProductService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    ProductService productService;

    @GetMapping("/index")
    public String index(Model model) {
        List<Products> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "trang-chu";
    }
}
