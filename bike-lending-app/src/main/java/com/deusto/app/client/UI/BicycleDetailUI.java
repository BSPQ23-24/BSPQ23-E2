package com.deusto.app.client.UI;

import javax.swing.*;
import java.awt.*;

public class BicycleDetailUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel idLabel, acquisitionDateLabel, typeLabel, isAvailableLabel, stationLabel;
    private JLabel idField, acquisitionDateField, typeField, isAvailableField, stationField;

    public BicycleDetailUI() {
        super("Bicycle Details");
        idLabel = new JLabel("ID:");
        idField = new JLabel();
        idField.setText(String.valueOf(0));

        acquisitionDateLabel = new JLabel("Acquisition Date:");
        acquisitionDateField = new JLabel();
        acquisitionDateField.setText("Aquisition Date");

        typeLabel = new JLabel("Type:");
        typeField = new JLabel();
        typeField.setText("Type");

        isAvailableLabel = new JLabel("Is Available:");
        isAvailableField = new JLabel();
        isAvailableField.setText(String.valueOf(0));

        stationLabel = new JLabel("Station:");
        stationField = new JLabel();
        stationField.setText("Station Name");

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

    public static void main(String[] args) {
        new BicycleDetailUI();
    }
}
