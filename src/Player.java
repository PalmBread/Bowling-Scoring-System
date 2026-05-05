import java.util.ArrayList;

public class Player {
    private String name;
    private int active_frame = 0;
    private ArrayList<Frame> frames = new ArrayList<>();

    Player(String name) {
        this.name = name;
        for (int i = 0; i < 9; i++) frames.add(new Frame());
        frames.add(new Frame(true));
    }

    public boolean isDone() { return active_frame == 10; }

    public boolean roll(int pins) {
        if (this.active_frame >= 10) return false;
        Frame frame = frames.get(active_frame);
        frame.roll(pins);
        Boolean done = frame.getType() != Frame.Type.ONGOING;
        if (done) this.active_frame++;
        return done;
    }

    public String getName() { return this.name; }

    public void display() {
        System.out.printf("%-10s", this.name);
        System.out.print("|");
        for (Frame frame : frames) frame.displayRoll();
        System.out.print("     |\n          |");
        for (int i = 0; i < 10; i++) frames.get(i).displayScore(frames, i);
        int totalScore = 0;
        System.out.printf("%5s|\n", totalScore == 0 ? "" : totalScore);
    }
}
