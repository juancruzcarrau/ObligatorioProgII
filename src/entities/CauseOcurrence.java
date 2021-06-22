package entities;

public class CauseOcurrence implements Comparable<CauseOcurrence> {

    private final CauseOfDeath cause;
    private int ocurrence;

    public CauseOcurrence(CauseOfDeath cause) {
        this.cause = cause;
        this.ocurrence = 0;
    }

    public CauseOfDeath getCause() {
        return cause;
    }

    public int getOcurrence() {
        return ocurrence;
    }

    public void incrementOcurrence() {
        this.ocurrence++;
    }

    @Override
    public int compareTo(CauseOcurrence that) {
        if (this.getOcurrence() > that.getOcurrence()) {
            return 1;
        }
        else if (this.getCause().equals(that.getCause())) {
            return 0;
        }
        else {
            return -1;
        }
    }

}
