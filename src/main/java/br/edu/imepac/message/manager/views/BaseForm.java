package br.edu.imepac.message.manager.views;

import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;

public abstract class BaseForm extends javax.swing.JInternalFrame {

    public void showErrorInternal(String message) {
        JOptionPane.showMessageDialog(null, "Um erro inesperado aconteceu! Comunique o administrador do sistema.\n" + message, "Erro interno", ERROR_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Erro interno", ERROR_MESSAGE);
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Sucesso!", INFORMATION_MESSAGE);
    }

    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Sucesso!", WARNING_MESSAGE);
    }

    public int showConfirmYesOrNoDialog(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Confirmação!", YES_NO_OPTION, QUESTION_MESSAGE);
    }

}
