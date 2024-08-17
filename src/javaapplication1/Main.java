import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Shape> shapes = new ArrayList<>();
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Load shapes from file");
            System.out.println("2. Add new shape");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    shapes = readShapesFromFile("shapes.txt");
                    break;
                case 2:
                    addNewShape(shapes, scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static List<Shape> readShapesFromFile(String filename) {
        List<Shape> shapes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            shapes = (List<Shape>) ois.readObject();
            System.out.println(shapes);
            System.out.println("Shapes loaded successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        }
        return shapes;
    }

    private static void saveShapesToFile(List<Shape> shapes, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(shapes);
            System.out.println("Shapes saved successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void addNewShape(List<Shape> shapes, Scanner scanner) {
        System.out.println("Choose shape type:");
        System.out.println("1. Rectangle");
        System.out.println("2. Circle");
        System.out.print("Choose an option: ");
        int shapeType = scanner.nextInt();
        switch (shapeType) {
            case 1:
                System.out.print("Enter width: ");
                double width = scanner.nextDouble();
                System.out.print("Enter height: ");
                double height = scanner.nextDouble();
                shapes.add(new Rectangle(width, height));
                saveShapesToFile(shapes, "shapes.txt");
                break;
            case 2:
                System.out.print("Enter radius: ");
                double radius = scanner.nextDouble();
                shapes.add(new Circle(radius));
                saveShapesToFile(shapes, "shapes.txt");
                break;
            default:
                System.out.println("Invalid shape type.");
        }
    }
}
