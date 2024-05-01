package com.deusto.app.client.UI;

import javax.swing.*;

import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.data.domain.User;
import com.deusto.app.server.pojo.UserData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private User user;
    
    public LoginUI() {
    	// Crear un usuario de ejemplo
        user = new User("12345678A", "root", "UsuarioTest", "ApellidoTest", "01-01-2000", "123456789", "test@mail.es");

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        initComponents();
        setupListeners();
    }
    
    
    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Espacio en blanco para mantener el diseño
        panel.add(loginButton);
        
        add(panel);
        setVisible(true);
    }

    private void setupListeners() {
    	loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Aquí llamar a algún método para manejar la lógica de inicio de sesión
                
                // Verificar si el usuario existe
                if (user != null && user.getDni().equals(username) && user.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(LoginUI.this, "Usuario aceptado");
                    
                   UserData userData = new UserData();
                   userData.setDni(username);
                   userData.setPassword(password);
                   
                   UserController.getInstance().loginUser(userData);
                   
                   // new DisplayStationsUI();
                                       
                    // ABRIR DISPLAY STATIONS con el token que se acaba de meter al usercontroller
                   
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, "Usuario o contraseña incorrectos");
                }
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
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginUI loginUI = new LoginUI();
                loginUI.showLogin();
            }
        });
    }
}
