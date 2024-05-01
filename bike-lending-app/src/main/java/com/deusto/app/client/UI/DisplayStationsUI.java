package com.deusto.app.client.UI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.StationData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        System.out.println(stations);
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

        // Add your UI components to display bike details here
        // For example:
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Bike IDs: " + station.getBikeIds().toString());
        panel.add(label);

        bikeDetailsWindow.add(panel);
        bikeDetailsWindow.setVisible(true);
    }
}
