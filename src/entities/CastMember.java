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
    private final int deathDate;
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
        this.deathDate = yearParser(metadata[9]);
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

    public int getDeathDate() {
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

/*    private String[] placeSeparator(String data) {
        String[] info = new String[4];
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

        else if (strArray.length == 3) { // SI HAY INFO DE CIUDAD, ESTADO Y PAIS
            info[0] = strArray[0].trim();
            info[1] = strArray[1].trim();
            info[2] = strArray[2].trim();
        }

        else { // CASO UK: DISTRITO, ESTADO, PAIS, UK
            info[0] = strArray[0].trim();
            info[1] = strArray[1].trim();
            info[2] = strArray[3].trim();
        }

        return info;
    }*/

/*    private Date dateParser(String str) {
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
    }*/

    private int yearParser(String str) {
        int ano = -1;
        String temp;
        if (str != null) {
            if (!str.equals("")) {
                temp = str.replaceAll("[^0-9]", "");
                if (!temp.equals("")) {
                    temp = temp.substring(0, Math.min(temp.length(), 4));
                    ano = parseInt(temp);
                }
            }
        }
        return ano;
    }

}