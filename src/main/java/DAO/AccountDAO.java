package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class AccountDAO {
    
    public Account createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO account (username,password) VALUES (?,?)";
        Connection conn = ConnectionUtil.getConnection();

        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, account.username);
        ps.setString(2, account.password);
        int result = ps.executeUpdate();

        if(result > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()) {
                account.setAccount_id(rs.getInt(1));
                return account;
            }
        }

        //error/failed
        return null;
    }

    public Account login(Account account) throws SQLException {
        String sql = "SELECT * FROM account WHERE username=? AND password=?";
        Connection conn = ConnectionUtil.getConnection();

        PreparedStatement ps = conn.prepareStatement(sql);

        //error/failed
        return null;
    }

}