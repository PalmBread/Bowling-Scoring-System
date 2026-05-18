import java.util.ArrayList;

public class Frame {
    private static final int TENTH_FRAME_ROLLS = 3;
    private static final int NORMAL_FRAME_ROLLS = 2;
    private static final int MAX_PINS = 10;
    
    public static enum Type { ONGOING, STRIKE, SPARE, OPEN }
    public static enum Output { DONE, INVALID, SUCCESS }

    private boolean isTenth;
    private Type type;
    private ArrayList<Integer> pinsHit;
    
    public Frame() { this(false);  }
    public Frame(boolean isTenth) { 
        this.isTenth = isTenth;
        this.type = Type.ONGOING;
        this.pinsHit = new ArrayList<>();
    }
    
    public Type getType() { return this.type; }
    public boolean isTenth() { return this.isTenth; }
    public ArrayList<Integer> getPins() { return this.pinsHit; }

    public Output roll(int pins) {
        if (this.type != Type.ONGOING) return Output.DONE;
        if (pins < 0 || pins > MAX_PINS) return Output.INVALID;
        
        int sum = getSum();
        
        if (this.isTenth) {
            return rollTenth(pins, sum);
        } else {
            return rollNormal(pins, sum);
        }
    }

    private Output rollNormal(int pins, int sum) {
        if (sum + pins > MAX_PINS) return Output.INVALID;
        
        this.pinsHit.add(pins);
        
        if (this.pinsHit.size() == NORMAL_FRAME_ROLLS) {
            this.type = Type.OPEN;
        }
        
        if (sum + pins == MAX_PINS) {
            this.type = Type.values()[this.pinsHit.size()];
        }
        
        return Output.SUCCESS;
    }

    private Output rollTenth(int pins, int sum) {
        this.pinsHit.add(pins);
        
        // If didn't get strike or spare on first two rolls, frame is done
        if (this.pinsHit.size() == 2 && sum + pins < MAX_PINS) {
            this.type = Type.OPEN;
        }
        
        // After third roll, classify frame type
        if (this.pinsHit.size() == TENTH_FRAME_ROLLS) {
            this.type = (this.pinsHit.get(0) == MAX_PINS) ? Type.STRIKE : Type.SPARE;
        }
        
        return Output.SUCCESS;
    }

    public void displayRoll() {
        StringBuilder display = new StringBuilder();
        
        for (int i = 0; i < pinsHit.size(); i++) {
            Integer pin = pinsHit.get(i);
            
            if (!isTenth && i == 1 && this.type == Type.SPARE) {
                display.append("\\");
            } else {
                display.append(encode(pin));
            }
            
            if (i < pinsHit.size() - 1) {
                display.append(" ");
            }
        }
        
        int maxRollsForFrame = isTenth ? TENTH_FRAME_ROLLS : NORMAL_FRAME_ROLLS;
        int currentWidth = display.length();
        int targetWidth = maxRollsForFrame * 2 - 1;
        
        for (int i = currentWidth; i < targetWidth; i++) display.append(" ");

        display.append("|");
        System.out.print(display.toString());
    }

    public int displayScore(ArrayList<Frame> frames, int index) {
        int score = this.getScore(frames, index);
        
        String format = isTenth ? "%5s" : "%3s";
        System.out.printf(format, score == -1 ? "" : score);
        System.out.print("|");
        
        return score == -1 ? 0 : score;
    }

    private String encode(int pinCount) {
        if (pinCount == 0) return "-";
        if (pinCount == MAX_PINS) return "X";

        return Integer.toString(pinCount);
    }

    public int getSum() {
        int sum = 0;
        for (Integer pin : pinsHit) {
            sum += pin;
        }
        return sum;
    }

    public int getScore(ArrayList<Frame> frames, int index) {
        if (!this.isTenth) return getScoreNormal(frames, index);        
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
                if (index == 8 || nextFrame.pinsHit.size() != 1) {
                    if (nextFrame.pinsHit.size() <= 1) return -1;
                    score += nextFrame.pinsHit.get(1);
                } else {
                    Frame nextierFrame = frames.get(index + 2);
                    if (nextierFrame.pinsHit.size() == 0) return -1;
                    score += nextierFrame.pinsHit.get(0);
                }

            case SPARE:
                if (nextFrame.pinsHit.size() == 0) return -1;
                score += nextFrame.pinsHit.get(0);
                    
            case OPEN:
                score += this.getSum();
                return score;
        }

        return -1;
    }
}
