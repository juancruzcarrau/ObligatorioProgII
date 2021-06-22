package entities;

import TADs.arrayList.ArrayListImpl;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class MovieRating {

    private final float weightedAverage;
    private final int totalVotes;
    private final float meanVote;
    private final float medianVote;
    private ArrayListImpl<Integer> votesRating;
    private ArrayListImpl<Rating> listaRatings;

    public MovieRating(String[] metadata) {
        this.weightedAverage = parseFloat(metadata[1]);
        this.totalVotes = parseInt(metadata[2]);
        this.meanVote = parseFloat(metadata[3]);
        this.medianVote = parseFloat(metadata[4]);

        this.votesRating = new ArrayListImpl<>(10);
        this.votesRating.add(parseInt(metadata[5]));
        this.votesRating.add(parseInt(metadata[6]));
        this.votesRating.add(parseInt(metadata[7]));
        this.votesRating.add(parseInt(metadata[9]));
        this.votesRating.add(parseInt(metadata[10]));
        this.votesRating.add(parseInt(metadata[11]));
        this.votesRating.add(parseInt(metadata[12]));
        this.votesRating.add(parseInt(metadata[13]));
        this.votesRating.add(parseInt(metadata[14]));

//        for (int i = 5; i <= 14; i = i + 2) {
//            votesRating.add(parseInt(metadata[i]));
//        }

        this.listaRatings = new ArrayListImpl<>(15);
        for (int i = 15; i <= 48; i = i + 2) {
            if(!metadata[i].isEmpty() && !metadata[i+1].isEmpty()){
                listaRatings.add( new Rating(parseFloat(metadata[i]), parseFloat(metadata[i+1])));
            }
        }
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

    public ArrayListImpl<Integer> getVotesRating() {
        return votesRating;
    }

    public ArrayListImpl<Rating> getListaRatings() {
        return listaRatings;
    }
}
