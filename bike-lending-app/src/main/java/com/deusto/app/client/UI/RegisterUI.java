package com.deusto.app.client.UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class RegisterUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public RegisterUI() {
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 5, 5)); // Add horizontal and vertical gaps
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding around the panel

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton registerButton = new JButton("Register");
        statusLabel = new JLabel();

        // Add padding around individual components
        usernameLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        passwordLabel.setBorder(new EmptyBorder(0, 10, 0, 0));
        registerButton.setBorder(new EmptyBorder(5, 5, 5, 5));

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // Add register logic here
                if (username.isEmpty() || passwordChars.length == 0) {
                    statusLabel.setText("Please enter username and password");
                } else {
                    statusLabel.setText("Registering...");
                    // Perform registration process here
                    System.out.println("Username: " + username);
                    System.out.println("Password: " + password);
                    // Simulate registration process completion
                    statusLabel.setText("Registration successful");
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(registerButton);
        panel.add(statusLabel); // Status label for feedback

        add(panel);
        setResizable(false); // Disable window resizing
        setVisible(true);
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
