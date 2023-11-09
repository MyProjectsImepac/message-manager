package br.edu.imepac.message.manager.controllers;

import br.edu.imepac.message.manager.entities.Contact;
import br.edu.imepac.message.manager.services.ContactService;
import br.edu.imepac.message.manager.views.BaseForm;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactController {

    private final ContactService contactService;
    private final BaseForm baseForm;

    public ContactController(BaseForm bseForm) {
        this.contactService = new ContactService();
        this.baseForm = bseForm;
    }

    public void saveContact(Contact contact) {
        try {
            this.contactService.addContact(contact);
        } catch (SQLException sqlException) {
            baseForm.showErrorInternal(sqlException.getMessage());
        }
    }

    public void deleteContact(Contact contact) {
        try {
            this.contactService.removeContact(contact.getId());
        } catch (SQLException sqlException) {
            baseForm.showErrorInternal(sqlException.getMessage());
        }
    }

    public ArrayList<Contact> loadAllData() {
        try {
            return this.contactService.findAllContact();
        } catch (SQLException sqlException) {
            baseForm.showErrorInternal(sqlException.getMessage());
        }
        return null;
    }

    public void updateContact(Contact contact) {
        try {
            this.contactService.updateContact(contact);
        } catch (SQLException sqlException) {
            baseForm.showErrorInternal(sqlException.getMessage());
        }
    }

}
