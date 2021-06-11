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
        this.birthDate = dateParser(metadata[6]);
        String[] birth = placeSeparator(metadata[7]);
        this.birthState = birth[1];
        this.birthCountry = birth[2];
        this.birthCity = birth[0];
        this.causeOfDeath = null;
        this.deathDate = dateParser(metadata[9]);
        String[] death = placeSeparator(metadata[10]);
        this.deathState = death[1];
        this.deathCountry = death[2];
        this.deathCity = death[0];
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

        if (strArray.length == 1) { // SI SOLO HAY INFO DE PAIS O NO HAY INFO (String vacio)

            if (strArray[0].isEmpty()) {
                info[0] = null;
                info[1] = null;
                info[2] = null;
            }
            else {
                info[0] = null;
                info[1] = null;
                info[2] = strArray[0].trim();
            }
        }

        else if (strArray.length == 2) { // INFO DE CIUDAD Y PAIS
            info[0] = strArray[0].trim();
            info[1] = null;
            info[2] = strArray[1].trim();
        }

        else { // SI HAY INFO DE CIUDAD, ESTADO Y PAIS
            info[0] = strArray[0].trim();
            info[1] = strArray[1].trim();
            info[2] = strArray[2].trim();
        }

        return info;
    }

    private Date dateParser(String str) {
        Date date = null;
        String test = null;

        if (str.isEmpty()) {
            return null;
        }

        try {

            if (str.length() == 10 && str.charAt(4) == '-') {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            }

            else {
                String prueba = str.replaceAll("[^0-9]","");
                date = new SimpleDateFormat("yyyy").parse(prueba);

                //String[] data = str.split("\\s");
                //if (data[0].length() == 4) {
                //    date = new SimpleDateFormat("yyyy").parse(data[0]);
                //}
                //else {
                    // CASO c.
                //}

            }
        }

        catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}