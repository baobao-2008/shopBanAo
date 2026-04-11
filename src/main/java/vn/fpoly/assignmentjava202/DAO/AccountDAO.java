package vn.fpoly.assignmentjava202.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.fpoly.assignmentjava202.Entity.Accounts;

import java.util.Optional;

public interface AccountDAO extends JpaRepository<Accounts,String> {
    Optional<Accounts> findByEmail(String email);


}
