import java.util.Scanner;

public class Selection {
    private static final int MAX_NAME_LENGTH = 10;
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isDone = false;

    public static void start() {
        while (!isDone) {
            App.clear();
            displayMenu();
            
            String choice = scanner.nextLine().strip();
            System.out.println("\n");
            
            switch (choice) {
                case "1": addPlayer(); break;
                case "2": kickPlayer(); break;
                case "3": displayPlayers(); break;
                case "4": tryStartGame(); break;
                default: invalidCommand(); break;
            }

            pause();
        }
    }

    private static void displayMenu() {
        System.out.println("1. Add new Player");
        System.out.println("2. Kick Player");
        System.out.println("3. View Player list");
        System.out.println("4. Start Game");
        System.out.print("\nEnter a number: ");
    }

    public static void addPlayer() {
        System.out.println("Creating new Player");
        System.out.print("\nEnter player name: ");
        String name = scanner.nextLine().toUpperCase();

        if (name.length() > MAX_NAME_LENGTH) {
            System.out.println("Player name can't be more than " + MAX_NAME_LENGTH + " characters.");
        } else if (playerExists(name)) {
            System.out.println("Player '" + name + "' already exists.");
        } else {
            Game.players.add(new Player(name));
            System.out.println("\nSuccessfully created player '" + name + "'.");
        }
    }

    public static void kickPlayer() {
        System.out.println("Kicking of Player");
        System.out.print("\nEnter player name: ");
        String name = scanner.nextLine().toUpperCase();

        Player playerToRemove = getPlayer(name);
        
        if (playerToRemove != null) {
            Game.players.remove(playerToRemove);
            System.out.println("Successfully deleted player '" + name + "'");
        } else {
            System.out.println("Player '" + name + "' not found.");
        }
    }

    public static void displayPlayers() {
        if (Game.players.size() == 0) {
            System.out.println("There are currently no players.");
        } else {
            System.out.println("Player List:");
            for (Player player : Game.players) {
                System.out.println("- " + player.getName());
            }
        }
    }

    public static void tryStartGame() {
        if (Game.players.size() > 0) {
            isDone = true;
        } else {
            System.out.println("There are currently no players.");
        }
    }

    private static boolean playerExists(String name) {
        for (Player player : Game.players) {
            if (player.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    private static Player getPlayer(String name) {
        for (Player player : Game.players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }

        return null;
    }

    public static void invalidCommand() {
        System.out.println("Invalid command.");
    }

    public static void pause() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
