package com.deusto.app.client.UI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationData;

import java.awt.*;
import java.util.List;

public class DisplayStationsUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable stationTable;

    public DisplayStationsUI() {
        setTitle("Station Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        initComponents();
    }

    private void initComponents() {
        // Main panel setup
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(0, 150, 136)); // Background color

        // Title label at the top
        JLabel titleLabel = new JLabel("Estaciones DeustoBike", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Fetch station data
        List<StationData> stations = BikeController.getInstance().displayStations(UserController.getInstance().getToken());

        // Create a table to display station data
        String[] columnNames = {"ID", "Location", "Bike IDs"};
        Object[][] data = new Object[stations.size()][3];
        for (int i = 0; i < stations.size(); i++) {
            StationData station = stations.get(i);
            data[i][0] = station.getId();
            data[i][1] = station.getLocation();
            data[i][2] = station.getBikeIds().toString();
        }
        stationTable = new JTable(data, columnNames) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        stationTable.setFont(new Font("Arial", Font.PLAIN, 16));
        stationTable.setRowHeight(30);
        stationTable.setSelectionBackground(new Color(255, 114, 118));
        stationTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        stationTable.getTableHeader().setBackground(new Color(255, 114, 118));
        stationTable.getTableHeader().setForeground(Color.WHITE);

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
        scrollPane.setBackground(new Color(0, 150, 136));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the main panel to the window
        add(mainPanel);
        setVisible(true);
    }

    private void displayBikeDetails(StationData station) {
        // Create a new window to display bike details
        JFrame bikeDetailsWindow = new JFrame("Bike Details for Station " + station.getId());
        bikeDetailsWindow.setSize(600, 400);
        bikeDetailsWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create a panel to hold the bike details
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(0, 150, 136));

        // Iterate through the bike IDs of the selected station and display details for each bike
        for (Integer bikeId : station.getBikeIds()) {
            BicycleData bike = BikeController.getInstance().getBikeDetails(bikeId, UserController.getInstance().getToken());

            JLabel idLabel = new JLabel("ID: " + bike.getId());
            idLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            idLabel.setForeground(Color.WHITE);
            idLabel.setVerticalAlignment(SwingConstants.TOP);

            JLabel acquisitionDateLabel = new JLabel("Acquisition Date: " + bike.getAcquisitionDate());
            acquisitionDateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            acquisitionDateLabel.setForeground(Color.WHITE);
            acquisitionDateLabel.setVerticalAlignment(SwingConstants.TOP);

            JLabel typeLabel = new JLabel("Type: " + bike.getType());
            typeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            typeLabel.setForeground(Color.WHITE);
            typeLabel.setVerticalAlignment(SwingConstants.TOP);

            JLabel isAvailableLabel = new JLabel("Is Available: " + bike.isAvailable());
            isAvailableLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            isAvailableLabel.setForeground(Color.WHITE);
            isAvailableLabel.setVerticalAlignment(SwingConstants.TOP);

            JLabel stationLabel = new JLabel("Station: " + bike.getStationId());
            stationLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            stationLabel.setForeground(Color.WHITE);
            stationLabel.setVerticalAlignment(SwingConstants.TOP);

            JPanel bikePanel = new JPanel(new GridLayout(5, 1));
            bikePanel.setBackground(new Color(0, 150, 136));
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
