package vn.fpoly.assignmentjava202.Service;

import vn.fpoly.assignmentjava202.Entity.Products;

import java.util.List;

public interface ProductService {
    List<Products> getAllProducts();

    void deleteProduct(int id);

    void addProduct(Products product);

    Products updateProduct(int id);

    Products getProductById(int id);
}
