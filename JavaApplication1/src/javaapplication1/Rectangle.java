/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;

/**
 *
 * @author Admin
 */

public class Rectangle extends Shape {
    private final double length;
    private final double width;
    
    public Rectangle(){
       this(1.0, 1.0, true, "green");
    }
    
        public Rectangle(double length, double width, boolean fill, String color) {
        super();
        this.length = length;
        this.width = width;
    }



    @Override
    public double area() {
        return length * width;
    }

    @Override
    public double perimeter() {
        return 2 * (length + width);
    }
    
    @Override
        public String toString() {
        return "Shape with fill: " + getFill() + ", color: " + getColor() + 
               ", area: " + area() + ", and perimeter: " + perimeter();
    }
}
