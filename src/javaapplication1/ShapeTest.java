public class ShapeTest {
    public static void main(String[] args) {
        Shape[] ls = new Shape[3];
        ls[0] = new Rectangle(1.0 , 2.0);
        ls[1] = new Circle(5);
        ls[2] = new Circle(10);

        for ( int i = 0; i < ls.length; i++){
            System.out.print(ls[i].toString());
            System.out.printf(" Area: %.2f", ls[i].getArea());
            System.out.println();
//            ls[i].center();
        }
    }
}