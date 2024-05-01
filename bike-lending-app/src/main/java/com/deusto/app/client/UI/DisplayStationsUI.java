package com.deusto.app.client.UI;

import javax.swing.*;

import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.StationData;

import java.util.List;

public class DisplayStationsUI extends JFrame {
    private JTable stationTable;

    public DisplayStationsUI() {
        setTitle("Station Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        
        List<StationData> stations = BikeController.getInstance().displayStations(UserController.getInstance().getToken());

        System.out.println(stations);
        // Create a table to display station data
        
        /*
        
        String[] columnNames = {"ID", "Location", "Bike IDs"};
        Object[][] data = new Object[stations.size()][3];
        for (int i = 0; i < stations.size(); i++) {
            StationData station = stations.get(i);
            data[i][0] = station.getId();
            data[i][1] = station.getLocation();
            data[i][2] = station.getBikeIds().toString(); // Convert list to string
        }
        stationTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(stationTable);

        add(scrollPane);
        
        */
        setVisible(true);
    }
}

