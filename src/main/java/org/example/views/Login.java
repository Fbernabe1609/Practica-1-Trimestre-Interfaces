package org.example.views;

import org.example.controllers.DbController;
import org.example.controllers.UserController;
import org.example.controllers.ValidationData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class Login extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel bodyPanel;
    private JPanel buttonsPanel;
    private JPanel contentButtonPanel;

    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    URL url = classloader.getResource("error.gif");
    ImageIcon icono = new ImageIcon(url);
    public Login() {

        UIManager.put("OptionPane.messageForeground", Color.red);
        UIManager.put("Button.background", buttonOK.getBackground());
        UIManager.put("Button.foreground", Color.white);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (ValidationData.checkFields(usernameField.getText(), String.valueOf(passwordField.getPassword()))) {
            String password = String.valueOf(passwordField.getPassword()).replaceAll(" ,]", "");
            password = password.replace("[", "");
            if (DbController.searchUser(usernameField.getText(), password)) {
                UserController.createUser(usernameField.getText(), password);
                dispose();
                StartViews.startDataViewer();
            } else {
                JOptionPane.showMessageDialog(this, "No se ha encontrado algún usuario con los datos introducidos", "Error", JOptionPane.ERROR_MESSAGE, icono);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }

    public static void start() {
        Login dialog = new Login();
        dialog.pack();
        dialog.setLocation(StartViews.appFrame.getLocation());
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
