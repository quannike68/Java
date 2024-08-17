import java.awt.*;

public class Rectangle extends Shape {
    private double width;
    private double length;

    public Rectangle(double width, double length){
        this.width = width;
        this.length = length;
    }

    public Rectangle(String color, boolean filled, double width, double length){
        super(color, filled);
        this.width = width;
        this.length = length;
    }

    public Rectangle() {
        this.width = 1.0;
        this.length = 5.0;
    }


    public double getWidth(){
        return width;
    }

    public double getLength(){
        return length;
    }

    public double setWidth(double width){
        return this.width = width;
    }

    public double setLength(double length){
        return this.length = length;
    }

    @Override
    public double getArea() {
        return width * length;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + length);
    }

    public String toString() {
        return "Rectangle," + color + "," + filled + "," + width + "," + length;
    }

    public static Rectangle fromString(String str) {
        String[] parts = str.split(",");
        String color = parts[1];
        boolean filled = Boolean.parseBoolean(parts[2]);
        double width = Double.parseDouble(parts[3]);
        double length = Double.parseDouble(parts[4]);
        return new Rectangle(color, filled, width, length);
    }

    @Override
    public void draw(Graphics g) {
        int intWidth = (int) width;
        int intLength = (int) length;
        if (filled) {
            g.fillRect(50, 50, intWidth, intLength);
        } else {
            g.drawRect(50, 50, intWidth, intLength);
        }
    }
}
