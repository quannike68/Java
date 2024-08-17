import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ShapeWriter {
    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Rectangle(10, 20));
        shapes.add(new Circle(15));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("shapewriter.txt"))) {
            oos.writeObject(shapes);
            System.out.println("Shapes saved to file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
