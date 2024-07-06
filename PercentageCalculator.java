import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class PercentageCalculator extends JFrame {

    private JTextField input1;
    private JTextField input2;
    private JComboBox<String> operation;
    private JButton calculateButton;
    private JLabel resultLabel;

    public PercentageCalculator() {
        // Set up main frame
        setTitle("Percentage Calculator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set an icon image for the frame (Optional)
        // ImageIcon icon = new ImageIcon("path/to/your/icon.png");
        // setIconImage(icon.getImage());

        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(Color.LIGHT_GRAY);

        input1 = new JTextField();
        input2 = new JTextField();

        JLabel input1Label = new JLabel("Input 1:");
        input1Label.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel input2Label = new JLabel("Input 2:");
        input2Label.setFont(new Font("Arial", Font.BOLD, 14));

        inputPanel.add(input1Label);
        inputPanel.add(input1);
        inputPanel.add(input2Label);
        inputPanel.add(input2);

        // Create operation panel
        JPanel operationPanel = new JPanel(new FlowLayout());
        operationPanel.setBackground(Color.LIGHT_GRAY);
        String[] operations = {"Calculate Percentage", "Percentage Increase", "Percentage Decrease", "Find Whole"};
        operation = new JComboBox<>(operations);
        operation.setFont(new Font("Arial", Font.BOLD, 14));
        operationPanel.add(new JLabel("Operation:"));
        operationPanel.add(operation);

        // Create result panel
        JPanel resultPanel = new JPanel(new FlowLayout());
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        resultPanel.setBackground(Color.WHITE);
        resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setForeground(Color.BLUE);
        resultPanel.add(resultLabel);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setBackground(Color.GREEN);
        calculateButton.setForeground(Color.WHITE);
        buttonPanel.add(calculateButton);

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(operationPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add button listener
        calculateButton.addActionListener(new CalculateButtonListener());

        setVisible(true);
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                BigDecimal num1 = new BigDecimal(input1.getText());
                BigDecimal num2 = new BigDecimal(input2.getText());
                String selectedOperation = (String) operation.getSelectedItem();
                BigDecimal result = BigDecimal.ZERO;

                switch (selectedOperation) {
                    case "Calculate Percentage":
                        result = calculatePercentage(num1, num2);
                        resultLabel.setText("Result: " + result.toString() + "%");
                        break;
                    case "Percentage Increase":
                        result = calculatePercentageChange(num1, num2, true);
                        resultLabel.setText("Result: " + result.toString() + "%");
                        break;
                    case "Percentage Decrease":
                        result = calculatePercentageChange(num1, num2, false);
                        resultLabel.setText("Result: " + result.toString() + "%");
                        break;
                    case "Find Whole":
                        result = findWhole(num1, num2);
                        resultLabel.setText("Result: " + result.toString());
                        break;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private BigDecimal calculatePercentage(BigDecimal part, BigDecimal whole) {
            return part.divide(whole, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
        }

        private BigDecimal calculatePercentageChange(BigDecimal oldVal, BigDecimal newVal, boolean isIncrease) {
            BigDecimal difference = newVal.subtract(oldVal);
            return difference.divide(oldVal, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
        }

        private BigDecimal findWhole(BigDecimal part, BigDecimal percentage) {
            return part.divide(percentage.divide(BigDecimal.valueOf(100), 4, BigDecimal.ROUND_HALF_UP), 4, BigDecimal.ROUND_HALF_UP);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PercentageCalculator::new);
    }
}
