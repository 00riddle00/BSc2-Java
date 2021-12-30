package texedit.main.cmdeditor.shapes;

public class Square extends Shape {

    public Square(){
        type = "Square";
    }

    @Override
    public void draw() {
        System.out.println("\n+------+" +
                           "\n|      |" +
                           "\n|      |" +
                           "\n+------+\n");
    }
}
