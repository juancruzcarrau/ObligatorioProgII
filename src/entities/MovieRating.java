package entities;

import TADs.listaSimpleFC.ListaEnlazada;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class MovieRating {

    private final float weightedAverage;
    private final int totalVotes;
    private final float meanVote;
    private final float medianVote;
    private ListaEnlazada<Rating> votesRating;

    public MovieRating(String[] metadata) {
        this.weightedAverage = parseFloat(metadata[1]);
        this.totalVotes = parseInt(metadata[2]);
        this.meanVote = parseFloat(metadata[3]);
        this.medianVote = parseFloat(metadata[4]);

        ListaEnlazada<Rating> listaRatings = new ListaEnlazada<>();

        for (int i = 15; i <= 48; i = i + 2) {
            if(!metadata[i].isEmpty() && !metadata[i+1].isEmpty()){
                Rating rating = new Rating(parseFloat(metadata[i]), parseFloat(metadata[i+1]));
                listaRatings.add(rating);
            }
        }

        this.votesRating = listaRatings;
    }

    public float getWeightedAverage() {
        return weightedAverage;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public float getMeanVote() {
        return meanVote;
    }

    public float getMedianVote() {
        return medianVote;
    }

    public ListaEnlazada<Rating> getVotesRating() {
        return votesRating;
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
