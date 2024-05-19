package com.deusto.app.client.UI;

import javax.swing.*;

import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.UserData;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField dniField, nameField, passwordField, surnameField, dateOfBirthField, phoneField, mailField, repeatPassword;

    public RegisterUI() {
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Make the window fullscreen
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(0, 150, 136)); // Same background color as login
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding fields and labels
        dniField = new JTextField(20);
        nameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        surnameField = new JTextField(20);
        dateOfBirthField = new JTextField(20);
        phoneField = new JTextField(20);
        mailField = new JTextField(20);
        repeatPassword= new JPasswordField(20);
        
        panel.add(new JLabel("DNI:"), gbc);
        panel.add(dniField, gbc);
        panel.add(new JLabel("Name:"), gbc);
        panel.add(nameField, gbc);
        panel.add(new JLabel("Surname:"), gbc);
        panel.add(surnameField, gbc);
        panel.add(new JLabel("Date of Birth:"), gbc);
        panel.add(dateOfBirthField, gbc);
        panel.add(new JLabel("Phone:"), gbc);
        panel.add(phoneField, gbc);
        panel.add(new JLabel("Mail:"), gbc);
        panel.add(mailField, gbc);
        panel.add(new JLabel("Password:"), gbc);
        panel.add(passwordField, gbc);
        panel.add(new JLabel("RepeatPassword:"), gbc);
        panel.add(repeatPassword, gbc);

        JButton registerButton = new JButton("Registrarse");
        JButton backButton = new JButton("Volver");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();  // Call a method to handle user registration
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginUI();  // Open LoginUI and close current window
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(buttonPanel, gbc);

        add(panel);
        setVisible(true);
    }

    private void registerUser() {
    	if(passwordField.getText().equals(repeatPassword.getText())) {
    		// Logic to register user
            UserData userData = new UserData();
            // Assume UserData class has these fields and set them
            userData.setDni(dniField.getText());
            userData.setName(nameField.getText());
            userData.setPassword(new String(passwordField.getText()));
            userData.setSurname(surnameField.getText());
            userData.setDateOfBirth(dateOfBirthField.getText());
            userData.setPhone(phoneField.getText());
            userData.setMail(mailField.getText());
            userData.setAdmin(false);

            boolean register = UserController.getInstance().registerUser(userData);
            if (register) {
                JOptionPane.showMessageDialog(this, "Usuario registrado");
                dispose();
                new LoginUI();  // Open Login window
            } else {
                JOptionPane.showMessageDialog(this, "Error en el registro");
            }
    	} else {
    		JOptionPane.showMessageDialog(this, "Las contraseñas no son iguales!");
    	}
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegisterUI();
            }
        });
    }
}

