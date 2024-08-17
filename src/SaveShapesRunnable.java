import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

class SaveShapesRunnable implements Runnable {
    private Shape[] shapes;
    private String filename;

    public SaveShapesRunnable(Shape[] shapes, String filename) {
        this.shapes = shapes;
        this.filename = filename;
    }

    @Override
    public void run() {
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Shape shape : shapes) {
                oos.writeObject(shape);
            }
            System.out.println("Shapes have been saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
