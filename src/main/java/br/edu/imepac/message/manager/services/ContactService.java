package br.edu.imepac.message.manager.services;

import br.edu.imepac.message.manager.daos.ContactDao;
import br.edu.imepac.message.manager.entities.Contact;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactService {

    private final ContactDao contactDao;

    public ContactService() {
        this.contactDao = new ContactDao();
    }

    public int addContact(Contact contact) throws SQLException {
        return this.contactDao.save(contact);
    }

    public Contact findContact(Long id) throws SQLException {
        return this.contactDao.read(id);
    }

    public ArrayList<Contact> findAllContact() throws SQLException {
        return this.contactDao.findAll();
    }

    public int removeContact(Long id) throws SQLException {
        return this.contactDao.delete(id);
    }

    public int updateContact(Contact contact) throws SQLException {
        return this.contactDao.update(contact);
    }
}
