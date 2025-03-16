package DAO;

import java.util.List;
import java.util.ArrayList;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class MessageDAO {
    
    public Message createMessage(Message message) throws SQLException {
        String sql = "INSERT INTO message (posted_by,message_text,time_posted_epoch) VALUES (?,?,?)";
        Connection conn = ConnectionUtil.getConnection();

        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, message.getPosted_by());
        ps.setString(2, message.getMessage_text());
        ps.setLong(3, message.getTime_posted_epoch());
        int result = ps.executeUpdate();

        if(result > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()) {
                message.setMessage_id(rs.getInt(1));
                return message;
            }
        }

        //error/failed
        return null;
    }

    public Message getAllMessages() throws SQLException {
        String sql = "SELECT * FROM message";
        Connection conn = ConnectionUtil.getConnection();

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            //TODO STOPPED HERE
        }

        //error/failed
        return null;
    }
}
