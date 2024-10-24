package edu.br.com.imepac.screens.messages;

import edu.br.com.imepac.daos.MessageManagerDao;
import entidades.Message;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MessageManagerScreen extends JInternalFrame {
    private JTable messageTable;
    private JButton deleteButton;
    private JButton newMessageButton;
    private JButton refreshButton;
    private JButton closeButton;
    private JButton editButton;

    public MessageManagerScreen() {
        MessageManagerDao messageManagerDao = new MessageManagerDao();
        List<Message> messages = messageManagerDao.getAll();

        initComponents(messages);
    }

    private void initComponents(List<Message> messages) {
        setTitle("Gerenciar Mensagens");
        setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 800));
        setClosable(true);
        setIconifiable(true);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Adiciona a imagem
        JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/message_manager.png"));
        mainPanel.add(imageLabel, BorderLayout.WEST);

        // Configura a tabela de mensagens
        String[] columnNames = {"ID", "Destinatário", "Remetente", "Mensagem"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas as células não são editáveis
            }
        };

        messageTable = new JTable(tableModel);
        messageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        for (Message message : messages) {
            Object[] rowData = {
                    message.getId(),
                    message.getContactReceiver().getName(),
                    message.getContactSender().getName(),
                    message.getMessage()
            };
            tableModel.addRow(rowData);
        }
        mainPanel.add(new JScrollPane(messageTable), BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Configura os botões
        JPanel buttonPanel = new JPanel();
        deleteButton = new JButton("Excluir");
        newMessageButton = new JButton("Nova Mensagem");
        closeButton = new JButton("Fechar");
        refreshButton = new JButton("Recarregar");
        editButton = new JButton("Editar");

        buttonPanel.add(deleteButton);
        buttonPanel.add(newMessageButton);
        buttonPanel.add(closeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(editButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Ações dos botões
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMessage();
            }
        });

        newMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewMessageScreen();
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
                editMessage();
            }
        });
    }

    private void setScreenEnabled(boolean enabled) {
        messageTable.setEnabled(enabled);
        deleteButton.setEnabled(enabled);
        newMessageButton.setEnabled(enabled);
        refreshButton.setEnabled(enabled);
        closeButton.setEnabled(enabled);
        editButton.setEnabled(enabled);
    }

    private void editMessage() {
        int selectedRow = messageTable.getSelectedRow();
        if (selectedRow != -1) {
            long messageId = (long) messageTable.getValueAt(selectedRow, 0);
            MessageManagerDao messageManagerDao = new MessageManagerDao();
            Message message = messageManagerDao.getById(messageId);
            MessageEditScreen messageEditScreen = new MessageEditScreen(message);
            getParent().add(messageEditScreen);
            messageEditScreen.setVisible(true);
            setScreenEnabled(false); // Bloquear a tela

            // Adicionar listener para desbloquear a tela e atualizar a tabela quando a tela de edição for fechada
            messageEditScreen.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                    setScreenEnabled(true); // Desbloquear a tela
                    refreshTable(); // Atualizar a tabela
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma mensagem para editar.");
        }
    }

    private void refreshTable() {
        MessageManagerDao messageManagerDao = new MessageManagerDao();
        List<Message> messages = messageManagerDao.getAll();

        DefaultTableModel tableModel = (DefaultTableModel) messageTable.getModel();
        tableModel.setRowCount(0); // Limpa a tabela

        for (Message message : messages) {
            Object[] rowData = {
                    message.getId(),
                    message.getContactReceiver().getName(),
                    message.getContactSender().getName(),
                    message.getMessage()
            };
            tableModel.addRow(rowData);
        }
    }

    private void deleteMessage() {
        int selectedRow = messageTable.getSelectedRow();
        if (selectedRow != -1) {
            long messageId = (long) messageTable.getValueAt(selectedRow, 0);
            MessageManagerDao messageManagerDao = new MessageManagerDao();
            messageManagerDao.delete(messageId);
            ((DefaultTableModel) messageTable.getModel()).removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Mensagem excluída com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma mensagem para excluir.");
        }
    }

    private void openNewMessageScreen() {
        MessageCreateScreen messageCreateScreen = new MessageCreateScreen();
        getParent().add(messageCreateScreen);
        messageCreateScreen.setVisible(true);
        setScreenEnabled(false); // Bloquear a tela

        // Adicionar listener para desbloquear a tela e atualizar a tabela quando a tela de criação for fechada
        messageCreateScreen.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                setScreenEnabled(true); // Desbloquear a tela
                refreshTable(); // Atualizar a tabela
            }
        });
    }
}