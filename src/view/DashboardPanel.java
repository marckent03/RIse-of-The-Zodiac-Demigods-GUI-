package view;

import controller.MealMateController;
import dao.MenuDAO;
import dao.OrderDAO;
import model.MenuItem;
import model.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class DashboardPanel extends JPanel {
    private MealMateController controller;
    private User user;
    private JPanel menuPanel;
    private JComboBox<String> timeSlotCombo;
    private JSpinner quantitySpinner;
    private MenuDAO menuDAO;
    private OrderDAO orderDAO;
    
    private static final String[] TIME_SLOTS = {"12:00 PM", "12:30 PM", "1:00 PM", "1:30 PM", "2:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM"};
    
    public DashboardPanel(MealMateController controller, User user) {
        this.controller = controller;
        this.user = user;
        this.menuDAO = new MenuDAO();
        this.orderDAO = new OrderDAO();
        setLayout(new BorderLayout());
        setBackground(new Color(240, 243, 247));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        JLabel welcomeLabel = new JLabel("Welcome back, " + user.getFullName().split(" ")[0] + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(33, 43, 54));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton profileBtn = new JButton("Profile");
        profileBtn.setBackground(new Color(255, 107, 53));
        profileBtn.setForeground(Color.WHITE);
        profileBtn.setOpaque(true);
        profileBtn.setBorderPainted(false);
        profileBtn.setFocusPainted(false);
        profileBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        profileBtn.addActionListener(e -> controller.showProfile());
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(42, 101, 255));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setOpaque(true);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        logoutBtn.addActionListener(e -> controller.logout());
        
        buttonPanel.add(profileBtn);
        buttonPanel.add(logoutBtn);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Center
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(248, 248, 252));
        
        JLabel menuTitle = new JLabel("Order now from today's menu");
        menuTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        menuTitle.setForeground(new Color(33, 43, 54));
        menuTitle.setBorder(new EmptyBorder(15, 20, 12, 20));
        centerPanel.add(menuTitle, BorderLayout.NORTH);
        
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(248, 248, 252));
        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(248, 248, 252));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Order controls
        JPanel orderControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        orderControls.setBackground(Color.WHITE);
        orderControls.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 226, 232), 1),
                new EmptyBorder(14, 18, 14, 18)));
        
        JLabel pickupLabel = new JLabel("Pickup Time:");
        pickupLabel.setForeground(new Color(102, 112, 122));
        orderControls.add(pickupLabel);
        timeSlotCombo = new JComboBox<>(TIME_SLOTS);
        timeSlotCombo.setBackground(new Color(248, 250, 254));
        timeSlotCombo.setBorder(BorderFactory.createLineBorder(new Color(220, 226, 232)));
        orderControls.add(timeSlotCombo);
        
        JLabel qtyLabel = new JLabel("Qty:");
        qtyLabel.setForeground(new Color(102, 112, 122));
        orderControls.add(qtyLabel);
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().setBackground(new Color(248, 250, 254));
        quantitySpinner.setBorder(BorderFactory.createLineBorder(new Color(220, 226, 232)));
        orderControls.add(quantitySpinner);
        
        centerPanel.add(orderControls, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
        
        refreshMenu();
    }
    
    public void refreshMenu() {
        menuPanel.removeAll();
        List<MenuItem> menuItems = menuDAO.getAllMenuItems();
        for (MenuItem item : menuItems) {
            JPanel itemCard = new JPanel(new BorderLayout());
            itemCard.setBackground(Color.WHITE);
            itemCard.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(220, 226, 232), 1),
                    new EmptyBorder(16, 18, 16, 18)
            ));
            itemCard.setMaximumSize(new Dimension(420, 120));
            
            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            infoPanel.setBackground(Color.WHITE);
            JLabel nameLabel = new JLabel(item.getName());
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            JLabel priceLabel = new JLabel(String.format("Php %.2f", item.getPrice()));
            priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            priceLabel.setForeground(new Color(255, 107, 53));
            infoPanel.add(nameLabel);
            infoPanel.add(priceLabel);
            
            JButton orderBtn = new JButton("Order Now");
            orderBtn.setBackground(new Color(255, 107, 53));
            orderBtn.setForeground(Color.WHITE);
            orderBtn.setOpaque(true);
            orderBtn.setBorderPainted(false);
            orderBtn.setFocusPainted(false);
            orderBtn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
            orderBtn.addActionListener(e -> placeOrder(item));
            
            itemCard.add(infoPanel, BorderLayout.CENTER);
            itemCard.add(orderBtn, BorderLayout.EAST);
            menuPanel.add(itemCard);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        menuPanel.revalidate();
        menuPanel.repaint();
    }
    
    private void placeOrder(MenuItem item) {
        int quantity = (Integer) quantitySpinner.getValue();
        String pickupTime = (String) timeSlotCombo.getSelectedItem();
        boolean success = orderDAO.placeOrder(user.getId(), item.getId(), quantity, pickupTime);
        if (success) {
            JOptionPane.showMessageDialog(this, "Order placed successfully!\n" +
                    item.getName() + " x" + quantity + "\nPickup: " + pickupTime);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to place order. Please try again.");
        }
    }
}