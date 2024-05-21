package com.deusto.app.client.UI;

import javax.swing.*;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.UserData;

import java.awt.*;



import java.util.Locale;
import java.util.ResourceBundle;


import java.text.SimpleDateFormat;
import java.util.Properties;


public class RegisterUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField dniField, nameField, passwordField, surnameField, phoneField, mailField, repeatPassword;
    private JDatePickerImpl dateOfBirthPicker;
    private ResourceBundle translation;

    public RegisterUI() {
        this.translation = ResourceBundle.getBundle("translation", Locale.getDefault());
        setTitle(translation.getString("title_Register"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Make the window fullscreen
        setLocationRelativeTo(null);

    	        JPanel panel = new JPanel(new GridBagLayout());
    	        panel.setBackground(new Color(0, 150, 136)); // Same background color as login
    	        GridBagConstraints gbc = new GridBagConstraints();
    	        gbc.gridwidth = GridBagConstraints.REMAINDER;
    	        gbc.fill = GridBagConstraints.HORIZONTAL;
    	        gbc.insets = new Insets(10, 50, 10, 50);
    	        
    	     // Setup date picker
    	        UtilDateModel model = new UtilDateModel();
    	        Properties p = new Properties();
    	        p.put("text.today", "Today");
    	        p.put("text.month", "Month");
    	        p.put("text.year", "Year");
    	        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
    	        dateOfBirthPicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());


    	        // Title with icons
    	        JPanel titlePanel = new JPanel();
    	        titlePanel.setBackground(new Color(0, 150, 136));
    	        ImageIcon icon = new ImageIcon("C:/Users/Usuario/Documents/BSPQ23-E2/bike-lending-app/src/main/resources/logo.png");
    	        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Increased size
    	        ImageIcon scaledIcon = new ImageIcon(scaledImage);
    	        titlePanel.add(new JLabel(scaledIcon));
    	        JLabel titleLabel=new JLabel(translation.getString("title_register"), SwingConstants.CENTER);
    	        titleLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Bigger font size
    	        titleLabel.setForeground(Color.WHITE); // Set text color to white
    	        titlePanel.add(titleLabel);
    	        titlePanel.add(new JLabel(scaledIcon));
    	        panel.add(titlePanel, gbc);
		       
    	        // Adding fields
    	        addField(panel, translation.getString("Username") + ":", dniField = new JTextField(20), gbc);
    	        addField(panel, translation.getString("Name") + ":", nameField = new JTextField(20), gbc);
    	        addField(panel, translation.getString("Surname") + ":", surnameField = new JTextField(20), gbc);
    	        addField(panel, translation.getString("Nacimiento") + ":", dateOfBirthPicker, gbc);
    	        addField(panel, translation.getString("Phone") + ":", phoneField = new JTextField(20), gbc);
    	        addField(panel, translation.getString("Mail") + ":", mailField = new JTextField(20), gbc);
    	        addField(panel, translation.getString("Password") + ":", passwordField = new JPasswordField(20), gbc);
    	        addField(panel, translation.getString("repeat_password") + ":", repeatPassword = new JPasswordField(20), gbc);


    	        // Buttons
    	        JButton registerButton = new JButton(translation.getString("register_act"));
    	        JButton backButton = new JButton(translation.getString("Volver") + ":");
    	        registerButton.setBackground(new Color(255, 114, 118));

       
    	        registerButton.addActionListener(e -> {
    	            if (validateFields()) {
    	                registerUser();
    	            } else {
    	                JOptionPane.showMessageDialog(this, translation.getString("msg_fill_fields"), "Error", JOptionPane.ERROR_MESSAGE);
    	            }
    	        });
    	        
    	        
    	        backButton.addActionListener(e -> {
    	        	new LoginUI();
    	            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	            setVisible(false);
    	            dispose();
    	        });


    	        JPanel buttonPanel = new JPanel();
    	        buttonPanel.setBackground(new Color(0, 150, 136));  
    	        buttonPanel.add(registerButton);
    	        buttonPanel.add(backButton);
    	        panel.add(buttonPanel, gbc);

    	        add(panel);
    	        setVisible(true);
    	    }



    	    
    	    private void addField(JPanel panel, String label, Component field, GridBagConstraints gbc) {
    	        gbc.gridwidth = GridBagConstraints.RELATIVE; // Label
    	        JLabel jLabel= new JLabel(label);
    	        jLabel.setForeground(Color.WHITE);
    	        panel.add(jLabel, gbc);
    	        gbc.gridwidth = GridBagConstraints.REMAINDER; // Field
    	        panel.add(field, gbc);
    	    }
    	    
    	    private boolean validateFields() {
    	        return !dniField.getText().isEmpty() && !nameField.getText().isEmpty() && !passwordField.getText().isEmpty() &&
    	                !surnameField.getText().isEmpty() && dateOfBirthPicker.getModel().getValue() != null && !phoneField.getText().isEmpty() &&
    	                !mailField.getText().isEmpty() && !repeatPassword.getText().isEmpty();
    	    }

    	    private void registerUser() {
    	    	
    	    		if (!passwordField.getText().equals(repeatPassword.getText())) {
        	            JOptionPane.showMessageDialog(this, translation.getString("msg_pass_equal"), "Error", JOptionPane.ERROR_MESSAGE);
        	            return;
        	        }
        	        // Assume the UserData class and UserController handle registration
    	    		UserData userData = new UserData();
    	            // Assume UserData class has these fields and set them
    	            userData.setDni(dniField.getText());
    	            userData.setName(nameField.getText());
    	            userData.setPassword(new String(passwordField.getText()));
    	            userData.setSurname(surnameField.getText());
    	            
    	            java.util.Date date = (java.util.Date) dateOfBirthPicker.getModel().getValue();
    	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formatting the date as a string in the "yyyy-MM-dd" format
    	            String formattedDate = sdf.format(date); // Convert Date to String
    	            userData.setDateOfBirth(formattedDate);
    	            
    	            userData.setPhone(phoneField.getText());
    	            userData.setMail(mailField.getText());
    	            userData.setAdmin(false);

    	            boolean register = UserController.getInstance().registerUser(userData);
    	            if (register) {
    	            	JOptionPane.showMessageDialog(RegisterUI.this, translation.getString("msg_usr_registered"));
    	                dispose();
    	                new LoginUI();  // Open Login window
    	            } else {
    	            	JOptionPane.showMessageDialog(RegisterUI.this, translation.getString("msg_register_error"));
    	            }
    	    	
    	        
    	    }

    	    public static void main(String[] args) {
    	        SwingUtilities.invokeLater(RegisterUI::new);
    	    }
    	}
