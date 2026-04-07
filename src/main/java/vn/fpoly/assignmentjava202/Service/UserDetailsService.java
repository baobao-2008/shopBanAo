package vn.fpoly.assignmentjava202.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.fpoly.assignmentjava202.DAO.AccountDAO;
import vn.fpoly.assignmentjava202.Entity.Accounts;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    AccountDAO accountDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Accounts accounts  = accountDAO.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Khng tìm thấy user"));

        return User.withUsername(accounts.getUsername())// phiên dịch dữ liệu sang ngôn ngữ scrurity
                .password(accounts.getPassword())// lây ật khấu từ sql
                .roles(accounts.getAdmin()?"ADMIN":"USER")
                .build();
    }

}
