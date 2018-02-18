package lab1;

public class Pair {

    private LongNumber longNumber;
    private int number;

    Pair(LongNumber longNumber, int number) {
        this.longNumber = longNumber;
        this.number = number;
    }

    public LongNumber getLongNumber() {
        return longNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setLongNumber(LongNumber longNumber) {
        this.longNumber = longNumber;
    }

}
