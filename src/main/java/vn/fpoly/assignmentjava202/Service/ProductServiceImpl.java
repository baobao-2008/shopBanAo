package vn.fpoly.assignmentjava202.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpoly.assignmentjava202.DAO.ProductDAO;
import vn.fpoly.assignmentjava202.Entity.Products;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDAO productDAO;

    @Override
    public List<Products> getAllProducts() {
        return productDAO.findAll();
    }

    @Override
    public void deleteProduct(int id) {
        productDAO.deleteById(id);
    }

    @Override
    public void addProduct(Products product) {
        productDAO.save(product);
    }

    @Override
    public void updateProduct(int id) {
productDAO.findById(id).orElse(null);
    }

}
