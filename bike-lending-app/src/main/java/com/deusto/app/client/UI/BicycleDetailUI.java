package com.deusto.app.client.UI;

import javax.swing.*;

import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.BicycleData;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import java.awt.*;

public class BicycleDetailUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel idLabel, acquisitionDateLabel, typeLabel, isAvailableLabel, stationLabel;
    private JLabel idField, acquisitionDateField, typeField, isAvailableField, stationField;

    public BicycleDetailUI(int bikeId) {
    	
        super("Bicycle Details");
        
        BicycleData bike = BikeController.getInstance().getBikeDetails(bikeId, UserController.getInstance().getToken());
        
        idLabel = new JLabel("ID:");
        idField = new JLabel();
        idField.setText(String.valueOf(bike.getId()));

        acquisitionDateLabel = new JLabel("Acquisition Date:");
        acquisitionDateField = new JLabel();
        acquisitionDateField.setText(bike.getAcquisitionDate());

        typeLabel = new JLabel("Type:");
        typeField = new JLabel();
        typeField.setText(bike.getType());

        isAvailableLabel = new JLabel("Is Available:");
        isAvailableField = new JLabel();
        isAvailableField.setText(String.valueOf(bike.isAvailable()));

        stationLabel = new JLabel("Station:");
        stationField = new JLabel();
        stationField.setText(String.valueOf(bike.getStationId()));

        JPanel panel = new JPanel(new GridLayout(5, 2));
        
        
        panel.add(idLabel);
        panel.add(idField);
        panel.add(acquisitionDateLabel);
        panel.add(acquisitionDateField);
        panel.add(typeLabel);
        panel.add(typeField);
        panel.add(isAvailableLabel);
        panel.add(isAvailableField);
        panel.add(stationLabel);
        panel.add(stationField);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

}
