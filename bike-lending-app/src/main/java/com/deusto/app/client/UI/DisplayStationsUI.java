package com.deusto.app.client.UI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayStationsUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable stationTable;

    private ResourceBundle translation;

    public DisplayStationsUI() {
        this.translation = ResourceBundle.getBundle("translation", Locale.getDefault());
        setTitle(translation.getString("title_Stations"));
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
        String[] columnNames = {translation.getString("column_ID"), translation.getString("column_Location"), translation.getString("column_Bike_IDs")};
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
        
        // Bold and center align cell data
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setFont(stationTable.getFont().deriveFont(Font.BOLD));
        stationTable.setDefaultRenderer(Object.class, centerRenderer);

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

        // Logout button setup
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
        		boolean logout = UserController.getInstance().logoutUser(UserController.getToken());
            	
            	if (logout) {
                    JOptionPane.showMessageDialog(DisplayStationsUI.this, "Logged out successfully.");
                    System.exit(0);
            	} else {
                    JOptionPane.showMessageDialog(DisplayStationsUI.this, "Log out failed.");
            	}

            }
        });

        JPanel logoutPanel = new JPanel(new BorderLayout());
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        logoutPanel.setBackground(new Color(0, 150, 136));
        logoutPanel.add(logoutButton, BorderLayout.EAST);
        mainPanel.add(logoutPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void displayBikeDetails(StationData station) {
        // Create a new window to display bike details
<<<<<<< HEAD
        JFrame bikeDetailsWindow = new JFrame(translation.getString("bike_details_for_station") + station.getId());
        bikeDetailsWindow.setSize(400, 300);
=======
        JFrame bikeDetailsWindow = new JFrame("Bike Details for Station " + station.getId());
        bikeDetailsWindow.setSize(800, 600); // Adjusted size for better display
        bikeDetailsWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
>>>>>>> 68-updating-guis-eg-logout-visually-etc

        // Create a panel to hold the bike details
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(0, 150, 136));

<<<<<<< HEAD
        // Iterate through the bike IDs of the selected station and display details for each bike
        for (Integer bikeId : station.getBikeIds()) {
            BicycleData bike = BikeController.getInstance().getBikeDetails(bikeId, UserController.getInstance().getToken());
            
            JLabel idLabel = new JLabel(translation.getString("ID") + ": " + bike.getId());
            idLabel.setVerticalAlignment(SwingConstants.TOP); // Align text to the top
            JLabel acquisitionDateLabel = new JLabel(translation.getString("acquisition_date") + ": " + bike.getAcquisitionDate());
            acquisitionDateLabel.setVerticalAlignment(SwingConstants.TOP); // Align text to the top
            JLabel typeLabel = new JLabel(translation.getString("bike_type") + ": " + bike.getType());
            typeLabel.setVerticalAlignment(SwingConstants.TOP); // Align text to the top
            JLabel isAvailableLabel = new JLabel(translation.getString("bike_is_available") + ": " + bike.isAvailable());
            isAvailableLabel.setVerticalAlignment(SwingConstants.TOP); // Align text to the top
            JLabel stationLabel = new JLabel(translation.getString("Station") + ": " + bike.getStationId());
            stationLabel.setVerticalAlignment(SwingConstants.TOP); // Align text to the top

            JPanel bikePanel = new JPanel(new GridLayout(5, 1));
            bikePanel.add(idLabel);
            bikePanel.add(acquisitionDateLabel);
            bikePanel.add(typeLabel);
            bikePanel.add(isAvailableLabel);
            bikePanel.add(stationLabel);

            panel.add(bikePanel);
            panel.add(new JSeparator(SwingConstants.HORIZONTAL)); // Add a separator between bikes
=======
        // Fetch bike data
        List<Integer> bikeIds = station.getBikeIds();
        Object[][] bikeData = new Object[bikeIds.size()][5];
        for (int i = 0; i < bikeIds.size(); i++) {
            BicycleData bike = BikeController.getInstance().getBikeDetails(bikeIds.get(i), UserController.getInstance().getToken());
            bikeData[i][0] = bike.getId();
            bikeData[i][1] = bike.getAcquisitionDate();
            bikeData[i][2] = bike.getType();
            bikeData[i][3] = bike.isAvailable() ? "Yes" : "No";
            bikeData[i][4] = bike.getStationId();
>>>>>>> 68-updating-guis-eg-logout-visually-etc
        }

        String[] bikeColumnNames = {"ID", "Acquisition Date", "Type", "Is Available", "Station"};

        JTable bikeTable = new JTable(bikeData, bikeColumnNames) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (column == 3) { // Check availability column
                    String value = (String) getValueAt(row, column);
                    comp.setForeground("Yes".equals(value) ? Color.GREEN : Color.RED);
                } else {
                    comp.setForeground(Color.BLACK);
                }
                return comp;
            }
        };
        
        // Bold and center align cell data
        DefaultTableCellRenderer bikeCenterRenderer = new DefaultTableCellRenderer();
        bikeCenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        bikeCenterRenderer.setFont(bikeTable.getFont().deriveFont(Font.BOLD));
        bikeTable.setDefaultRenderer(Object.class, bikeCenterRenderer);

        bikeTable.setFont(new Font("Arial", Font.PLAIN, 16));
        bikeTable.setRowHeight(30);
        bikeTable.setSelectionBackground(new Color(255, 114, 118));
        bikeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        bikeTable.getTableHeader().setBackground(new Color(255, 114, 118));
        bikeTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane bikeScrollPane = new JScrollPane(bikeTable);
        bikeScrollPane.setBackground(new Color(0, 150, 136));

        panel.add(bikeScrollPane, BorderLayout.CENTER);

        // Logout button setup for bike details window
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	
            	
                JOptionPane.showMessageDialog(bikeDetailsWindow, "Logged out successfully.");
                System.exit(0);
            }
        });

        JPanel logoutPanel = new JPanel(new BorderLayout());
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        logoutPanel.setBackground(new Color(0, 150, 136));
        logoutPanel.add(logoutButton, BorderLayout.EAST);
        panel.add(logoutPanel, BorderLayout.NORTH);

        bikeDetailsWindow.add(panel);
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
