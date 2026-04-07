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
    public List<Products> findAll() {
        return productDAO.findAll();
    }

}
