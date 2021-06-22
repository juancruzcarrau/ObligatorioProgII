package entities;

import TADs.arrayList.ArrayListImpl;

import static java.lang.Integer.parseInt;

public class MovieCastMember {

    private final String movieID;
    private final int ordering;
    private final String actorID;
    private final String category;
    private final String job;
    private final ArrayListImpl<String> characters;

    public MovieCastMember(String[] metadata) {
        this.movieID = metadata[0];
        this.ordering = !metadata[1].isEmpty() ? parseInt(metadata[1]) : 0;
        this.actorID = metadata[2];
        this.category = metadata[3];
        this.job = metadata[4];
        this.characters = listFromString(metadata[5]);
    }

    public String getMovieID() {
        return movieID;
    }

    public int getOrdering() {
        return ordering;
    }

    public String getActorID() {
        return actorID;
    }

    public String getCategory() {
        return category;
    }

    public String getJob() {
        return job;
    }

    public ArrayListImpl<String> getCharacters() {
        return characters;
    }

    private ArrayListImpl<String> listFromString (String s) {
        ArrayListImpl<String> list = new ArrayListImpl<>(10);

        s = s.replaceAll("\\[", "").replaceAll("\\]",""); // SACO [ ]
        s = s.replaceAll("\"","");

        String[] array = s.split(",");

        for (String st : array) {
            list.add(st);
        }

        return list;
    }

}
