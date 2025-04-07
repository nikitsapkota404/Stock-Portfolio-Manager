package nikit;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Stock {
    String name;
    int quantity;
    double price;

    Stock(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    double getValue() {
        return quantity * price;
    }
}

public class NepsePortfolioManager extends JFrame {
    private ArrayList<Stock> portfolio = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField, quantityField, priceField;
    private JLabel totalValueLabel;

    public NepsePortfolioManager() {
        setTitle("Stock Portfolio Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialise UI components
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Input Stock name
        panel.add(new JLabel("Stock Name:"));
        nameField = new JTextField(10);
        panel.add(nameField);

        // Input Quantity
        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField(5);
        panel.add(quantityField);

        // Input Price
        panel.add(new JLabel("Price per Stock:"));
        priceField = new JTextField(5);
        panel.add(priceField);

        // Add stock button
        JButton addButton = new JButton("Add Stock");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStock();
            }
        });
        panel.add(addButton);

        // Total Value Label
        totalValueLabel = new JLabel("Total Portfolio Value: $0.0");
        panel.add(totalValueLabel);

        // Display Portfolio
        String[] columns = {"Stock Name", "Quantity", "Price", "Total Value"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add panel to the top of the window
        add(panel, BorderLayout.NORTH);
    }

    private void addStock() {
        String name = nameField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();

        // Validate input
        if (name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        int quantity;
        double price;

        try {
            quantity = Integer.parseInt(quantityText);
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity or price.");
            return;
        }

        Stock stock = new Stock(name, quantity, price);
        portfolio.add(stock);

        // Add stock to table
        Object[] row = {name, quantity, price, stock.getValue()};
        tableModel.addRow(row);

        // Update the total portfolio value
        updateTotalValue();

        // Clear input fields
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    private void updateTotalValue() {
        double totalValue = 0;
        for (Stock stock : portfolio) {
            totalValue += stock.getValue();
        }
        totalValueLabel.setText("Total Portfolio Value: $" + totalValue);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NepsePortfolioManager().setVisible(true);
            }
        });
    }
}