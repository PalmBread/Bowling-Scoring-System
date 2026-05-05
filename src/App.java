public class App {
    public static void main(String[] args) {
        Selection.start();
        Game.start();
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        for (int i = 0; i < 10; i++) System.out.println();
        System.out.flush();
    }
}