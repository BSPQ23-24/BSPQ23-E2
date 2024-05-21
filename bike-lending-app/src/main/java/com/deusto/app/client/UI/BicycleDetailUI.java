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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;


public class BicycleDetailUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JComboBox<BicycleData> bikes;
    private JComboBox<String> startHour, endHour;
    private JDatePickerImpl loanDate;

	/**
	 * Constructs a new BicycleDetailUI with the specified station ID.
	 * Sets up the UI components for renting a bike.
	 * Displays station information and available bikes for rental.
	 * Allows the user to select the start and end time, bike, and loan date.
	 * Provides buttons for renting a bike and returning to the previous screen.
	 */
    public BicycleDetailUI(int stationID) {
        
        setTitle("Alquilar Bicicleta");
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
    	        
				StationData sta=new StationData();
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


			/**
			 * Sets up the time fields (startHour and endHour) as JComboBoxes.
			 * Populates the JComboBoxes with hours from 00:00 to 23:00.
			 */
		    private void setupTimeFields() {
		        startHour = new JComboBox<>();
		        endHour = new JComboBox<>();
		        for (int i = 0; i < 24; i++) {
		            String time = String.format("%02d:00", i);
		            startHour.addItem(time);
		            endHour.addItem(time);
		        }
		    }
		    
			/**
			 * Adds a labeled field to the specified panel using GridBagConstraints.
			 * @param panel The panel to which the field will be added.
			 * @param label The label for the field.
			 * @param field The component representing the field.
			 * @param gbc The GridBagConstraints used for layout.
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
			 * Validates the selected bike, time difference, and loan date.
			 * @return true if all fields are valid, false otherwise.
			 */
    	    private boolean validateFields() {
    	        return bikes.getSelectedItem() != null && validateTimeDifference()
    	        		&&  loanDate.getModel().getValue() != null;
    	    }
			/**
			 * Creates a new loan based on the selected bike, start and end hour, and loan date.
			 * If all fields are valid, the loan is created, and the bike is disabled. Otherwise, an error message is displayed.
			 * @param bikesAvailable the list of available bikes to choose from.
			 */
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

    	    /**
			 * Sets up the date picker component for selecting the loan date.
			 * The date picker is initialized with the current date and allows selection of future dates.
			 */
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
    	    
			/**
			 * Validates the time difference between the start and end hours selected by the user.
			 * Ensures that the time difference is within 24 hours.
			 *
			 * @return true if the time difference is within 24 hours, false otherwise
			 */
    	    private boolean validateTimeDifference() {
    	        int start = Integer.parseInt(startHour.getSelectedItem().toString().substring(0, 2));
    	        int end = Integer.parseInt(endHour.getSelectedItem().toString().substring(0, 2));
    	        return Math.abs(end - start) <= 24;
    	    }

    	    public sStatic void main(String[] args) {
    	        SwingUtilities.invokeLater(RegisterUI::new);
    	    }
    	}
