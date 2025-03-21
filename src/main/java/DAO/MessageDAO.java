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

    public List<Message> getAllMessages() throws SQLException {
        String sql = "SELECT * FROM message";
        Connection conn = ConnectionUtil.getConnection();

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Message> messageList = new ArrayList<Message>();
        while(rs.next()) {
            Message message = new Message(rs.getInt("message_id"), 
                                    rs.getInt("posted_by"), 
                                    rs.getString("message_text"),
                                    rs.getLong("time_posted_epoch"));
            messageList.add(message);
        }

        return messageList;
    }

    public Message getMessageFromID(int messageID) throws SQLException {
        String sql = "SELECT * FROM message WHERE message_id=?";
        Connection conn = ConnectionUtil.getConnection();

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, messageID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            Message message = new Message(rs.getInt("message_id"), 
                                    rs.getInt("posted_by"), 
                                    rs.getString("message_text"),
                                    rs.getLong("time_posted_epoch"));

            return message;
        }

        //error/failed
        return null;
    }

    public Message deleteMessageFromID(int messageID) throws SQLException {
        //check if message exists
        Message messageToDelete = getMessageFromID(messageID);
        
        if(messageToDelete != null) {
            String sql = "DELETE FROM message where message_id=?";
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, messageID);
            int success = ps.executeUpdate();
            if(success > 0)
                return messageToDelete;
        }
        return null;
    }

    public Message updateMessage(Message messageToUpdate) throws SQLException {
        //check if message exists
        Message newMessage = getMessageFromID(messageToUpdate.getMessage_id());

        if(newMessage != null) {
            String sql = "UPDATE message SET posted_by=?,message_text=?,time_posted_epoch=? WHERE message_id=?";
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, messageToUpdate.getPosted_by());
            ps.setString(2, messageToUpdate.getMessage_text());
            ps.setLong(3, messageToUpdate.getTime_posted_epoch());
            ps.setInt(4, messageToUpdate.getMessage_id());
            int success = ps.executeUpdate();

            if(success > 0) 
                return messageToUpdate;
        }

        return null;
    }
}
