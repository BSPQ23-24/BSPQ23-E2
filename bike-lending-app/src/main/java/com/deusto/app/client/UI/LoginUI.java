package com.deusto.app.client.UI;

import com.deusto.app.server.data.domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginUI() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        initComponents();
        setupListeners();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel lblUsername = new JLabel("Username:");
        txtUsername = new JTextField();

        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField();

        btnLogin = new JButton("Login");

        panel.add(lblUsername);
        panel.add(txtUsername);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(new JLabel()); // Espacio en blanco
        panel.add(btnLogin);

        add(panel);
    }

    private void setupListeners() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Crear un objeto User (solo para demostración)
                User user = new User("hola", "hola", "Test", "User", "01-01-2000", "123456789", "test@mail.es");
                System.out.println("usuario aceptado: " + user);

                // Aquí llamar a algún método para manejar la lógica de inicio de sesión

            }
        });
    }

    public void showLogin() {
        setVisible(true);
    }

    public void hideLogin() {
        setVisible(false);
    }

    public String getUsername() {
        return txtUsername.getText();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginUI loginUI = new LoginUI();
                loginUI.showLogin();
            }
        });
    }
}
