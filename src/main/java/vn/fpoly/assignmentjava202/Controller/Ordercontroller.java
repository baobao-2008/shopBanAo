package vn.fpoly.assignmentjava202.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.fpoly.assignmentjava202.DAO.OrderDAO;
import vn.fpoly.assignmentjava202.Service.OrderService;

@Controller
@RequestMapping("/admin")
public class Ordercontroller {
    @Autowired
    OrderService orderService;

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("page", "admin/order");
        return "/admin/sidebar";
    }
}
