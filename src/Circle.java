import java.awt.*;

public class Circle extends Shape {
    private double radius;

    public Circle() {
        super();
        this.radius = 1.0;
    }

    public Circle(double radius) {
        super();
        this.radius = radius;
    }

    public Circle(double radius, String color, boolean filled) {
        super(color, filled);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void draw(Graphics g) {
        int intRadius = (int) radius;
        if (filled) {
            g.fillOval(200, 50, intRadius * 2, intRadius * 2);
        } else {
            g.drawOval(200, 50, intRadius * 2, intRadius * 2);
        }
    }

    public String toString() {
        return "Circle," + color + "," + filled + "," + radius;
    }

    public static Circle fromString(String str) {
        String[] parts = str.split(",");
        String color = parts[1];
        boolean filled = Boolean.parseBoolean(parts[2]);
        double radius = Double.parseDouble(parts[3]);
        return new Circle(radius, color, filled);
    }

//    public static void main(String[] args) {
//        Circle Circle1 = new Circle();
//        System.out.println(Circle1);
//        System.out.println("Area: " + Circle1.getArea());
//        System.out.println("Perimeter: " + Circle1.getPerimeter());
//
//        Circle Circle2 = new Circle(2.5, "blue", false);
//        System.out.println(Circle2);
//        System.out.println("Area: " + Circle2.getArea());
//        System.out.println("Perimeter: " + Circle2.getPerimeter());
//    }
}