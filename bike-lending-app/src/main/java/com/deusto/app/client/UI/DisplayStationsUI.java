package com.deusto.app.client.UI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.LoanController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.LoanData;
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
    private JPanel statusPanel; 
    private JLabel statusLabel;
    private JButton reportButton;

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
                        
                    }
                }
            }
        });

        statusPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        

        reportButton = new JButton("REPORT");
        reportButton.setFont(new Font("Arial", Font.BOLD, 16));
        reportButton.setForeground(Color.WHITE);
        reportButton.setBorderPainted(false);
        statusPanel.add(reportButton, BorderLayout.EAST);
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        updateStatusPanel(); // Call this method to set the initial state of the panel

        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        JScrollPane scrollPane = new JScrollPane(stationTable);
        scrollPane.setBackground(new Color(0, 150, 136));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the main panel to the window
        add(mainPanel);

        // Logout button setup
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(0, 150, 136));
        logoutButton.addActionListener(e -> {
        	boolean logout = UserController.getInstance().logoutUser(UserController.getToken());
        	
        	if (logout) {
                JOptionPane.showMessageDialog(DisplayStationsUI.this, "Logged out successfully.");
                new LoginUI();
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(false);
                dispose();
        	} else {
                JOptionPane.showMessageDialog(DisplayStationsUI.this, "Log out failed.");
        	}

        });
        logoutButton.setBorderPainted(false);
        mainPanel.add(logoutButton, BorderLayout.NORTH);

        setVisible(true);
    }
    
    private void updateStatusPanel() {
    	LoanData loan= LoanController.getInstance().isLoanActive(UserController.getToken());
        if (loan!=null) {
        	System.out.println(loan);
            statusPanel.setBackground(Color.ORANGE);
            statusLabel.setText("Tienes una bici activada");
        } else {
            statusPanel.setBackground(Color.BLUE);
            reportButton.setBackground(Color.BLUE);
            statusLabel.setText("Esperando a que alquiles una bici");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DisplayStationsUI();
            }
        });
    }
}
