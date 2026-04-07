package vn.fpoly.assignmentjava202.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.fpoly.assignmentjava202.Entity.Accounts;

public interface AccountDAO extends JpaRepository<Accounts,String> {
}
