package com.deusto.app.client.UI;

import javax.swing.*;

import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.UserData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URISyntaxException;


import java.util.Locale;
import java.util.ResourceBundle;

public class LoginUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton forgotPasswordButton;

    private ResourceBundle translation;
    
    public LoginUI() {
        this.translation = ResourceBundle.getBundle("translation", Locale.getDefault());

        setTitle(translation.getString("title_Login"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        initComponents();
        setupListeners();
    }

    private void initComponents() {
        // Make the window fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Main panel setup
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(0, 150, 136)); // Background color

        // Title label at the top
        JLabel titleLabel = new JLabel(translation.getString("welcome_title"), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48)); // Set the font size and style
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Center Panel for logo, text fields, and buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Adding logo
        JLabel logoLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon("C:/Users/Usuario/Documents/BSPQ23-E2/bike-lending-app/src/main/resources/logo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH); // Increased size
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        logoLabel.setIcon(scaledIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setOpaque(false);
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        centerPanel.add(logoPanel, BorderLayout.NORTH);

     // Panel for DNI and its label
        JPanel dniPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        dniPanel.setOpaque(false);
        JLabel dniLabel = new JLabel(translation.getString("Username") + ":");
        dniLabel.setForeground(Color.WHITE);
        dniPanel.add(dniLabel);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        dniPanel.add(usernameField);
        centerPanel.add(dniPanel);

        // Panel for Password and its label
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        passwordPanel.setOpaque(false);
        JLabel passwordLabel = new JLabel(translation.getString("Password") + ":");
        passwordLabel.setForeground(Color.WHITE);
        passwordPanel.add(passwordLabel);

        passwordField = new JPasswordField();

        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordPanel.add(passwordField);
        centerPanel.add(passwordPanel);

        // Buttons
        registerButton = new JButton(translation.getString("Register"));
        loginButton = new JButton(translation.getString("Login"));
        forgotPasswordButton = new JButton("<html><font color='white'>" + translation.getString("forgot_password") + "</font></html>");
        loginButton.setBackground(new Color(255, 114, 118)); // New button color
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setOpaque(false);
        forgotPasswordButton.setContentAreaFilled(false);
        forgotPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        centerPanel.add(Box.createVerticalStrut(10)); // Space between fields and buttons
        centerPanel.add(registerButton);
        centerPanel.add(Box.createVerticalStrut(5)); // Reduced space
        centerPanel.add(loginButton);
        centerPanel.add(Box.createVerticalStrut(5)); // Reduced space
        centerPanel.add(forgotPasswordButton);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Add the main panel to the window
        add(mainPanel);
        setVisible(true);
    }


    private void setupListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                UserData userData = new UserData();
                userData.setDni(username);
                userData.setPassword(password);

                boolean login = UserController.getInstance().loginUser(userData);

                if (login) {
                    JOptionPane.showMessageDialog(LoginUI.this, translation.getString("msg_usr_accepted"));
                   
                    new DisplayStationsUI();
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setVisible(false);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, translation.getString("msg_usr_pwd_incorrect"));
                }
            }
        });

        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt user for old and new password
            	String username = JOptionPane.showInputDialog(LoginUI.this, translation.getString("enter_usr_name") + ":");
                String oldPassword = JOptionPane.showInputDialog(LoginUI.this, translation.getString("enter_old_pwd") + ":");
                String newPassword = JOptionPane.showInputDialog(LoginUI.this, translation.getString("enter_new_pwd") + ":");

                boolean passwordChanged = UserController.getInstance().changePassword(username, oldPassword, newPassword);

                if (passwordChanged) {
                    JOptionPane.showMessageDialog(LoginUI.this, translation.getString("msg_pwd_change_success"));
                } else {
                    JOptionPane.showMessageDialog(LoginUI.this, translation.getString("msg_pwd_change_fail"));
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterUI();
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(false);
                dispose();
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
        new LoginUI();
    }
}
