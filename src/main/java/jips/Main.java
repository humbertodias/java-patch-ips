package jips;

public class Main {

    public static void main(String... args) {
        final View view = new View();
        final Controller controller = new Controller(view);
        controller.run();
    }

}