package entities;

import java.text.ParseException;
import static java.lang.Integer.parseInt;

public class CastMember {

    private final String imdbNameId;
    private final String name;
    private final String birthName;
    private final int height;
    private final String bio;
    private final int birthDate;
    private final String birthCountry;
    private CauseOfDeath causeOfDeath;
    private final String deathDate;
    private final String deathCountry;
    private final String spousesString;
    private final int spouses;
    private final int divorces;
    private final int spousesWithChildren;
    private final int children;

    public CastMember(String[] metadata) throws ParseException {
        this.imdbNameId = metadata[0];
        this.name = metadata[1];
        this.birthName = metadata[2];
        this.height = !metadata[3].isEmpty() ? parseInt(metadata[3]) : 0;
        this.bio = metadata[4];
        this.birthDate = yearParser(metadata[6]);
        this.birthCountry = !metadata[7].isEmpty() ? metadata[7] : null;
        this.causeOfDeath = null;
        this.deathDate = metadata[9];
        this.deathCountry = metadata[10];
        this.spousesString = metadata[12];
        this.spouses = !metadata[13].isEmpty() ? parseInt(metadata[13]) : 0;
        this.divorces = !metadata[14].isEmpty() ? parseInt(metadata[14]) : 0;
        this.spousesWithChildren = !metadata[15].isEmpty() ? parseInt(metadata[15]) : 0;
        this.children = !metadata[16].isEmpty() ? parseInt(metadata[16]) : 0;
    }

    public String getImdbNameId() {
        return imdbNameId;
    }

    public String getName() {
        return name;
    }

    public String getBirthName() {
        return birthName;
    }

    public int getHeight() {
        return height;
    }

    public String getBio() {
        return bio;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public CauseOfDeath getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(CauseOfDeath causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public String getDeathCountry() {
        return deathCountry;
    }

    public String getSpousesString() {
        return spousesString;
    }

    public int getSpouses() {
        return spouses;
    }

    public int getDivorces() {
        return divorces;
    }

    public int getSpousesWithChildren() {
        return spousesWithChildren;
    }

    public int getChildren() {
        return children;
    }

    private int yearParser(String str) {
        int ano = -1;
        String temp;

        if (str.length() > 3) {
            temp = str.substring(0,4);
            try {
                ano = parseInt(temp);
            }
            catch (Exception ignored) {
            }
        }

        return ano;
    }

}