package txedt.cmdeditor.shapes;

public class Square extends Shape {

    public Square(){
        type = "Square";
    }

    public void draw() {
        System.out.println("\n+------+" +
                           "\n|      |" +
                           "\n|      |" +
                           "\n+------+\n");
    }
}
