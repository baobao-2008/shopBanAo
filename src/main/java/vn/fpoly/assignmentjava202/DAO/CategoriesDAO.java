package vn.fpoly.assignmentjava202.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.fpoly.assignmentjava202.Entity.Catergories;

public interface CategoriesDAO extends JpaRepository<Catergories, String> {
}
