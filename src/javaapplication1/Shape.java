import java.awt.*;
import java.io.Serializable;

public abstract class Shape implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String color;
    protected boolean filled;

    public Shape() {
        this.color = "black";
        this.filled = true;
    }

    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Color getColorAsObject() {
        switch (color.toLowerCase()) {
            case "red": return Color.RED;
            case "green": return Color.GREEN;
            case "blue": return Color.BLUE;
            case "yellow": return Color.YELLOW;
            case "pink": return Color.PINK;
            default: return Color.BLACK;
        }
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public abstract double getArea();

    public abstract double getPerimeter();

    public abstract String toString();

    public static Shape fromString(String str) {
        String[] parts = str.split(",");
        String type = parts[0];

        switch (type) {
            case "Rectangle":
                return Rectangle.fromString(str);
            case "Circle":
                return Circle.fromString(str);
            default:
                throw new IllegalArgumentException("Unknown shape type: " + type);
        }
    }

    public abstract void draw(Graphics g);
}
