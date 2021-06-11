package entities;

import TADs.listaSimpleFC.ListaEnlazada;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class MovieRating {

    private final float weightedAverage;
    private final int totalVotes;
    private final float meanVote;
    private final float medianVote;
    private ListaEnlazada<Integer> votesRating;

    public MovieRating(float weightedAverage, int totalVotes, float meanVote, float medianVote, ListaEnlazada<Integer> votesRating) {
        this.weightedAverage = weightedAverage;
        this.totalVotes = totalVotes;
        this.meanVote = meanVote;
        this.medianVote = medianVote;
        this.votesRating = votesRating;
    }

    public MovieRating(String[] metadata) {
        this.weightedAverage = parseFloat(metadata[1]);
        this.totalVotes = parseInt(metadata[2]);
        this.meanVote = parseFloat(metadata[3]);
        this.medianVote = parseFloat(metadata[4]);

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

    public ListaEnlazada<Integer> getVotesRating() {
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
