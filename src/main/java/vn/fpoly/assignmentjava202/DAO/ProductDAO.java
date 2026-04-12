package vn.fpoly.assignmentjava202.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.fpoly.assignmentjava202.Entity.Products;

import java.util.List;

public interface ProductDAO extends JpaRepository<Products, Integer> {

    List<Products> findByCategoryNameContainingIgnoreCase(String name);
}
