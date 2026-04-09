package vn.fpoly.assignmentjava202.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpoly.assignmentjava202.DAO.OrderDAO;
import vn.fpoly.assignmentjava202.Entity.Orders;

import java.util.List;
@Service
public class OrderSeviceImpl implements OrderService {

    @Autowired
    OrderDAO orderDAO;

    @Override
    public List<Orders> getAllOrders() {
        return orderDAO.findAll();
    }
}
