import java.util.ArrayList;

public class Player {
    private static final int TOTAL_FRAMES = 10;
    private static final int NORMAL_FRAMES = 9;
    
    private String name;
    private int activeFrameIndex;
    private ArrayList<Frame> frames;

    public Player(String name) {
        this.name = name;
        this.activeFrameIndex = 0;
        this.frames = new ArrayList<>();
        
        for (int i = 0; i < NORMAL_FRAMES; i++) frames.add(new Frame());
        frames.add(new Frame(true));
    }

    public String getName() { return this.name; }
    public boolean isDone() { return activeFrameIndex == TOTAL_FRAMES; }

    public boolean roll(int pins) {
        if (this.activeFrameIndex >= TOTAL_FRAMES) return false;
        
        Frame currentFrame = frames.get(activeFrameIndex);
        currentFrame.roll(pins);
        
        boolean isFrameComplete = (currentFrame.getType() != Frame.Type.ONGOING);
        
        if (isFrameComplete) this.activeFrameIndex++;
        
        return isFrameComplete;
    }

    public void display() {
        //Display Name
        System.out.printf("%-10s|", this.name);
        
        //Display Rolls
        for (Frame frame : frames) frame.displayRoll();
        System.out.print("     |\n");

        //Display Scores
        System.out.print("          |");
        
        int totalScore = 0;
        for (int i = 0; i < TOTAL_FRAMES; i++) {
            totalScore += frames.get(i).displayScore(frames, i);
        }
        
        System.out.printf("%5s|\n", totalScore);
    }
}
