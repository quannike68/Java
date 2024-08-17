package javaapplication1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapeGUI extends JFrame {
    private JTextField radiusField, lengthField, widthField;
    private JCheckBox fillCheckBox;
    private JTextArea resultArea;
    private DrawingPanel drawingPanel;
    private JComboBox<String> colorComboBox;

    public ShapeGUI() {
        // Setup the frame
        setTitle("Shape Calculator and Drawer");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create GUI components
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        radiusField = new JTextField();
        lengthField = new JTextField();
        widthField = new JTextField();
        fillCheckBox = new JCheckBox("Filled");
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        // Define color options
        String[] colors = {"Black", "Red", "Blue", "Green", "Yellow"};
        colorComboBox = new JComboBox<>(colors);

        JButton calculateCircleButton = new JButton("Calculate Circle");
        calculateCircleButton.addActionListener(new CalculateCircleListener());

        JButton calculateRectangleButton = new JButton("Calculate Rectangle");
        calculateRectangleButton.addActionListener(new CalculateRectangleListener());

        JButton drawShapeButton = new JButton("Draw Shape");
        drawShapeButton.addActionListener(new DrawShapeListener());

        // Add components to the input panel
        inputPanel.add(new JLabel("Radius:"));
        inputPanel.add(radiusField);
        inputPanel.add(new JLabel("Length:"));
        inputPanel.add(lengthField);
        inputPanel.add(new JLabel("Width:"));
        inputPanel.add(widthField);
        inputPanel.add(new JLabel("Color:"));
        inputPanel.add(colorComboBox);
        inputPanel.add(new JLabel("Filled:"));
        inputPanel.add(fillCheckBox);
        inputPanel.add(calculateCircleButton);
        inputPanel.add(calculateRectangleButton);
        inputPanel.add(drawShapeButton);
        inputPanel.add(new JScrollPane(resultArea));

        // Setup the drawing panel
        drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredSize(new Dimension(400, 400));

        // Add panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
    }

    // Action Listener for the Circle calculation
    private class CalculateCircleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double radius = Double.parseDouble(radiusField.getText());
            boolean fill = fillCheckBox.isSelected();
            String color = (String) colorComboBox.getSelectedItem();

            Circle circle = new Circle(radius, fill, color.toLowerCase());
            resultArea.setText(circle.toString());
        }
    }

    // Action Listener for the Rectangle calculation
    private class CalculateRectangleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double length = Double.parseDouble(lengthField.getText());
            double width = Double.parseDouble(widthField.getText());
            boolean fill = fillCheckBox.isSelected();
            String color = (String) colorComboBox.getSelectedItem();

            Rectangle rectangle = new Rectangle(length, width, fill, color.toLowerCase());
            resultArea.setText(rectangle.toString());
        }
    }

    // Action Listener for drawing the shape
    private class DrawShapeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            drawingPanel.repaint();
        }
    }

    // Custom panel to draw shapes
    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            String color = (String) colorComboBox.getSelectedItem();
            boolean fill = fillCheckBox.isSelected();
            g2d.setColor(parseColor(color.toLowerCase()));

            // Draw Circle
            if (!radiusField.getText().isEmpty()) {
                double radius = Double.parseDouble(radiusField.getText());
                int diameter = (int) (radius * 2);
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;

                if (fill) {
                    g2d.fillOval(x, y, diameter, diameter);
                } else {
                    g2d.drawOval(x, y, diameter, diameter);
                }
            }

            // Draw Rectangle
            if (!lengthField.getText().isEmpty() && !widthField.getText().isEmpty()) {
                double length = Double.parseDouble(lengthField.getText());
                double width = Double.parseDouble(widthField.getText());
                int x = (getWidth() - (int) width) / 2;
                int y = (getHeight() - (int) length) / 2;

                if (fill) {
                    g2d.fillRect(x, y, (int) width, (int) length);
                } else {
                    g2d.drawRect(x, y, (int) width, (int) length);
                }
            }
        }

        private Color parseColor(String colorStr) {
            switch (colorStr) {
                case "red":
                    return Color.RED;
                case "blue":
                    return Color.BLUE;
                case "green":
                    return Color.GREEN;
                case "yellow":
                    return Color.YELLOW;
                default:
                    return Color.BLACK;
            }
        }
    }
}
