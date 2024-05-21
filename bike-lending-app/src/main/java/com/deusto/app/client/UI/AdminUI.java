package com.deusto.app.client.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;


import com.deusto.app.client.controller.AdminController;
import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.LoanController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.LoanData;
import com.deusto.app.server.pojo.StationData;


public class AdminUI extends JFrame {
    
	private static final long serialVersionUID = 1L;
	private JTable loansTable, nonAvailableBikesTable;

    public AdminUI() {
        setTitle("Bike Administration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        initComponents();
    }

    /**
     * Initializes the components of the main user interface.
     * Sets up the main panel, logout button, split pane, and buttons for loan and bike management.
     */
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(0, 150, 136));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(0, 150, 136));
        logoutButton.addActionListener(e -> logout());
        logoutButton.setBorderPainted(false);
        mainPanel.add(logoutButton, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(0.5);

        setupLoansPanel(splitPane);
        setupNonAvailableBikesPanel(splitPane);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        JButton createNewBikeButton = new JButton("Crear Nueva Bicicleta");
        createNewBikeButton.setFont(new Font("Arial", Font.BOLD, 16));
        createNewBikeButton.setForeground(Color.WHITE);
        createNewBikeButton.setBackground(new Color(0, 150, 136));
        createNewBikeButton.setBorderPainted(false); // Remove border
        createNewBikeButton.addActionListener(e -> createNewBike());

        mainPanel.add(createNewBikeButton, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);
    }

    /**
     * Sets up the loans panel within the split pane.
     * Configures the layout, header label, and table for displaying loan data.
     *
     * @param splitPane the split pane to which the loans panel will be added
     */
    private void setupLoansPanel(JSplitPane splitPane) {
        JPanel loansPanel = new JPanel(new BorderLayout());
        
        JLabel header = new JLabel("LOAN", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(255, 114, 118));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        loansPanel.add(header, BorderLayout.NORTH);
        
     // Fetch station data
        List<LoanData> loans = LoanController.getInstance().getAllLoans();

        // Create a table to display station data
        String[] columnNames = {"ID","User","Bicycle","Date"};
        Object[][] data = new Object[loans.size()][4];
        for (int i = 0; i < loans.size(); i++) {
            LoanData loan = loans.get(i);
            data[i][0] = loan.getId();
            data[i][1] = loan.getUserDni();
            data[i][2] = loan.getBicycleId();
            data[i][3] = loan.getLoanDate();

            
            
        }
        
        loansTable = new JTable(data, columnNames) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        
        setupTable(loansTable);
        loansPanel.add(new JScrollPane(loansTable), BorderLayout.CENTER);
        JButton deleteLoanButton = new JButton("Borrar Loan");
        deleteLoanButton.setBackground(new Color(255, 114, 118));
        deleteLoanButton.setForeground(Color.WHITE);
        deleteLoanButton.addActionListener(e -> deleteLoan());
        loansPanel.add(deleteLoanButton, BorderLayout.SOUTH);
        splitPane.setLeftComponent(loansPanel);
        loansTable.getTableHeader().setForeground(Color.WHITE);
        loansTable.getTableHeader().setBackground(new Color(255, 114, 118));
    }

        /**
     * Sets up the panel for displaying non-available bikes within the split pane.
     * Configures the layout, header label, and table for displaying bike data.
     * Adds a button to reactivate bikes and handles the necessary background setup.
     *
     * @param splitPane the split pane to which the non-available bikes panel will be added
     */
    private void setupNonAvailableBikesPanel(JSplitPane splitPane) {
        JPanel nonAvailableBikesPanel = new JPanel(new BorderLayout());
        JLabel header = new JLabel("NO AVAILABLE BIKES", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(255, 114, 118));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        nonAvailableBikesPanel.add(header, BorderLayout.NORTH);
        
     // Fetch station data
        List<BicycleData> bikes = BikeController.getInstance().displayNoAvailableBikes(UserController.getToken());
        List<StationData> stations = BikeController.getInstance().displayStations(UserController.getToken());

        // Create a table to display station data
        String[] columnNames = {"ID","Type","Station"};
        Object[][] data = new Object[bikes.size()][3];
        for (int i = 0; i < bikes.size(); i++) {
            BicycleData bike = bikes.get(i);
            data[i][0] = bike.getId();
            data[i][1] = bike.getType();
            for (StationData stationData : stations) {
            	if(stationData.getId()==bike.getStationId()) {
            		data[i][2] = stationData.getLocation();
            	}
            }
            
        }
        
        nonAvailableBikesTable = new JTable(data, columnNames) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setupTable(nonAvailableBikesTable);
        nonAvailableBikesPanel.add(new JScrollPane(nonAvailableBikesTable), BorderLayout.CENTER);
        JButton reactivateBikeButton = new JButton("Reactivar Bicicleta");
        reactivateBikeButton.setBackground(new Color(255, 114, 118));
        reactivateBikeButton.setForeground(Color.WHITE);
        reactivateBikeButton.addActionListener(e -> reactivateBike());
        nonAvailableBikesPanel.add(reactivateBikeButton, BorderLayout.SOUTH);
        splitPane.setRightComponent(nonAvailableBikesPanel);
        nonAvailableBikesPanel.setBackground(new Color(255,255,255));
        nonAvailableBikesTable.getTableHeader().setForeground(Color.WHITE);
        nonAvailableBikesTable.getTableHeader().setBackground(new Color(255, 114, 118));
        
     
    }

    /**
     * Configures the table for displaying data.
     * Sets up custom rendering for table cells to change background color when selected.
     * Adds a mouse listener to clear selection when clicking outside of valid rows.
     *
     * @param table the JTable to be configured
     */
    private void setupTable(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
			private static final long serialVersionUID = 1L;

			@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                	setBackground(new Color(255, 114, 118));
                } else {
                setBackground(Color.WHITE); // Restore to default color when not selected
            }
            	return this;
            }
        });
    
    
    table.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            JTable source = (JTable) e.getSource();
            int row = source.rowAtPoint(e.getPoint());
            

            // Clear selection when clicking outside of valid rows
            if (!source.isRowSelected(row)) {
                source.clearSelection();
            }
        }
    });
}

    /**
     * Logs out the current user.
     * Calls the UserController to perform the logout operation.
     * Displays a success message and redirects to the login screen if logout is successful.
     * Displays an error message if logout fails.
     */
    private void logout() {
    	boolean logout = UserController.getInstance().logoutUser(UserController.getToken());
    	
    	if (logout) {
            JOptionPane.showMessageDialog(AdminUI.this, "Logged out successfully.");
            new LoginUI();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(false);
            dispose();
    	} else {
            JOptionPane.showMessageDialog(AdminUI.this, "Log out failed.");
    	}
        
    }
    /**
     * Deletes the selected loan.
     * Retrieves the selected row from the loans table.
     * Deletes the loan using LoanController based on the selected loan's ID.
     * Displays a success message if the deletion is successful and redirects to the admin UI.
     * Displays an error message if deletion fails.
     */
    private void deleteLoan() {
    	int selectedRow = loansTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Assume delete logic here
        	boolean success= LoanController.getInstance().deleteLoan(Integer.parseInt(loansTable.getValueAt(selectedRow, 0).toString()));
        	if(success) {
        		JOptionPane.showMessageDialog(this, "Loan Deleted: " + loansTable.getValueAt(selectedRow, 0));
        		new AdminUI();
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(false);
                dispose();
        	}else {
        		JOptionPane.showMessageDialog(this, "ERROR!");
        	}
            
        } else {
            JOptionPane.showMessageDialog(this, "No loan selected.");
        }
        
    }
    /**
     * Reactivates the selected bike.
     * Retrieves the selected row from the non-available bikes table.
     * Reactivates the bike using AdminController based on the selected bike's ID.
     * Displays a success message if reactivation is successful and redirects to the admin UI.
     * Displays an error message if reactivation fails.
     */
    private void reactivateBike() {
    	int selectedRow = nonAvailableBikesTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Assume delete logic here
        	boolean success= AdminController.getInstance().ableBike(Integer.parseInt(nonAvailableBikesTable.getValueAt(selectedRow, 0).toString())
        															,UserController.getToken());
        	if(success) {
        		JOptionPane.showMessageDialog(this, "Bike Enable: " + nonAvailableBikesTable.getValueAt(selectedRow, 0));
        		new AdminUI();
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(false);
                dispose();
        	}else {
        		JOptionPane.showMessageDialog(this, "ERROR!");
        	}
            
        } else {
            JOptionPane.showMessageDialog(this, "No loan selected.");
        }
    }
    /**
     * Opens a new UI for creating a new bike.
     * Closes the current UI.
     */
    private void createNewBike() {
        new CreateBikeUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminUI::new);
    }
}
