/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;

/**
 *
 * @author Admin
 */
public class Circle extends Shape {
    private final double radius;
    
    public Circle(){
        this(1.0, true, "green");
    }

    public Circle(double radius, String circleFill, String circleColor) {
        super();
        this.radius = radius;
    }
    
        public Circle(double radius, boolean fill, String color) {
        super(fill, color); 
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }
    
     @Override
     public String toString() {
        return "Circle with radius: " + radius + 
               ", fill: " + getFill() + 
               ", color: " + getColor() + 
               ", area: " + area() + 
               ", and perimeter: " + perimeter();
    }
}
