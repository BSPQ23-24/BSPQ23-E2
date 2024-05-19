package com.deusto.app.client.UI;

import javax.swing.*;

import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.BicycleData;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import java.awt.*;

import java.util.Locale;
import java.util.ResourceBundle;

public class BicycleDetailUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel idLabel, acquisitionDateLabel, typeLabel, isAvailableLabel, stationLabel;
    private JLabel idField, acquisitionDateField, typeField, isAvailableField, stationField;

    private ResourceBundle translation;

    private static String getBicycleDetailsTitle() {
        ResourceBundle translation = ResourceBundle.getBundle("translation", Locale.getDefault());
        return translation.getString("Bicycle_details");
    }

    public BicycleDetailUI(int bikeId) {
        super(getBicycleDetailsTitle());

        this.translation = ResourceBundle.getBundle("translation", Locale.getDefault());

        BicycleData bike = BikeController.getInstance().getBikeDetails(bikeId, UserController.getInstance().getToken());
        
        idLabel = new JLabel(translation.getString("bike_ID") + ":");
        idField = new JLabel();
        idField.setText(String.valueOf(bike.getId()));

        acquisitionDateLabel = new JLabel(translation.getString("acquisition_date") + ":");
        acquisitionDateField = new JLabel();
        acquisitionDateField.setText(bike.getAcquisitionDate());

        typeLabel = new JLabel(translation.getString("bike_type") + ":");
        typeField = new JLabel();
        typeField.setText(bike.getType());

        isAvailableLabel = new JLabel(translation.getString("bike_is_available") + ":");
        isAvailableField = new JLabel();
        isAvailableField.setText(String.valueOf(bike.isAvailable()));

        stationLabel = new JLabel(translation.getString("Station") + ":");
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
