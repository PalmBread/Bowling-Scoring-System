import java.util.Scanner;

public class Selection {
    private static Scanner scan = new Scanner(System.in);

    public static void start() {
        while (true) {
            App.clear();

            System.out.println("1. Add new Player");
            System.out.println("2. Kick Player");
            System.out.println("3. View Player list");
            System.out.println("4. Start Game");

            System.out.print("\nEnter a number: ");
            String input = scan.nextLine().strip();
            System.out.println("\n");

            switch (input) {
                case "1": addPlayer(); break;
                case "2": kickPlayer(); break;
                case "3": viewPlayers(); break;
                case "4": return;
                default: invalidCommand(); break;
            }

            pause();
        }
    }

    public static void addPlayer() {
        System.out.println("Creating new Player");
        System.out.print("\nEnter player name: ");
        String name = scan.nextLine().toUpperCase();

        if (name.length() > 10) {
            System.out.println("Player name can't be more than 10 characters.");
        } else if (Game.players.stream().anyMatch(player -> player.getName().equals(name))) {
            System.out.println("Player '"+ name +"' already exists.");
        } else {
            Game.players.add(new Player(name));
            System.out.println("\nSuccessfully created player '" + name + "'.");
        }
    }
    
    public static void kickPlayer() {
        System.out.println("Kicking of Player");
        System.out.print("\nEnter player name: ");
        String name = scan.nextLine().toUpperCase();

        for (Player player : Game.players) {
            if (player.getName().equals(name)) {
                Game.players.remove(player);
                System.out.println("Successfully deleted player '" + name + "'");
                return;
            }
        }
        
        System.out.println("Player '" + name + "' not found.");
    }

    public static void viewPlayers() {
        if (Game.players.size() == 0) {
            System.out.println("There are currently no players.");
        } else {
            System.out.println("Player List:");
            for (Player player : Game.players) System.out.println("- " + player.getName());
        }
    }

    public static void invalidCommand() {
        System.out.println("Invalid command.");
    }

    public static void pause() {
        System.out.print("\nPress Enter to continue...");
        scan.nextLine();
    }
}
