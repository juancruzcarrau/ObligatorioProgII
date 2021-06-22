package entities;

import java.util.Objects;

public class CauseOfDeath implements Comparable<CauseOfDeath> {

    private int ocurrencia = 1;
    private final String name;

    public CauseOfDeath(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getOcurrencia() {
        return ocurrencia;
    }

    public void incrementOcurrencia() {
        this.ocurrencia++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CauseOfDeath that = (CauseOfDeath) o;
        return Objects.equals(name, that.name);
    }


    @Override
    public int compareTo(CauseOfDeath that) {

        if (this.getOcurrencia() > that.getOcurrencia()) {
            return 1;
        }
        else if (this.getOcurrencia() == that.getOcurrencia()) {
            return 0;
        }
        else {
            return -1;
        }
    }

}
