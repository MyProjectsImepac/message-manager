package edu.br.com.imepac.screens;

import edu.br.com.imepac.screens.contacts.ContactCreateScreen;
import edu.br.com.imepac.screens.contacts.ContactManagerScreen;
import edu.br.com.imepac.screens.messages.MessageCreateScreen;
import edu.br.com.imepac.screens.messages.MessageManagerScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {
    private JDesktopPane desktopPane;

    public MainScreen() {
        initComponents();
    }

    private void initComponents() {
        // Configura a tela principal para ocupar todo o espaço da área de trabalho
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Main Screen");

        // Cria o JDesktopPane
        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        // Cria a barra de menu
        JMenuBar menuBar = new JMenuBar();

        JMenu contactsMenu = new JMenu("Contatinhos");
        JMenuItem contactsItem = new JMenuItem("Cadastrar");
        contactsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ContactCreateScreen contactCreateScreen = new ContactCreateScreen();
                desktopPane.add(contactCreateScreen);
                contactCreateScreen.setVisible(true);
            }
        });

        JMenuItem contactsListagemItem = new JMenuItem("Listagem");
        contactsListagemItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ContactManagerScreen contactManagerScreen = new ContactManagerScreen();
                desktopPane.add(contactManagerScreen);
                contactManagerScreen.setVisible(true);
            }
        });

        contactsMenu.add(contactsItem);
        contactsMenu.add(contactsListagemItem);

        // Cria o menu "Mensagens"
        JMenu mensagensMenu = new JMenu("Mensagens");
        JMenuItem cadastrarItem = new JMenuItem("Cadastrar");
        cadastrarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MessageCreateScreen messageCreateScreen = new MessageCreateScreen();
                desktopPane.add(messageCreateScreen);
                messageCreateScreen.setVisible(true);
            }
        });

        JMenuItem listagemItem = new JMenuItem("Listagem");
        listagemItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MessageManagerScreen messageManagerScreen = new MessageManagerScreen();
                desktopPane.add(messageManagerScreen);
                messageManagerScreen.setVisible(true);
            }
        });

        mensagensMenu.add(cadastrarItem);
        mensagensMenu.add(listagemItem);

        // Cria o menu "Sair"
        JMenuItem sairMenu = new JMenuItem("Sair");

        sairMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Adiciona os menus à barra de menu
        menuBar.add(contactsMenu);
        menuBar.add(mensagensMenu);
        menuBar.add(sairMenu);

        // Define a barra de menu na tela principal
        setJMenuBar(menuBar);

        // Exibe a tela principal
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
            }
        });
    }
}