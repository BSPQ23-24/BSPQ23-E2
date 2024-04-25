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

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10); // Add padding

        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(acquisitionDateLabel, gbc);
        gbc.gridx = 1;
        panel.add(acquisitionDateField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(typeLabel, gbc);
        gbc.gridx = 1;
        panel.add(typeField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(isAvailableLabel, gbc);
        gbc.gridx = 1;
        panel.add(isAvailableField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(stationLabel, gbc);
        gbc.gridx = 1;
        panel.add(stationField, gbc);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new BicycleDetailUI();
    }
}
