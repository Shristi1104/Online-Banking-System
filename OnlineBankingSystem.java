import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OnlineBankingSystem extends JFrame {
    // Database simulation
    private Map<String, User> users = new HashMap<>();
    private User currentUser = null;
    
    // GUI Components
    private JPanel cards;
    private CardLayout cardLayout;
    
    // Login components
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    
    // Register components
    private JTextField regNameField;
    private JTextField regUsernameField;
    private JPasswordField regPasswordField;
    private JTextField regEmailField;
    
    // Dashboard components
    private JLabel welcomeLabel;
    private JLabel balanceLabel;
    private JTextField amountField;
    
    public OnlineBankingSystem() {
        // Initialize some test users
        initializeTestUsers();
        
        // Set up the main frame
        setTitle("Online Banking System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create card layout for switching between panels
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        
        // Create and add panels
        cards.add(createLoginPanel(), "LOGIN");
        cards.add(createRegisterPanel(), "REGISTER");
        cards.add(createDashboardPanel(), "DASHBOARD");
        
        add(cards);
        
        // Show login panel first
        cardLayout.show(cards, "LOGIN");
    }
    
    private void initializeTestUsers() {
        // Add some test users
        users.put("john", new User("John Doe", "john", "password123", "john@example.com", 1000.0));
        users.put("jane", new User("Jane Smith", "jane", "password456", "jane@example.com", 2500.0));
    }
    
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Online Banking Login", JLabel.CENTER), gbc);
        
        // Username
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        loginUsernameField = new JTextField(15);
        panel.add(loginUsernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        loginPasswordField = new JPasswordField(15);
        panel.add(loginPasswordField, gbc);
        
        // Login button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        panel.add(loginButton, gbc);
        
        // Register link
        gbc.gridy++;
        JButton registerButton = new JButton("Don't have an account? Register here");
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setForeground(Color.BLUE);
        registerButton.addActionListener(e -> cardLayout.show(cards, "REGISTER"));
        panel.add(registerButton, gbc);
        
        return panel;
    }
    
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Register New Account", JLabel.CENTER), gbc);
        
        // Full Name
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Full Name:"), gbc);
        
        gbc.gridx = 1;
        regNameField = new JTextField(15);
        panel.add(regNameField, gbc);
        
        // Username
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        regUsernameField = new JTextField(15);
        panel.add(regUsernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        regPasswordField = new JPasswordField(15);
        panel.add(regPasswordField, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        regEmailField = new JTextField(15);
        panel.add(regEmailField, gbc);
        
        // Register button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> register());
        panel.add(registerButton, gbc);
        
        // Back to login link
        gbc.gridy++;
        JButton loginButton = new JButton("Already have an account? Login here");
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setForeground(Color.BLUE);
        loginButton.addActionListener(e -> cardLayout.show(cards, "LOGIN"));
        panel.add(loginButton, gbc);
        
        return panel;
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel for welcome and balance
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        welcomeLabel = new JLabel("", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel = new JLabel("", JLabel.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(welcomeLabel);
        topPanel.add(balanceLabel);
        
        // Center panel for operations
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        
        // Amount field
        JPanel amountPanel = new JPanel();
        amountPanel.add(new JLabel("Amount: $"));
        amountField = new JTextField(10);
        amountPanel.add(amountField);
        
        // Buttons
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> performTransaction(true));
        
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> performTransaction(false));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        
        centerPanel.add(amountPanel);
        centerPanel.add(depositButton);
        centerPanel.add(withdrawButton);
        centerPanel.add(logoutButton);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void login() {
        String username = loginUsernameField.getText().trim();
        String password = new String(loginPasswordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User user = users.get(username);
        if (user == null || !user.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        currentUser = user;
        updateDashboard();
        cardLayout.show(cards, "DASHBOARD");
        
        // Clear fields
        loginUsernameField.setText("");
        loginPasswordField.setText("");
    }
    
    private void register() {
        String name = regNameField.getText().trim();
        String username = regUsernameField.getText().trim();
        String password = new String(regPasswordField.getPassword());
        String email = regEmailField.getText().trim();
        
        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (users.containsKey(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create new user with initial balance of 0
        User newUser = new User(name, username, password, email, 0.0);
        users.put(username, newUser);
        
        JOptionPane.showMessageDialog(this, "Registration successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
        cardLayout.show(cards, "LOGIN");
        
        // Clear fields
        regNameField.setText("");
        regUsernameField.setText("");
        regPasswordField.setText("");
        regEmailField.setText("");
    }
    
    private void logout() {
        currentUser = null;
        cardLayout.show(cards, "LOGIN");
    }
    
    private void updateDashboard() {
        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getName());
            balanceLabel.setText(String.format("Account Balance: $%.2f", currentUser.getBalance()));
        }
    }
    
    private void performTransaction(boolean isDeposit) {
        if (currentUser == null) return;
        
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (isDeposit) {
                currentUser.deposit(amount);
                JOptionPane.showMessageDialog(this, String.format("$%.2f deposited successfully", amount), 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (currentUser.getBalance() < amount) {
                    JOptionPane.showMessageDialog(this, "Insufficient funds", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                currentUser.withdraw(amount);
                JOptionPane.showMessageDialog(this, String.format("$%.2f withdrawn successfully", amount), 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            
            updateDashboard();
            amountField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OnlineBankingSystem bankingSystem = new OnlineBankingSystem();
            bankingSystem.setVisible(true);
        });
    }
    
    // User class to represent bank users
    private static class User {
        private String name;
        private String username;
        private String password;
        private String email;
        private double balance;
        
        public User(String name, String username, String password, String email, double balance) {
            this.name = name;
            this.username = username;
            this.password = password;
            this.email = email;
            this.balance = balance;
        }
        
        public String getName() { return name; }
        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getEmail() { return email; }
        public double getBalance() { return balance; }
        
        public void deposit(double amount) {
            balance += amount;
        }
        
        public void withdraw(double amount) {
            balance -= amount;
        }
    }
}