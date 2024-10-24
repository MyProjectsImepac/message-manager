package edu.br.com.imepac.screens.contacts;

import edu.br.com.imepac.daos.ContactDao;
import entidades.Contact;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ContactManagerScreen extends JInternalFrame {
    private JTable contactTable;
    private JButton deleteButton;
    private JButton newContactButton;
    private JButton refreshButton;
    private JButton closeButton;
    private JButton editButton;

    public ContactManagerScreen() {
        ContactDao contactDao = new ContactDao();
        List<Contact> contacts = contactDao.getAll();

        initComponents(contacts);
    }

    private void initComponents(List<Contact> contacts) {
        setTitle("Gerenciar Contatos");
        setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 800));
        setClosable(true);
        setIconifiable(true);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Adiciona a imagem
        JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/contact_manager.png"));
        mainPanel.add(imageLabel, BorderLayout.WEST);

        // Configura a tabela de contatos
        String[] columnNames = {"ID", "Nome", "Email", "Data de Nascimento"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas as células não são editáveis
            }
        };
        
        contactTable = new JTable(tableModel);
        contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (Contact contact : contacts) {
            Object[] rowData = {
                    contact.getId(),
                    contact.getName(),
                    contact.getEmail(),
                    contact.getBirthDate()
            };
            tableModel.addRow(rowData);
        }
        mainPanel.add(new JScrollPane(contactTable), BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Configura os botões
        JPanel buttonPanel = new JPanel();
        deleteButton = new JButton("Excluir");
        newContactButton = new JButton("Novo Contato");
        closeButton = new JButton("Fechar");
        refreshButton = new JButton("Recarregar");
        editButton = new JButton("Editar");

        buttonPanel.add(deleteButton);
        buttonPanel.add(newContactButton);
        buttonPanel.add(closeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(editButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Ações dos botões
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        newContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewContactScreen();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editContact();
            }
        });
    }

    private void setScreenEnabled(boolean enabled) {
        contactTable.setEnabled(enabled);
        deleteButton.setEnabled(enabled);
        newContactButton.setEnabled(enabled);
        refreshButton.setEnabled(enabled);
        closeButton.setEnabled(enabled);
        editButton.setEnabled(enabled);
    }

    private void editContact() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow != -1) {
            long contactId = (long) contactTable.getValueAt(selectedRow, 0);
            ContactDao contactDao = new ContactDao();
            Contact contact = contactDao.getById(contactId);
            ContactEditScreen contactEditScreen = new ContactEditScreen(contact);
            getParent().add(contactEditScreen);
            contactEditScreen.setVisible(true);
            setScreenEnabled(false); // Bloquear a tela

            // Adicionar listener para desbloquear a tela e atualizar a tabela quando a tela de edição for fechada
            contactEditScreen.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                    setScreenEnabled(true); // Desbloquear a tela
                    refreshTable(); // Atualizar a tabela
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um contato para editar.");
        }
    }

    private void refreshTable() {
        ContactDao contactDao = new ContactDao();
        List<Contact> contacts = contactDao.getAll();

        DefaultTableModel tableModel = (DefaultTableModel) contactTable.getModel();
        tableModel.setRowCount(0); // Limpa a tabela

        for (Contact contact : contacts) {
            Object[] rowData = {
                    contact.getId(),
                    contact.getName(),
                    contact.getEmail(),
                    contact.getBirthDate()
            };
            tableModel.addRow(rowData);
        }
    }

    private void deleteContact() {
        int selectedRow = contactTable.getSelectedRow();
        if (selectedRow != -1) {
            long contactId = (long) contactTable.getValueAt(selectedRow, 0);
            ContactDao contactDao = new ContactDao();
            contactDao.delete(contactId);
            ((DefaultTableModel) contactTable.getModel()).removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Contato excluído com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um contato para excluir.");
        }
    }

    private void openNewContactScreen() {
        ContactCreateScreen contactCreateScreen = new ContactCreateScreen();
        getParent().add(contactCreateScreen);
        contactCreateScreen.setVisible(true);
        setScreenEnabled(false); // Bloquear a tela

        // Adicionar listener para desbloquear a tela e atualizar a tabela quando a tela de criação for fechada
        contactCreateScreen.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                setScreenEnabled(true); // Desbloquear a tela
                refreshTable(); // Atualizar a tabela
            }
        });
    }
}