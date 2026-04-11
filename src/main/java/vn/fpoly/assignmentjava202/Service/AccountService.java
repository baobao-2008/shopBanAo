package vn.fpoly.assignmentjava202.Service;


import vn.fpoly.assignmentjava202.Entity.Accounts;

import java.util.List;

public interface AccountService  {
    List<Accounts> getAllAccounts();

    public void deleteAccount(String id);
}
