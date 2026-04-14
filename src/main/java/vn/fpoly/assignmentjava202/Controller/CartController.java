package vn.fpoly.assignmentjava202.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.assignmentjava202.DAO.ProductDAO;
import vn.fpoly.assignmentjava202.Entity.CartItem;
import vn.fpoly.assignmentjava202.Entity.Products;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    ProductDAO productDAO;

    @Autowired
    Products product;

    private List<CartItem> getCartItems(HttpSession session) {
List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

if (cart == null) {
    cart = new ArrayList<>();
    session.setAttribute("cart", cart);
}
return cart;
    }

    @GetMapping
    public String getCart(HttpSession session, Model model) {
        List<CartItem> cart = getCartItems(session);
        double total = 0;
        for (CartItem item : cart) {
            total += item.getPrice();
        }
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    @GetMapping("/add/{productId}")
    public String addCart(HttpSession session, @PathVariable Integer productId, @RequestParam(defaultValue = "1") Integer quantity, Model model, RedirectAttributes redirectAttributes) {

        Products products = productDAO.findById(productId).orElse(null);

        if(products == null){
            return "redirect:/home/index";
        }

      List<CartItem> cart = getCartItems(session);

        CartItem existingItem = cart.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst().orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        }else{
            CartItem newItem = new CartItem(
                    products.getId(),
                    products.getName(),
                    products.getImage(),
                    products.getPrice().doubleValue(),
                    quantity
            );
       cart.add(newItem);
        }
        redirectAttributes.addFlashAttribute("message", "Đã thêm vào giỏ hàng");
        return "redirect:/home/index";
    }

    @PostMapping("/update/{productId}")
    public String updateCart(@PathVariable Integer productId, @RequestParam(defaultValue = "1") Integer quantity,HttpSession session) {
        List<CartItem> cart = getCartItems(session);

        cart.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresent(item ->{
                        if(quantity <=0 ){
                            cart.remove(item);
        } else{
                    item.setQuantity(quantity);
                        }});
        return "redirect:/cart";
    }

    @GetMapping("/remove/{productId}")
    public String removeCart(@PathVariable Integer productId, HttpSession session) {
        List<CartItem> cart = getCartItems(session);
        cart.removeIf(item -> item.getProductId().equals(productId));
        return "redirect:/cart";

    }

    @GetMapping("/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/cart";
    }
}
