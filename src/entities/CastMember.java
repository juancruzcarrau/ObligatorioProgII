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
        this.height = parseInt(metadata[3]);
        this.bio = metadata[4];
        this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(metadata[6]);
        this.birthState = metadata[7];
        this.birthCountry = metadata[8];
        this.birthCity = metadata[9];
        this.deathDate = new SimpleDateFormat("yyyy-MM-dd").parse(metadata[11]);
        this.deathState = metadata[13];
        this.deathCountry = metadata[14];
        this.deathCity = metadata[12];
        this.spousesString = metadata[16];
        this.spouses = parseInt(metadata[17]);
        this.divorces = parseInt(metadata[18]);
        this.spousesWithChildren = parseInt(metadata[19]);
        this.children = parseInt(metadata[20]);
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

}