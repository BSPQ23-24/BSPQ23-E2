package com.deusto.app.client.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.deusto.app.client.controller.AdminController;
import com.deusto.app.client.controller.BikeController;
import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.BicycleData;
import com.deusto.app.server.pojo.StationData;
import com.deusto.app.server.pojo.UserData;

public class AdminUI extends JFrame {
    private JTable loansTable, nonAvailableBikesTable;

    public AdminUI() {
        setTitle("Bike Administration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(0, 150, 136));

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

    private void setupLoansPanel(JSplitPane splitPane) {
        JPanel loansPanel = new JPanel(new BorderLayout());
        loansTable = new JTable(new Object[][]{{"Loan 1", "User A"}, {"Loan 2", "User B"}}, new String[]{"Loan ID", "User"});
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

    private void setupNonAvailableBikesPanel(JSplitPane splitPane) {
        JPanel nonAvailableBikesPanel = new JPanel(new BorderLayout());
        nonAvailableBikesTable = new JTable(new Object[][]{{"Bike 1", "Broken"}, {"Bike 2", "In Repair"}}, new String[]{"Bike ID", "Status"});
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

    private void setupTable(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) setBackground(null);
                return this;
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                table.setSelectionBackground(new Color(255, 114, 118));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                table.clearSelection();
            }
        });
    }

    private void deleteLoan() {
        JOptionPane.showMessageDialog(this, "Loan Deleted!");
    }

    private void reactivateBike() {
        JOptionPane.showMessageDialog(this, "Bike Reactivated!");
    }

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
