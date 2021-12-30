package texedit.main.cmdeditor.shapes;

public class Rectangle extends Shape {

    public Rectangle(){
        type = "Rectangle";
    }

    @Override
    public void draw() {
        System.out.println("\n+-------------+" +
                           "\n|             |" +
                           "\n|             |" +
                           "\n+-------------+\n");
    }
}
