package Service;

import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }
    
    public Message createMessage(Message message) {
        //if and only if the message_text is not blank, is not over 255 characters, and posted_by refers to a real, existing user
        if(message.getMessage_text() != null && message.getMessage_text() != "" && message.getMessage_text().length() != 0 && message.getMessage_text().length() < 255) {
            try {
                Account checkUser = accountDAO.getAccountFromID(message.getPosted_by());
                if(checkUser != null) {
                    Message result = messageDAO.createMessage(message);
                    return result;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        //fail/error
        return null;
    }

    public List<Message> getAllMessages() {
        List<Message> messageList = new ArrayList<Message>();
        try {
            messageList = messageDAO.getAllMessages();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return messageList;
    }

    public Message getMessage(int messageID) {
        try {
            Message message = messageDAO.getMessageFromID(messageID);
            return message;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Message deleteMessage(int messageID) {
        Message resultMessage = null;

        try {
            resultMessage = messageDAO.deleteMessageFromID(messageID);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return resultMessage;
    }
    
    public Message updateMessage(Message messageToUpdate) {
        Message newMessage = null;
        
        try {
            if(messageToUpdate.getMessage_text() != null && messageToUpdate.getMessage_text().length() > 0 && messageToUpdate.getMessage_text().length() <= 255)
                newMessage = messageDAO.updateMessage(messageToUpdate);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return newMessage;
    }

     public List<Message> getMessagesOfAccountID(int accountID) {
        List<Message> messageList = new ArrayList<Message>();
        try {
            messageList = messageDAO.getAllMessagesFromAcccountID(accountID);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return messageList;
     }
    
}
