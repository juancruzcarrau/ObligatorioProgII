package entities;

import java.util.Objects;

public class CauseOfDeath {

    private final String name;

    public CauseOfDeath(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CauseOfDeath that = (CauseOfDeath) o;
        return Objects.equals(name, that.name);
    }

}
