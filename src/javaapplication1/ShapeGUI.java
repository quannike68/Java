import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;


public class ShapeGUI extends JFrame {
    private JTextField widthField, lengthField, radiusField;
    private JRadioButton filledRect, filledCircle;
    private JComboBox<String> colorRect, colorCircle;
    private JPanel rectanglePanel, circlePanel;
    private JButton drawButton, addButton, loadButton;
    private DrawingPanel drawingPanel;
    private JTable shapeTable;
    private DefaultTableModel tableModel;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveItem, loadItem;
    private JMenuItem exitItem;

    public ShapeGUI() {
        setTitle("Giao diện Shape");
        setSize(800, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo và gán các đối tượng Menu
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        saveItem = new JMenuItem("Save");
        loadItem = new JMenuItem("Load");
        exitItem = new JMenuItem("Exit");

        // Thêm ActionListener cho các MenuItem
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Thêm các MenuItem vào Menu và Menu vào MenuBar
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // Gắn MenuBar vào JFrame
        setJMenuBar(menuBar);

        // Rectangle Panel
        rectanglePanel = new JPanel(new GridLayout(4, 2));
        rectanglePanel.setBorder(BorderFactory.createTitledBorder("Rectangle"));
        widthField = new JTextField();
        lengthField = new JTextField();
        filledRect = new JRadioButton("Filled");
        colorRect = new JComboBox<>(new String[]{"red", "green", "blue", "yellow", "pink"});

        rectanglePanel.add(new JLabel("Width:"));
        rectanglePanel.add(widthField);
        rectanglePanel.add(new JLabel("Length:"));
        rectanglePanel.add(lengthField);
        rectanglePanel.add(new JLabel("Filled:"));
        rectanglePanel.add(filledRect);
        rectanglePanel.add(new JLabel("Color:"));
        rectanglePanel.add(colorRect);

        // Circle Panel
        circlePanel = new JPanel(new GridLayout(3, 2));
        circlePanel.setBorder(BorderFactory.createTitledBorder("Circle"));
        radiusField = new JTextField();
        filledCircle = new JRadioButton("Filled");
        colorCircle = new JComboBox<>(new String[]{"red", "green", "blue", "yellow", "pink"});

        circlePanel.add(new JLabel("Radius:"));
        circlePanel.add(radiusField);
        circlePanel.add(new JLabel("Filled:"));
        circlePanel.add(filledCircle);
        circlePanel.add(new JLabel("Color:"));
        circlePanel.add(colorCircle);

        addButton = new JButton("Add to Table");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToTable();
            }
        });

        // Load Button
        loadButton = new JButton("Load Table");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataFromDatabase();
            }
        });

        // Draw Button
        drawButton = new JButton("Draw");
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDrawAction();
            }
        });

        drawingPanel = new DrawingPanel();

        JPanel inputPanel = new JPanel(new GridLayout(5, 1));
        inputPanel.add(rectanglePanel);
        inputPanel.add(circlePanel);
        inputPanel.add(drawButton);
        inputPanel.add(addButton);
        inputPanel.add(loadButton); // Add Load Button here

        String[] columnNames = {"Shape", "Width/Radius", "Length", "Filled", "Color"};
        tableModel = new DefaultTableModel(columnNames, 0);
        shapeTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(shapeTable);
        tableScrollPane.setPreferredSize(new Dimension(600, 150));

        add(inputPanel, BorderLayout.CENTER);
        add(drawingPanel, BorderLayout.EAST);
        add(tableScrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void handleDrawAction() {
        try {
            // Clear previous drawings
            drawingPanel.clearShapes();

            // Handle Rectangle Drawing
            String widthText = widthField.getText();
            String lengthText = lengthField.getText();
            double width = Double.parseDouble(widthText);
            double length = Double.parseDouble(lengthText);

            if (width > 0 && length > 0) {
                String rectColor = (String) colorRect.getSelectedItem();
                boolean isRectFilled = filledRect.isSelected();
                drawingPanel.addRectangle(width, length, rectColor, isRectFilled);
            }

            // Handle Circle Drawing
            String radiusText = radiusField.getText();
            double radius = Double.parseDouble(radiusText);

            if (radius > 0) {
                String circleColor = (String) colorCircle.getSelectedItem();
                boolean isCircleFilled = filledCircle.isSelected();
                drawingPanel.addCircle(radius, circleColor, isCircleFilled);
            }

            drawingPanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/shape_java"; // Replace with your DB URL
        String user = "root"; // Replace with your DB username
        String password = "root"; // Replace with your DB password

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void loadDataFromDatabase() {
        Connection conn = connect();
        if (conn != null) {
            String sql = "SELECT shape_type, width, length, radius, filled, color FROM shapes";
            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    String shapeType = rs.getString("shape_type");
                    String width = rs.getString("width");
                    String length = rs.getString("length");
                    String radius = rs.getString("radius");
                    boolean filled = rs.getBoolean("filled");
                    String filledStatus = filled ? "Yes" : "No";
                    String color = rs.getString("color");

                    if (shapeType.equals("Rectangle")) {
                        Object[] rowData = {shapeType, width, length, filledStatus, color};
                        tableModel.addRow(rowData);
                    } else if (shapeType.equals("Circle")) {
                        Object[] rowData = {shapeType, radius, "", filledStatus, color};
                        tableModel.addRow(rowData);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void addToTable() {
        Connection conn = connect();
        try {
            // Rectangle info
            String shapeType = "Rectangle";
            String width = widthField.getText();
            String length = lengthField.getText();
            String rectColor = (String) colorRect.getSelectedItem();
            boolean isRectFilled = filledRect.isSelected();
            String filledRectStatus = isRectFilled ? "Yes" : "No";

            if (!width.isEmpty() && !length.isEmpty()) {
                Object[] rectData = {shapeType, width, length, filledRectStatus, rectColor};
                tableModel.addRow(rectData);

                // Save to database
                String sql = "INSERT INTO shapes (shape_type, width, length, filled, color) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, shapeType);
                    pstmt.setDouble(2, Double.parseDouble(width));
                    pstmt.setDouble(3, Double.parseDouble(length));
                    pstmt.setBoolean(4, isRectFilled);
                    pstmt.setString(5, rectColor);
                    pstmt.executeUpdate();
                }
            }

            // Circle info
            shapeType = "Circle";
            String radius = radiusField.getText();
            String circleColor = (String) colorCircle.getSelectedItem();
            boolean isCircleFilled = filledCircle.isSelected();
            String filledCircleStatus = isCircleFilled ? "Yes" : "No";

            if (!radius.isEmpty()) {
                Object[] circleData = {shapeType, radius, "", filledCircleStatus, circleColor};
                tableModel.addRow(circleData);

                // Save to database
                String sql = "INSERT INTO shapes (shape_type, radius, filled, color) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, shapeType);
                    pstmt.setDouble(2, Double.parseDouble(radius));
                    pstmt.setBoolean(3, isCircleFilled);
                    pstmt.setString(4, circleColor);
                    pstmt.executeUpdate();
                }
            }

        } catch (NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                FileWriter writer = new FileWriter(file);
                for (Shape shape : drawingPanel.shapes) {
                    writer.write(shape.toString() + "\n");
                }
                writer.close();
                JOptionPane.showMessageDialog(this, "File saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                drawingPanel.clearShapes();
                while ((line = reader.readLine()) != null) {
                    Shape shape = Shape.fromString(line);
                    drawingPanel.addShape(shape);
                }
                reader.close();
                drawingPanel.repaint();
                if (!drawingPanel.shapes.isEmpty()) {
                    updateInputFieldsForAllShapes();
                }
                JOptionPane.showMessageDialog(this, "File loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateInputFields(Shape shape) {
        if (shape instanceof Rectangle) {
            Rectangle rect = (Rectangle) shape;
            widthField.setText(String.valueOf(rect.getWidth()));
            lengthField.setText(String.valueOf(rect.getLength()));
            filledRect.setSelected(shape.isFilled());
            colorRect.setSelectedItem(shape.getColor());
        }

        if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            radiusField.setText(String.valueOf(circle.getRadius()));
            filledCircle.setSelected(shape.isFilled());
            colorCircle.setSelectedItem(shape.getColor());
        }
    }

    private void updateInputFieldsForAllShapes() {
        for (Shape shape : drawingPanel.shapes) {
            updateInputFields(shape);
        }
    }

    private static class DrawingPanel extends JPanel {
        private java.util.List<Shape> shapes;

        public DrawingPanel() {
            shapes = new java.util.ArrayList<>();
            setPreferredSize(new Dimension(600, 400));
            setBackground(Color.WHITE);
        }

        public void addRectangle(double width, double length, String color, boolean filled) {
            shapes.add(new Rectangle(color, filled, width, length));
        }

        public void addCircle(double radius, String color, boolean filled) {
            shapes.add(new Circle(radius, color, filled));
        }

        public void addShape(Shape shape) {
            shapes.add(shape);
        }

        public void clearShapes() {
            shapes.clear();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Shape shape : shapes) {
                g.setColor(shape.getColorAsObject());
                shape.draw(g);
            }
        }

    }

    public static void main(String[] args) {
        new ShapeGUI();
    }
}
