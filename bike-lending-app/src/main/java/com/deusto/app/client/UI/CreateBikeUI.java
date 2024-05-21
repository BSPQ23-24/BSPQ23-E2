package com.deusto.app.client.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.List;

import java.util.Properties;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.deusto.app.client.controller.AdminController;
import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationData;


public class CreateBikeUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> type, station;
    private JDatePickerImpl acquisitionDate;
    
	/**
	 * Constructor for the CreateBikeUI class.
	 * Sets up the UI components for creating a new bike.
	 */
    public CreateBikeUI() {
       
        setTitle("Crear Bicicleta");
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
    	        acquisitionDate = new JDatePickerImpl(datePanel, new DateComponentFormatter());


    	        // Title with icons
    	        JPanel titlePanel = new JPanel();
    	        titlePanel.setBackground(new Color(0, 150, 136));
    	        ImageIcon icon = new ImageIcon("C:/Users/Usuario/Documents/BSPQ23-E2/bike-lending-app/src/main/resources/logo.png");
    	        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Increased size
    	        ImageIcon scaledIcon = new ImageIcon(scaledImage);
    	        titlePanel.add(new JLabel(scaledIcon));
    	        JLabel titleLabel=new JLabel("Nueva Bicicleta!", SwingConstants.CENTER);
    	        titleLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Bigger font size
    	        titleLabel.setForeground(Color.WHITE); // Set text color to white
    	        titlePanel.add(titleLabel);
    	        titlePanel.add(new JLabel(scaledIcon));
    	        panel.add(titlePanel, gbc);
    	        
    	        type=new JComboBox<String>();
    	        type.addItem("Mountain");
    	        type.addItem("Road");
    	        type.addItem("Hybrid");
    	        type.addItem("Electric");
		       
    	        station=new JComboBox<String>();
    	        List<StationData> stations =BikeController.getInstance().displayStations(UserController.getToken());
    	        for (StationData stationData : stations) {
					station.addItem(stationData.getLocation());
				}
    	        
    	        // Adding fields
    	        addField(panel, "Bike Type :", type, gbc);
    	        addField(panel, "Station:", station, gbc);
    	        addField(panel, "Acquisition Date:", acquisitionDate, gbc);
    	        


    	        // Buttons
    	        JButton addButton = new JButton("Crear Bici");
    	        JButton backButton = new JButton("Volver");
    	        addButton.setBackground(new Color(255, 114, 118));

       
    	        addButton.addActionListener(e -> {
    	            if (validateFields()) {
    	                addBike(stations);
    	            } else {
    	                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
    	            }
    	        });
    	        
    	        
    	        backButton.addActionListener(e -> {
    	        	new AdminUI();
	                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                setVisible(false);
	                dispose();
    	        });


    	        JPanel buttonPanel = new JPanel();
    	        buttonPanel.setBackground(new Color(0, 150, 136));  
    	        buttonPanel.add(addButton);
    	        buttonPanel.add(backButton);
    	        panel.add(buttonPanel, gbc);

    	        add(panel);
    	        setVisible(true);
    	    }



    	    /**
			 * Adds a label and its corresponding input field to the provided panel.
			 *
			 * @param panel The panel to which the label and field will be added.
			 * @param label The text for the label.
			 * @param field The input field component to be added.
			 * @param gbc The GridBagConstraints object to manage the layout constraints.
			 */
    	    private void addField(JPanel panel, String label, Component field, GridBagConstraints gbc) {
    	        gbc.gridwidth = GridBagConstraints.RELATIVE; // Label
    	        JLabel jLabel= new JLabel(label);
    	        jLabel.setForeground(Color.WHITE);
    	        panel.add(jLabel, gbc);
    	        gbc.gridwidth = GridBagConstraints.REMAINDER; // Field
    	        panel.add(field, gbc);
    	    }
    	    
			/**
			 * Validates that all required fields are filled.
			 *
			 * @return true if all fields (station, type, acquisition date) are not null; false otherwise.
			 */
    	    private boolean validateFields() {
    	        return station.getSelectedItem() != null && type.getSelectedItem() != null 
    	        		&&  acquisitionDate.getModel().getValue() != null;
    	    }
			
			/**
			 * Adds a new bike based on user input if all fields are validated.
			 *
			 * @param stations The list of available stations.
			 */
    	    private void addBike(List<StationData> stations) {
    	    	if(validateFields()) {
        	        // Assume the UserData class and UserController handle registration
    	    		BicycleData bikeData = new BicycleData();
    	            // Assume UserData class has these fields and set them
    	            bikeData.setAvailable(true);
    	            for (StationData stationData : stations) {
						if(stationData.getLocation().equals(station.getSelectedItem().toString())) {
							bikeData.setStationId(stationData.getId());
						}
					}
    	            
    	            bikeData.setType(type.getSelectedItem().toString());
    	            
    	            java.util.Date date = (java.util.Date) acquisitionDate.getModel().getValue();
    	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formatting the date as a string in the "yyyy-MM-dd" format
    	            String formattedDate = sdf.format(date); // Convert Date to String
    	            bikeData.setAcquisitionDate(formattedDate);

    	            boolean add = AdminController.getInstance().addBike(bikeData, UserController.getToken());
    	            if (add) {
    	            	JOptionPane.showMessageDialog(CreateBikeUI.this, "Bicicleta Añadida");
    	            	new AdminUI();
    	                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	                setVisible(false);
    	                dispose();  // Open Login window
    	            } else {
    	            	JOptionPane.showMessageDialog(CreateBikeUI.this, "Problema añadiendo bicicleta!");
    	            }
    	    	} else {
    	    		JOptionPane.showMessageDialog(this, "Rellena todo los campos!");
    	    	}
    	        
    	    }

    	    public static void main(String[] args) {
    	        SwingUtilities.invokeLater(RegisterUI::new);
    	    }
    	}
