package vn.fpoly.assignmentjava202.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.fpoly.assignmentjava202.Entity.OrderDetails;
import vn.fpoly.assignmentjava202.Entity.RevenueByCategory;
import vn.fpoly.assignmentjava202.Entity.VipCustomer;

import java.util.List;

public interface OrderDetailsDAO extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findByOrderId(Long orderId);

    @Query("SELECT new vn.fpoly.assignmentjava202.Entity.RevenueByCategory(" +
            "p.category.name, " +
            "CAST(SUM(od.price * od.quantity) AS double), " +
            "SUM(od.quantity), " +
            "MAX(od.price), " +
            "MIN(od.price), " +
            "AVG(od.price)) " +
            "FROM OrderDetails od " +
            "JOIN od.product p " +
            "GROUP BY p.category.name")
    List<RevenueByCategory> findRevenueByCategory();

    @Query("SELECT new vn.fpoly.assignmentjava202.Entity.VipCustomer(" +
            "o.account.fullname, " +
            "CAST(SUM(od.price * od.quantity) AS double), " +
            "MIN(o.createDate), " +
            "MAX(o.createDate)) " +
            "FROM OrderDetails od " +
            "JOIN od.order o " +
            "GROUP BY o.account.fullname " +
            "ORDER BY SUM(od.price * od.quantity) DESC")
    List<VipCustomer> getTop10VipCustomers();
}
