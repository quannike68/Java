/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package javaapplication1;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Create and show the GUI
        SwingUtilities.invokeLater(() -> {
            new ShapeGUI().setVisible(true);
        });
    }
}
