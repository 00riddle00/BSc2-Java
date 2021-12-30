package txedt.cmdeditor.shapes;

public class Rectangle extends Shape {

    public Rectangle(){
        type = "Rectangle";
    }

    public void draw() {
        System.out.println("\n+-------------+" +
                           "\n|             |" +
                           "\n|             |" +
                           "\n+-------------+\n");
    }
}
