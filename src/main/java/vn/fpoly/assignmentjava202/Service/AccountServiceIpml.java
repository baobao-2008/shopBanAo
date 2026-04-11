package vn.fpoly.assignmentjava202.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpoly.assignmentjava202.DAO.AccountDAO;
import vn.fpoly.assignmentjava202.Entity.Accounts;

import java.util.List;
@Service
public class AccountServiceIpml implements AccountService {

    @Autowired
    AccountDAO accountDAO;

    @Override
    public List<Accounts> getAllAccounts() {
        return accountDAO.findAll();
    }

    @Override
    public void deleteAccount(String id) {
        accountDAO.deleteById(id);
    }
}
