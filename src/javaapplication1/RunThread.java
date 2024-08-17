public class RunThread {
    public static void main(String[] args) {
        Shape[] shapes = { new Circle(), new Rectangle(), new Circle(2.2) };
        String filename = "shapes.txt";

        Thread saveThread = new Thread(new SaveShapesRunnable(shapes, filename));
        saveThread.start();

        try {
            saveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread loadThread = new Thread(new LoadShapesRunnable(filename));
        loadThread.start();

        try {
            loadThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
