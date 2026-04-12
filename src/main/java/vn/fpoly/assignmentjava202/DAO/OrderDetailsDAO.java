package vn.fpoly.assignmentjava202.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.fpoly.assignmentjava202.Entity.OrderDetails;

import java.util.List;

public interface OrderDetailsDAO extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findByOrderId(Long orderId);
}
