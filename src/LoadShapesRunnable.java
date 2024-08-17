import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

class LoadShapesRunnable implements Runnable {
    private String filename;

    public LoadShapesRunnable(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                try {
                    Shape shape = (Shape) ois.readObject();
                    System.out.println(shape);
//                    shape.draw();
                } catch (IOException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}