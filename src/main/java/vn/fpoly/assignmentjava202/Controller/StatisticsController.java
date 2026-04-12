package vn.fpoly.assignmentjava202.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.fpoly.assignmentjava202.DAO.OrderDetailsDAO;
import vn.fpoly.assignmentjava202.Entity.RevenueByCategory;

@Controller
@RequestMapping("admin")
public class StatisticsController {
    @Autowired
    OrderDetailsDAO orderDetailsDAO;

    @GetMapping("/statistics/revenue")
    public String revenueByCategory(Model model) {

        var revenues = orderDetailsDAO.findRevenueByCategory();

        double totalRevenue = revenues.stream()
                .mapToDouble(RevenueByCategory::getTotalRevenue)
                .sum();

        long totalQuantity = revenues.stream()
                .mapToLong(RevenueByCategory::getTotalQuantity)
                .sum();

        model.addAttribute("revenues", revenues);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("page", "/admin/statistics-revenue");
        return "admin/sidebar";
    }

    @GetMapping("/statistics/vip")
    public String vipCustomers(Model model) {
        model.addAttribute("vips", orderDetailsDAO.getTop10VipCustomers());
        model.addAttribute("page", "/admin/statistics-vip");
        return "admin/sidebar";
    }

}
