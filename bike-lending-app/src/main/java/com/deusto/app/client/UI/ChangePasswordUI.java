package com.deusto.app.client.UI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.deusto.app.client.controller.UserController;

class ChangePasswordUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JButton changeButton;

    public ChangePasswordUI() {
        setTitle("Change Password");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        setSize(300, 200);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel oldPasswordLabel = new JLabel("Old Password:");
        JLabel newPasswordLabel = new JLabel("New Password:");
        usernameField = new JTextField();
        oldPasswordField = new JPasswordField();
        newPasswordField = new JPasswordField();
        changeButton = new JButton("Change");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(oldPasswordLabel);
        panel.add(oldPasswordField);
        panel.add(newPasswordLabel);
        panel.add(newPasswordField);
        panel.add(new JLabel()); // Empty space
        panel.add(changeButton);

        add(panel);
        setVisible(true);

        // Setup listener for the change button
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());

                // Change password
                boolean passwordChanged = UserController.getInstance().changePassword(username, oldPassword, newPassword);

                // Display message to user based on password change result
                if (passwordChanged) {
                    JOptionPane.showMessageDialog(ChangePasswordUI.this, "Password changed successfully.");
                    dispose(); // Close this window
                } else {
                    JOptionPane.showMessageDialog(ChangePasswordUI.this, "Failed to change password. Please try again.");
                }
            }
        });
    }
    
    public static void main(String[] args) {
    	new ChangePasswordUI();
    }
}