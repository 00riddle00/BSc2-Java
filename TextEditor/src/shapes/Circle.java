package shapes;

public class Circle extends Shape {

    public Circle(){
        type = "Circle";
    }

    @Override
    public void draw() {
        System.out.println("\n  ******  " +
                           "\n****  ****" +
                           "\n**      **" +
                           "\n****  ****" +
                           "\n  ******\n");
    }
}
