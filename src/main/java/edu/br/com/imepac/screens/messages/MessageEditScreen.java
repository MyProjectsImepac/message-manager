package edu.br.com.imepac.screens.messages;

import edu.br.com.imepac.daos.ContactDao;
import edu.br.com.imepac.daos.MessageManagerDao;
import edu.br.com.imepac.entidades.Contact;
import edu.br.com.imepac.entidades.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MessageEditScreen extends JInternalFrame {
    private JTextField idField;
    private JComboBox<Contact> contactReceiverField;
    private JComboBox<Contact> contactSenderField;
    private JTextArea messageField;
    private JButton saveButton;
    private JButton clearButton;
    private JButton cancelButton;
    private long messageId;

    public MessageEditScreen(Message message) {
        this.messageId = message.getId();
        ContactDao contactDao = new ContactDao();
        List<Contact> contacts = contactDao.getAll();
        initComponents(message, contacts);
    }

    private void initComponents(Message message, List<Contact> contacts) {
        setTitle("Editar Mensagem");
        setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 600));
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
        formPanel.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        idField = new JTextField(20);
        idField.setText(String.valueOf(message.getId()));
        idField.setEditable(false);
        formPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Destinatário:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        contactReceiverField = new JComboBox<>(contacts.toArray(new Contact[0]));
        contactReceiverField.setRenderer(new ContactListCellRenderer());
        contactReceiverField.setSelectedItem(message.getContactReceiver());
        formPanel.add(contactReceiverField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Remetente:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        contactSenderField = new JComboBox<>(contacts.toArray(new Contact[0]));
        contactSenderField.setRenderer(new ContactListCellRenderer());
        contactSenderField.setSelectedItem(message.getContactSender());
        formPanel.add(contactSenderField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Mensagem:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        messageField = new JTextArea(5, 20);
        messageField.setText(message.getMessage());
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
            JOptionPane.showMessageDialog(this, "O campo remetente é obrigatório!");
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
        String messageText = messageField.getText();

        Message message = new Message();
        message.setId(messageId);
        message.setContactReceiver(contactReceiver);
        message.setContactSender(contactSender);
        message.setMessage(messageText);

        MessageManagerDao messageManagerDao = new MessageManagerDao();
        messageManagerDao.update(message);

        // Salvar a mensagem (ex: em um banco de dados)
        JOptionPane.showMessageDialog(this, "Mensagem atualizada com sucesso!");
        this.dispose();
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