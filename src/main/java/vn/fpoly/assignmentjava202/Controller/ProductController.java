package vn.fpoly.assignmentjava202.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.fpoly.assignmentjava202.Service.ProductService;

@Controller
@RequestMapping("/admin")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products",productService.getAllProducts());
        model.addAttribute("page","/admin/products");
        return "admin/sidebar";
    }

}
