package com.deusto.app.client.UI;

import javax.swing.*;

import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.UserData;

import java.awt.*;
import java.awt.event.*;

import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JPasswordField passwordField;

    private ResourceBundle translation;

    public RegisterUI() {
        this.translation = ResourceBundle.getBundle("translation", Locale.getDefault());
        setTitle(translation.getString("title_Register"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel(translation.getString("Username") + ":");
        JLabel passwordLabel = new JLabel(translation.getString("Password") + ":");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton registerButton = new JButton(translation.getString("register_act"));

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // register logic
                UserData userData = new UserData();
                userData.setDni(username);
                userData.setPassword(password);

                boolean register = UserController.getInstance().registerUser(userData);
                                
                if (register) {
                    JOptionPane.showMessageDialog(RegisterUI.this, translation.getString("msg_usr_registered"));
                   
                    new LoginUI();
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setVisible(false);
                    dispose();
                                       
                    // ABRIR DISPLAY STATIONS con el token que se acaba de meter al usercontroller
                   
                } else {
                    JOptionPane.showMessageDialog(RegisterUI.this, translation.getString("msg_register_error"));
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label to occupy space
        panel.add(new JLabel()); // Empty label to occupy space
        panel.add(new JLabel()); // Empty label to occupy space
        panel.add(registerButton);

        add(panel);
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
