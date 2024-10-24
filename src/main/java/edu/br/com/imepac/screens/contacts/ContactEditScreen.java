package edu.br.com.imepac.screens.contacts;

import edu.br.com.imepac.daos.ContactDao;
import entidades.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class ContactEditScreen extends JInternalFrame {
    private JLabel idLabel;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField birthDateField;
    private JButton saveButton;
    private JButton clearButton;
    private JButton cancelButton;
    private long contactId;

    public ContactEditScreen(Contact contact) {
        this.contactId = contact.getId();
        initComponents(contact);
    }

    private void initComponents(Contact contact) {
        setTitle("Editar Contato");
        setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 600));
        setClosable(true);
        setIconifiable(true);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Adiciona a imagem
        JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/contact.png"));
        mainPanel.add(imageLabel, BorderLayout.WEST);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        idLabel = new JLabel(String.valueOf(contact.getId()));
        formPanel.add(idLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nameField = new JTextField(20);
        nameField.setText(contact.getName());
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        emailField = new JTextField(20);
        emailField.setText(contact.getEmail());
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Data de Nascimento (yyyy-mm-dd):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        birthDateField = new JTextField(20);
        birthDateField.setText(contact.getBirthDate().toString());
        formPanel.add(birthDateField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Salvar");
        clearButton = new JButton("Limpar Campos");
        cancelButton = new JButton("Cancelar");

        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateData()) {
                    saveContact();
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private boolean validateData() {
        // Implementar lógica de validação dos campos nome, email.

        // Validação do campo nome
        String name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo nome é obrigatório!");
            return false;
        }

        // Validação do campo email
        String email = emailField.getText();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo email é obrigatório!");
            return false;
        }
        return true;
    }

    private void saveContact() {
        // Implementar lógica de salvamento do contato
        String name = nameField.getText();
        String email = emailField.getText();
        Date birthDate = Date.valueOf(birthDateField.getText());

        Contact contact = new Contact();
        contact.setId(contactId);
        contact.setName(name);
        contact.setEmail(email);
        contact.setBirthDate(birthDate);

        ContactDao contactDao = new ContactDao();
        contactDao.update(contact);

        // Salvar o contato (ex: em um banco de dados)
        JOptionPane.showMessageDialog(this, "Contato atualizado com sucesso!");
        this.dispose();
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        birthDateField.setText("");
    }
}