package vn.fpoly.assignmentjava202.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.fpoly.assignmentjava202.Entity.Accounts;
import vn.fpoly.assignmentjava202.Entity.Orders;

public interface OrderDAO extends JpaRepository<Orders,String> {
}
