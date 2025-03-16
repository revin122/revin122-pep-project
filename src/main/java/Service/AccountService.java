package Service;

import DAO.AccountDAO;
import Model.Account;

import java.sql.SQLException;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Account registerUser(Account account) {
        //username is not blank and the password is at least 4 characters long
        if(account.getUsername() != null && account.getUsername() != "" && account.getPassword() != null && account.getPassword().length() >= 4) {
            //Account with that username does not already exist
            try {
                Account checkAccount = accountDAO.getAccountFromEmail(account);
                if(checkAccount == null) {
                    Account createdAccount = accountDAO.createAccount(account);
                    return createdAccount;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
        //fail/error
        return null;
    }

    public Account loginUser(Account account) {
        try {
            Account result = accountDAO.login(account);
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //fail/error
        return null;
    }
}
