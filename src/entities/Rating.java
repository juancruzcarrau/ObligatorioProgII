package entities;

public class Rating {

    private float numberVotes;
    private float averageRating;

    public Rating(float averageRating, float numberVotes) {
        this.numberVotes = numberVotes;
        this.averageRating = averageRating;
    }

    public float getNumberVotes() {
        return numberVotes;
    }

    public void setNumberVotes(float numberVotes) {
        this.numberVotes = numberVotes;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }
}
