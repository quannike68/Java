/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;

/**
 *
 * @author Admin
 */
public abstract class Shape {
    private String color;
    private boolean fill;
    
        public Shape() {
        this.fill = true;  
        this.color = "green"; 
    }
    
    public Shape(boolean fill ,String color) {
        this.fill = fill;
        this.color = color;
    }
    
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }


    public abstract double area();
    public abstract double perimeter();
}
