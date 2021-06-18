package entities;

public class Year implements Comparable<Year> {

    private final int year;
    private int ocurrencias;

    public Year(int year) {
        this.year = year;
        this.ocurrencias = 1;
    }

    public int getYear() {
        return year;
    }

    public int getOcurrencias() {
        return ocurrencias;
    }

    public void incrementOcurrencias() {
        this.ocurrencias++;
    }

    @Override
    public int compareTo(Year that) {
        if (this.getOcurrencias() > that.getOcurrencias()) {
            return 1;
        }
        else if (this.getYear() == that.getYear()) {
            return 0;
        }
        else {
            return -1;
        }
    }
}
