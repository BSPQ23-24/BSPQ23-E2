package com.deusto.app.client.UI;

import javax.swing.*;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.deusto.app.client.controller.AdminController;
import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.LoanController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.LoanData;
import com.deusto.app.server.pojo.StationData;

import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class BicycleDetailUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JComboBox<BicycleData> bikes;
    private JComboBox<String> startHour, endHour;
    private JDatePickerImpl loanDate;
    private ResourceBundle translation;

    public BicycleDetailUI(int stationID) {
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
    	        
    	        setupDatePicker(); 


    	        // Title with icons
    	        JPanel titlePanel = new JPanel();
    	        titlePanel.setBackground(new Color(0, 150, 136));
    	        ImageIcon icon = new ImageIcon("C:/Users/Usuario/Documents/BSPQ23-E2/bike-lending-app/src/main/resources/logo.png");
    	        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Increased size
    	        ImageIcon scaledIcon = new ImageIcon(scaledImage);
    	        titlePanel.add(new JLabel(scaledIcon));
    	        List<StationData> stations = BikeController.getInstance().displayStations(UserController.getToken());
    	        StationData sta=null;
    	        for (StationData station : stations) {
					if(station.getId()==stationID)sta=station;
				}
    	        JLabel titleLabel=new JLabel("Alquila Bicicleta de " + sta.getLocation(), SwingConstants.CENTER);
    	        titleLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Bigger font size
    	        titleLabel.setForeground(Color.WHITE); // Set text color to white
    	        titlePanel.add(titleLabel);
    	        titlePanel.add(new JLabel(scaledIcon));
    	        panel.add(titlePanel, gbc);
		       
    	        bikes=new JComboBox<BicycleData>();
    	        List<BicycleData> bikesAvailable =BikeController.getInstance().getAvailableBikesInStation(stationID,UserController.getToken());
    	        for (BicycleData bike : bikesAvailable) {
					bikes.addItem(bike);
				}
    	        
    	        // Adding fields
    	        setupTimeFields(); 
    	        addField(panel, "Start Hour:", startHour, gbc);
    	        addField(panel, "End Hour:", endHour, gbc);
    	        addField(panel, "Bikes Available:", bikes, gbc);
    	        addField(panel, "Loan Date:", loanDate, gbc);
    	        


    	        // Buttons
    	        JButton addButton = new JButton("Alquilar");
    	        JButton backButton = new JButton("Volver");
    	        addButton.setBackground(new Color(255, 114, 118));

       
    	        addButton.addActionListener(e -> {
    	            if (validateFields()) {
    	                createLoan(bikesAvailable);
    	            } else {
    	                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
    	            }
    	        });
    	        
    	        
    	        backButton.addActionListener(e -> {
    	        	new DisplayStationsUI();
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



		    private void setupTimeFields() {
		        startHour = new JComboBox<>();
		        endHour = new JComboBox<>();
		        for (int i = 0; i < 24; i++) {
		            String time = String.format("%02d:00", i);
		            startHour.addItem(time);
		            endHour.addItem(time);
		        }
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
    	        return bikes.getSelectedItem() != null && validateTimeDifference()
    	        		&&  loanDate.getModel().getValue() != null;
    	    }

    	    private void createLoan(List<BicycleData> bikesAvailable) {
    	    	if(validateFields()) {
        	        // Assume the UserData class and UserController handle registration
    	    		LoanData loanData = new LoanData();
    	            // Assume UserData class has these fields and set them
    	    		
    	            loanData.setUserDni(UserController.getInstance().getUser(UserController.getToken()).getDni());
    	    		loanData.setEndHour(endHour.getSelectedItem().toString());
    	    		loanData.setStartHour(startHour.getSelectedItem().toString());
    	            for (BicycleData bikeData : bikesAvailable) {
						if(bikeData.equals(bikes.getSelectedItem())) {
							loanData.setBicycleId(bikeData.getId());
						}
					}
    	            
    	            
    	            
    	            java.util.Date date = (java.util.Date) loanDate.getModel().getValue();
    	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formatting the date as a string in the "yyyy-MM-dd" format
    	            String formattedDate = sdf.format(date); // Convert Date to String
    	            loanData.setLoanDate(formattedDate);

    	            boolean createLoan = LoanController.getInstance().createLoan(loanData);
    	            if (createLoan) {
    	            	JOptionPane.showMessageDialog(BicycleDetailUI.this, "Bicicleta Añadida");
    	            	AdminController.getInstance().disableBike(loanData.getBicycleId(), UserController.getToken());
    	            	new DisplayStationsUI();
    	                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	                setVisible(false);
    	                dispose();  // Open Login window
    	            } else {
    	            	JOptionPane.showMessageDialog(BicycleDetailUI.this, "Problema añadiendo bicicleta!");
    	            }
    	    	} else {
    	    		JOptionPane.showMessageDialog(this, "Rellena todo los campos!");
    	    	}
    	        
    	    }
    	    
    	    private void setupDatePicker() {
    	    	UtilDateModel model = new UtilDateModel();
    	        Properties properties = new Properties();
    	        properties.put("text.today", "Today");
    	        properties.put("text.month", "Month");
    	        properties.put("text.year", "Year");

    	        model.setDate(Calendar.getInstance().get(Calendar.YEAR),
    	                      Calendar.getInstance().get(Calendar.MONTH),
    	                      Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    	        model.setSelected(true);

    	        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
    	        loanDate = new JDatePickerImpl(datePanel, new DateComponentFormatter());

    	        // You can't directly set a range, but you can set a validator or listener if needed
    	        loanDate.addActionListener(e -> {
    	            java.util.Date selectedDate =  (java.util.Date) loanDate.getModel().getValue();
    	            Calendar selectedCalendar = Calendar.getInstance();
    	            if (selectedDate != null) {
    	                selectedCalendar.setTime(selectedDate);
    	            }
    	            Calendar today = Calendar.getInstance();
    	            if (selectedCalendar.before(today)) {
    	                // Reset the selection or show an error
    	                loanDate.getModel().setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
    	                JOptionPane.showMessageDialog(this, "Please select today's date or a future date.");
    	            }
    	        });
    	    }
    	    
    	    private boolean validateTimeDifference() {
    	        int start = Integer.parseInt(startHour.getSelectedItem().toString().substring(0, 2));
    	        int end = Integer.parseInt(endHour.getSelectedItem().toString().substring(0, 2));
    	        return Math.abs(end - start) <= 24;
    	    }

    	    public static void main(String[] args) {
    	        SwingUtilities.invokeLater(RegisterUI::new);
    	    }
    	}
