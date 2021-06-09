package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class CastMember {

    private final String imdbNameId;
    private final String name;
    private final String birthName;
    private final int height;
    private final String bio;
    private final Date birthDate;
    private final String birthState;
    private final String birthCountry;
    private final String birthCity;

    public void setCauseOfDeath(CauseOfDeath causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    private CauseOfDeath causeOfDeath;
    private final Date deathDate;
    private final String deathState;
    private final String deathCountry;
    private final String deathCity;
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
        this.birthDate = !metadata[6].isEmpty() ? new SimpleDateFormat("yyyy-MM-dd").parse(metadata[6]) : null;
        this.birthState = placeSeparator(metadata[7])[1];
        this.birthCountry = placeSeparator(metadata[7])[2];
        this.birthCity = placeSeparator(metadata[7])[0];
        this.causeOfDeath = null;
        this.deathDate = !metadata[9].isEmpty() ? new SimpleDateFormat("yyyy-MM-dd").parse(metadata[9]) : null;
        this.deathState = placeSeparator(metadata[10])[1];
        this.deathCountry = placeSeparator(metadata[10])[2];
        this.deathCity = placeSeparator(metadata[10])[0];
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

    public Date getBirthDate() {
        return birthDate;
    }

    public String getBirthState() {
        return birthState;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public CauseOfDeath getCauseOfDeath() {
        return causeOfDeath;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public String getDeathState() {
        return deathState;
    }

    public String getDeathCountry() {
        return deathCountry;
    }

    public String getDeathCity() {
        return deathCity;
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

    private String[] placeSeparator(String data) {
        String[] info = new String[3];
        String[] strArray = data.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        if (strArray.length == 1) { // SI SOLO HAY INFO DE PAIS
            info[0] = null;
            info[1] = null;
            info[2] = strArray[0];
        }

        else if (strArray.length == 2) { // INFO DE CIUDAD Y PAIS
            info[0] = strArray[0];
            info[1] = null;
            info[2] = strArray[1];
        }

        else { // SI HAY INFO DE CIUDAD, ESTADO Y PAIS
            info = strArray;
        }

        return info;
    }

}