import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static final int TOTAL_FRAMES = 10;
    
    private static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Player> players = new ArrayList<>();
    private static int activePlayerIndex = 0;

    public static void start() {
        while (true) {
            Player activePlayer = players.get(activePlayerIndex);

            App.clear();
            displayScoreboard();

            System.out.println("\n" + activePlayer.getName() + " turn to roll...\n");
            System.out.print("pins hit: ");
            
            int pins = getPinInput();
            if (pins == -1) continue;

            boolean frameComplete = activePlayer.roll(pins);
            if (frameComplete) moveToNextPlayer();

            if (isGameComplete()) break;
        }
        
        displayScoreboard();
        System.out.println("\nThe Game has ended.");
    }

    private static int getPinInput() {
        String input = scanner.nextLine();
        
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            return -1;
        }
    }

    private static void moveToNextPlayer() {
        activePlayerIndex++;
        activePlayerIndex %= players.size();
    }

    private static boolean isGameComplete() {
        int finishedPlayerCount = 0;
        
        for (Player player : players) {
            if (player.isDone()) {
                finishedPlayerCount++;
            }
        }
        
        return finishedPlayerCount == players.size();
    }

    public static void displayScoreboard() {
        printFullLine();
        printHeader();
        printFullLine();

        for (Player player : players) player.display();        

        printFullLine();
    }

    private static void printHeader() {
        System.out.print("          |");
        for (int i = 1; i < TOTAL_FRAMES; i++) System.out.printf("  %d|", i);
        System.out.println("   10|TOTAL|");
    }

    public static void printFullLine() {
        System.out.print("~~~~~~~");
        for (int i = 0; i < TOTAL_FRAMES; i++) System.out.print("~~~|");        
        System.out.println("~~~~~|~~~~~|");
    }
}
