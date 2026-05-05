import java.util.ArrayList;

public class Frame {
    public static enum Type { ONGOING, STRIKE, SPARE, OPEN }
    public static enum Output { DONE, INVALID, SUCCESS}

    Frame(boolean tenth) { this.tenth = tenth; }
    Frame() { this(false); }

    private boolean tenth = false;
    private Type type = Type.ONGOING;
    private ArrayList<Integer> pins = new ArrayList<>();;
    
    public Type getType() { return this.type; }
    public boolean isTenth() { return this.tenth; }
    public ArrayList<Integer> getPins() { return this.pins; }

    public Output roll(int pins) {
        if (this.type != Type.ONGOING) return Output.DONE; // Returns when frame is already complete
        if (pins < 0 || pins > 10) return Output.INVALID; // When pin input is not within interval [0, 10]
        int sum = 0;
        for (Integer i : this.pins) sum += i;
        if (this.tenth) return this.roll_tenth(pins, sum);
        else return this.roll_normal(pins, sum);
    }

    private Output roll_normal(int pins, int sum) {
        if (sum + pins > 10) return Output.INVALID; // Returns when the sum of pins if more than 10 but each are less than 10
        this.pins.add(pins);
        if (this.pins.size() == 2) { this.type = Type.OPEN; }
        if (sum + pins == 10) this.type = Type.values()[this.pins.size()];
        return Output.SUCCESS;
    }

    private Output roll_tenth(int pins, int sum) {
        this.pins.add(pins);        
        if (this.pins.size() == 2 && sum + pins < 10) this.type = Type.OPEN;
        if (this.pins.size() == 3) this.type = this.pins.get(0) == 10 ? Type.STRIKE : Type.SPARE;
        return Output.SUCCESS;
    }

    public void log() {
        System.out.println("Type: " + Type.values()[this.type.ordinal()].name());
        for (int i = 0; i < pins.size(); i++) System.out.println("Roll " + (i+1) + ": " + pins.get(i));
        if (pins.size() == 0) System.out.println("No rolls yet");
    }

    public void displayRoll() {
        int isTenth = (tenth ? 1 : 0);
        for (Integer pin : pins) System.out.print(encode(pin) + " ");
        if (pins.size() == 2 + isTenth) System.out.print("\b");
        if (this.type == Type.SPARE) System.out.print("\b\\");
        for (int i = 0; i < (2 + isTenth - pins.size()) * 2 - 1; i++) System.out.print(" ");
        System.out.print("|");
    }

    public int displayScore(ArrayList<Frame> frames, int index) {
        int score = this.getScore(frames, index);
        System.out.printf(tenth ? "%5s" : "%3s", score == -1 ? "" : score);
        System.out.print("|");
        return score == -1 ? 0 : score;
    }

    private String encode(int n) {
        if (n == 0) return "-";
        if (n == 10) return "X";
        return Integer.toString(n);
    }

    public int getSum() {
        int sum = 0;
        for (Integer pin : pins) sum += pin;
        return sum;
    }

    public int getScore(ArrayList<Frame> frames, int index) {
        if (!this.tenth) return getScoreNormal(frames, index);
        if (this.type == Type.ONGOING) return -1;
        return this.getSum();
    }

    private int getScoreNormal(ArrayList<Frame> frames, int index) {
        int score = 0;
        Frame nextFrame = frames.get(index + 1);
        switch (this.type) {
            case ONGOING:
                return -1;

            case STRIKE:
                if (index == 8 || nextFrame.pins.size() != 1) {
                    if (nextFrame.pins.size() <= 1) return -1;
                    score += nextFrame.pins.get(1);
                } else {
                    Frame nextierFrame = frames.get(index + 2);
                    if (nextierFrame.pins.size() == 0) return -1;
                    score += nextierFrame.pins.get(0);
                }

            case SPARE:
                if (nextFrame.pins.size() == 0) return -1;
                score += nextFrame.pins.get(0);
                    
            case OPEN:
                score += this.getSum();
                return score;
        }

        return -1;
    }
}
