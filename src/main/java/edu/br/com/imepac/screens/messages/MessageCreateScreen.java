package edu.br.com.imepac.screens.messages;

import edu.br.com.imepac.daos.ContactDao;
import edu.br.com.imepac.daos.MessageManagerDao;
import entidades.Contact;
import entidades.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MessageCreateScreen extends JInternalFrame {
    private JComboBox<Contact> contactReceiverField;
    private JComboBox<Contact> contactSenderField;
    private JTextArea messageField;
    private JButton saveButton;
    private JButton clearButton;
    private JButton cancelButton;

    public MessageCreateScreen() {
        ContactDao contactDao = new ContactDao();
        List<Contact> contacts = contactDao.getAll();
        initComponents(contacts);
    }

    private void initComponents(List<Contact> contacts) {
        setTitle("Cadastrar Mensagem");
        setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 500));
        setClosable(true);
        setIconifiable(true);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Adiciona a imagem
        JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/message.png"));
        mainPanel.add(imageLabel, BorderLayout.WEST);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Destinatário:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        contactReceiverField = new JComboBox<>(contacts.toArray(new Contact[0]));
        contactReceiverField.setRenderer(new ContactListCellRenderer());
        formPanel.add(contactReceiverField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Remetente:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        contactSenderField = new JComboBox<>(contacts.toArray(new Contact[0]));
        contactSenderField.setRenderer(new ContactListCellRenderer());
        formPanel.add(contactSenderField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Mensagem:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        messageField = new JTextArea(5, 20);
        formPanel.add(new JScrollPane(messageField), gbc);

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
                    saveMessage();
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
        // Validação do campo destinatário
        Contact contactReceiver = (Contact) contactReceiverField.getSelectedItem();
        if (contactReceiver == null) {
            JOptionPane.showMessageDialog(this, "O campo destinatário é obrigatório!");
            return false;
        }

        // Validação do campo remetente
        Contact contactSender = (Contact) contactSenderField.getSelectedItem();
        if (contactSender == null) {
            JOptionPane.showMessageDialog(this, "O campo remetente é obrigat��rio!");
            return false;
        }

        // Validação do campo mensagem
        String message = messageField.getText();
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo mensagem é obrigatório!");
            return false;
        }
        return true;
    }

    private void saveMessage() {
        // Implementar lógica de salvamento da mensagem
        Contact contactReceiver = (Contact) contactReceiverField.getSelectedItem();
        Contact contactSender = (Contact) contactSenderField.getSelectedItem();
        String message = messageField.getText();

        Message newMessage = new Message();
        newMessage.setContactReceiver(contactReceiver);
        newMessage.setContactSender(contactSender);
        newMessage.setMessage(message);

        MessageManagerDao messageManagerDao = new MessageManagerDao();
        messageManagerDao.save(newMessage);

        // Salvar a mensagem (ex: em um banco de dados)
        JOptionPane.showMessageDialog(this, "Mensagem salva com sucesso!");
        clearFields();
    }

    private void clearFields() {
        contactReceiverField.setSelectedItem(null);
        contactSenderField.setSelectedItem(null);
        messageField.setText("");
    }

    private class ContactListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Contact) {
                Contact contact = (Contact) value;
                setText(contact.getName());
            }
            return this;
        }
    }
}