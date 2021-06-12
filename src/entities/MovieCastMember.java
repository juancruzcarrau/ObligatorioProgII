package entities;

import TADs.listaSimpleFC.ListaEnlazada;

import static java.lang.Integer.parseInt;

public class MovieCastMember {

    private final String movieID;
    private final int ordering;
    private final String actorID;
    private final String category;
    private final String job;
    private final ListaEnlazada<String> characters;

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

    public ListaEnlazada<String> getCharacters() {
        return characters;
    }

    private ListaEnlazada<String> listFromString (String s) {
        ListaEnlazada<String> list = new ListaEnlazada<>();
        String[] array = s.split(",");

        for (String st : array) {
            list.add(st);
        }

        return list;
    }

}
