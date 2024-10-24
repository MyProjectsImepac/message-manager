package edu.br.com.imepac.daos;

import edu.br.com.imepac.entidades.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageManagerDao extends GenericDao {

    public void save(Message message) {
        String sql = "INSERT INTO messages (contact_receiver_id, contact_sender_id, message) VALUES (?, ?, ?)";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, message.getContactReceiver().getId());
            stmt.setLong(2, message.getContactSender().getId());
            stmt.setString(3, message.getMessage());
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getLong("id"));
                message.setContactReceiver(new ContactDao().getById(rs.getLong("contact_receiver_id")));
                message.setContactSender(new ContactDao().getById(rs.getLong("contact_sender_id")));
                message.setMessage(rs.getString("message"));
                messages.add(message);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public void delete(long messageId) {
        String sql = "DELETE FROM messages WHERE id = ?";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, messageId);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Message message) {
        String sql = "UPDATE messages SET contact_receiver_id = ?, contact_sender_id = ?, message = ? WHERE id = ?";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, message.getContactReceiver().getId());
            stmt.setLong(2, message.getContactSender().getId());
            stmt.setString(3, message.getMessage());
            stmt.setLong(4, message.getId());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Message getById(long messageId) {
        Message message = new Message();
        String sql = "SELECT * FROM messages WHERE id = ?";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, messageId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                message.setId(rs.getLong("id"));
                message.setContactReceiver(new ContactDao().getById(rs.getLong("contact_receiver_id")));
                message.setContactSender(new ContactDao().getById(rs.getLong("contact_sender_id")));
                message.setMessage(rs.getString("message"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }
}