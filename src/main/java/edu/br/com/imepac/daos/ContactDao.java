package edu.br.com.imepac.daos;

import edu.br.com.imepac.entidades.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDao extends GenericDao {

    public void save(Contact contact) {
        String sql = "INSERT INTO contacts (name, email,birth_date) VALUES (?, ?, ?)";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getEmail());
            stmt.setDate(3, contact.getBirthDate());
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contact> getAll() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setEmail(rs.getString("email"));
                contact.setBirthDate(rs.getDate("birth_date"));
                // Adicione outros campos conforme necessário
                contacts.add(contact);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public void delete(long contactId) {
        String sql = "DELETE FROM contacts WHERE id = ?";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, contactId);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Contact contact) {
        String sql = "UPDATE contacts SET name = ?, email = ?, birth_date = ? WHERE id = ?";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getEmail());
            stmt.setDate(3, contact.getBirthDate());
            stmt.setLong(4, contact.getId());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Contact getById(long contactId) {
        Contact contact = new Contact();
        String sql = "SELECT * FROM contacts WHERE id = ?";
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, contactId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                contact.setId(rs.getInt("id"));
                contact.setName(rs.getString("name"));
                contact.setEmail(rs.getString("email"));
                contact.setBirthDate(rs.getDate("birth_date"));
                // Adicione outros campos conforme necessário
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }
}