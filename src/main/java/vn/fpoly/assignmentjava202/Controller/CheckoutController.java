package vn.fpoly.assignmentjava202.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.assignmentjava202.DAO.AccountDAO;
import vn.fpoly.assignmentjava202.DAO.OrderDAO;
import vn.fpoly.assignmentjava202.DAO.OrderDetailsDAO;
import vn.fpoly.assignmentjava202.DAO.ProductDAO;
import vn.fpoly.assignmentjava202.Entity.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    OrderDetailsDAO orderDetailsDAO;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    ProductDAO productDAO;

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if(cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        double total = cart.stream().mapToDouble(CartItem::getTotalPrice).sum();
        model.addAttribute("total", total);
        model.addAttribute("cart", cart);
return "checkout";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, Model model, @RequestParam("address") String address,Authentication authentication, RedirectAttributes redirectAttributes) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if(cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        String username = authentication.getName();
        Accounts accounts = accountDAO.findById(username).orElse(null);

        Orders orders = new Orders();
        orders.setAccount(accounts);
        orders.setAddress(address);
        orders.setCreateDate(LocalDate.now());
        orderDAO.save(orders);

        for(CartItem cartItem : cart) {
            Products product = productDAO.findById(cartItem.getProductId()).orElse(null);
            if(product != null) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrder(orders);
                orderDetails.setProduct(product);
                orderDetails.setQuantity(cartItem.getQuantity());
                orderDetails.setPrice(cartItem.getPrice());
                orderDetailsDAO.save(orderDetails);
                }
            }
        session.removeAttribute("cart");

        redirectAttributes.addFlashAttribute("message","Đặt hàng thành công");
        return "redirect:/orders";
        }
        @GetMapping("/orders")
    public String orders(Authentication authentication, Model model) {
        String username = authentication.getName();
        Accounts accounts = accountDAO.findById(username).orElse(null);
        List<Orders> orders = orderDAO.findByAccount(accounts);
        return "orders";
        }

        @GetMapping("/orders/{id}")
        public String orderDetails(@PathVariable Long id,  Model model) {
        Orders order = orderDAO.findById(id).orElse(null);
        if(order == null) {
            return "redirect:/orders";
        }
        model.addAttribute("order", order);
        return "order-detail";
        }
    }
