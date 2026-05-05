import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static Scanner scan = new Scanner(System.in);
    public static ArrayList<Player> players = new ArrayList<>();
    private static int active_player = 0;

    public static void start() {
        while (true) {
            Player player = players.get(active_player);

            App.clear();
            displayScoreboard();

            System.out.println("\n" +player.getName() + " turn to roll...\n");

            System.out.print("pins hit: ");
            String input = scan.nextLine();
            int pins;

            try {
                pins = Integer.parseInt(input);
            } catch (Exception e) {
                continue;
            }

            System.out.println(pins + "");

            if (player.roll(pins)) active_player++;
            active_player %= players.size();
            
            int u = 0;
            for (Player _player : players) if (_player.isDone()) u++;
            if (u == players.size()) break;
        }
        
        displayScoreboard();
        System.out.println("\nThe Game has ended.");
    }

    public static void displayScoreboard() {
        fullLine();
        System.out.print("          |");
        for (int i = 1; i < 10; i++) System.out.printf("  %d|", i);
        System.out.println("   10|TOTAL|");
        fullLine();
        for (Player player : players) player.display();
        fullLine();
    }

    public static void fullLine() {
        System.out.print("~~~~~~~");
        for (int i = 0; i < 10; i++) System.out.print("~~~|");
        System.out.println("~~~~~|~~~~~|");
    }
}
