package vn.fpoly.assignmentjava202.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.fpoly.assignmentjava202.Service.ProductService;

@Controller
@RequestMapping("/product")
public class UserProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/detail/{id}")
    public String detailProduct(@PathVariable int id, Model model) {
        var products = productService.getProductById(id);
        if(products==null) {
            return "redirect:/home/index";
        }
        model.addAttribute("product",products);
        return "product-detail";
    }
}
