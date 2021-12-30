package txedt.cmdeditor.shapes;

public class Circle extends Shape {

    public Circle(){
        type = "Circle";
    }

    public void draw() {
        System.out.println("\n  ******  " +
                           "\n****  ****" +
                           "\n**      **" +
                           "\n****  ****" +
                           "\n  ******\n");
    }
}
