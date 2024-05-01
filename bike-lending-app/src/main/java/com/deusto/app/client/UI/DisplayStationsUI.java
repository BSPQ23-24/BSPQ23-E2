package com.deusto.app.client.UI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationData;

import java.awt.GridLayout;
import java.util.List;

public class DisplayStationsUI extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTable stationTable;

    public DisplayStationsUI() {
        setTitle("Station Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        
        List<StationData> stations = BikeController.getInstance().displayStations(UserController.getInstance().getToken());

        // Create a table to display station data
        String[] columnNames = {"ID", "Location", "Bike IDs"};
        Object[][] data = new Object[stations.size()][3];
        for (int i = 0; i < stations.size(); i++) {
            StationData station = stations.get(i);
            data[i][0] = station.getId();
            data[i][1] = station.getLocation();
            data[i][2] = station.getBikeIds().toString(); // Convert list to string
        }
        stationTable = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        stationTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = stationTable.getSelectedRow();
                    if (selectedRow != -1) {
                        StationData selectedStation = stations.get(selectedRow);
                        // Open another window to display bike details for the selected station
                        displayBikeDetails(selectedStation);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(stationTable);

        add(scrollPane);
        setVisible(true);
    }

    private void displayBikeDetails(StationData station) {
        // Create a new window to display bike details
        JFrame bikeDetailsWindow = new JFrame("Bike Details for Station " + station.getId());
        bikeDetailsWindow.setSize(400, 300);

        // Create a panel to hold the bike details
        JPanel panel = new JPanel(new GridLayout(0, 1));

        // Iterate through the bike IDs of the selected station and display details for each bike
        for (Integer bikeId : station.getBikeIds()) {
            BicycleData bike = BikeController.getInstance().getBikeDetails(bikeId, UserController.getInstance().getToken());
            
            JLabel idLabel = new JLabel("ID: " + bike.getId());
            idLabel.setVerticalAlignment(SwingConstants.TOP); // Align text to the top
            JLabel acquisitionDateLabel = new JLabel("Acquisition Date: " + bike.getAcquisitionDate());
            acquisitionDateLabel.setVerticalAlignment(SwingConstants.TOP); // Align text to the top
            JLabel typeLabel = new JLabel("Type: " + bike.getType());
            typeLabel.setVerticalAlignment(SwingConstants.TOP); // Align text to the top
            JLabel isAvailableLabel = new JLabel("Is Available: " + bike.isAvailable());
            isAvailableLabel.setVerticalAlignment(SwingConstants.TOP); // Align text to the top
            JLabel stationLabel = new JLabel("Station: " + bike.getStationId());
            stationLabel.setVerticalAlignment(SwingConstants.TOP); // Align text to the top

            JPanel bikePanel = new JPanel(new GridLayout(5, 1));
            bikePanel.add(idLabel);
            bikePanel.add(acquisitionDateLabel);
            bikePanel.add(typeLabel);
            bikePanel.add(isAvailableLabel);
            bikePanel.add(stationLabel);

            panel.add(bikePanel);
            panel.add(new JSeparator(SwingConstants.HORIZONTAL)); // Add a separator between bikes
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        bikeDetailsWindow.add(scrollPane);
        bikeDetailsWindow.setVisible(true);
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DisplayStationsUI();
            }
        });
    }
}
