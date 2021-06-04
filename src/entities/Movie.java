package entities;

import TADs.listaSimple.ListaEnlazada;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class Movie {

    private final String imbdTitleId;
    private final String title;
    private final String originalTitle;
    private final int year;
    private final Date datePublished;
    private ListaEnlazada<String> genre;
    private final int duration;
    private ListaEnlazada<String> country;
    private final String language;
    private ListaEnlazada<String> director;
    private ListaEnlazada<String> writer;
    private final String productionCompany;
    private ListaEnlazada<String> actors;
    private final String description;
    private final float avgVote;
    private final int votes;
    private final String budget;
    private final String usaGrossIncome;
    private final String worldwideGrossIncome;
    private final float metaScore;
    private final float reviewsFromUsers;
    private final float reviewsFromCritics;
    private float weightedAverage;
    private int totalVotes;
    private float meanVote;
    private float medianVote;
    private ListaEnlazada<Integer> votesRating;

    public Movie(String imbdTitleId, String title, String originalTitle,
                 int year, Date datePublished, ListaEnlazada<String> genre,
                 int duration, ListaEnlazada<String> country, String language,
                 ListaEnlazada<String> director, ListaEnlazada<String> writer,
                 String productionCompany, ListaEnlazada<String> actors,
                 String description, float avgVote, int votes, String budget,
                 String usaGrossIncome, String worldwideGrossIncome,
                 float metaScore, float reviewsFromUsers,
                 float reviewsFromCritics, float weightedAverage, int totalVotes,
                 float meanVote, float medianVote, ListaEnlazada<Integer> votesRating) {

        this.imbdTitleId = imbdTitleId;
        this.title = title;
        this.originalTitle = originalTitle;
        this.year = year;
        this.datePublished = datePublished;
        this.genre = genre;
        this.duration = duration;
        this.country = country;
        this.language = language;
        this.director = director;
        this.writer = writer;
        this.productionCompany = productionCompany;
        this.actors = actors;
        this.description = description;
        this.avgVote = avgVote;
        this.votes = votes;
        this.budget = budget;
        this.usaGrossIncome = usaGrossIncome;
        this.worldwideGrossIncome = worldwideGrossIncome;
        this.metaScore = metaScore;
        this.reviewsFromUsers = reviewsFromUsers;
        this.reviewsFromCritics = reviewsFromCritics;
        this.weightedAverage = weightedAverage;
        this.totalVotes = totalVotes;
        this.meanVote = meanVote;
        this.medianVote = medianVote;
        this.votesRating = votesRating;

    }

    public Movie(String[] metadata) throws ParseException {
        this.imbdTitleId = metadata[0];
        this.title = metadata[1];
        this.originalTitle = metadata[2];
        this.year = parseInt(metadata[3]);
        this.datePublished = new SimpleDateFormat("yyyy-MM-dd").parse(metadata[4]);;
        this.genre = null;                       // metadata[5];
        this.duration = parseInt(metadata[6]);
        this.country = null;                     // metadata[7];
        this.language = metadata[8];
        this.director = null;                    // metadata[9];
        this.writer = null;                      // metadata[10];
        this.productionCompany = metadata[11];
        this.actors = null;                      // metadata[12];
        this.description = metadata[13];
        this.avgVote = parseFloat(metadata[14]);
        this.votes = parseInt(metadata[15]);
        this.budget = metadata[16];
        this.usaGrossIncome = metadata[17];
        this.worldwideGrossIncome = metadata[18];
        this.metaScore = parseFloat(metadata[19]);
        this.reviewsFromUsers = parseFloat(metadata[20]);
        this.reviewsFromCritics = parseFloat(metadata[21]);
        this.weightedAverage = 0;
        this.totalVotes = 0;
        this.meanVote = 0;
        this.medianVote = 0;
        this.votesRating = null;
    }

    // Setters de atributos tipo ListaEnlazada

    public void setGenre(ListaEnlazada<String> genre) {
        this.genre = genre;
    }

    public void setCountry(ListaEnlazada<String> country) {
        this.country = country;
    }

    public void setDirector(ListaEnlazada<String> director) {
        this.director = director;
    }

    public void setWriter(ListaEnlazada<String> writer) {
        this.writer = writer;
    }

    public void setActors(ListaEnlazada<String> actors) {
        this.actors = actors;
    }

    //

    public String getImbdTitleId() {
        return imbdTitleId;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getYear() {
        return year;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public ListaEnlazada<String> getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public ListaEnlazada<String> getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public ListaEnlazada<String> getDirector() {
        return director;
    }

    public ListaEnlazada<String> getWriter() {
        return writer;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public ListaEnlazada<String> getActors() {
        return actors;
    }

    public String getDescription() {
        return description;
    }

    public float getAvgVote() {
        return avgVote;
    }

    public int getVotes() {
        return votes;
    }

    public String getBudget() {
        return budget;
    }

    public String getUsaGrossIncome() {
        return usaGrossIncome;
    }

    public String getWorldwideGrossIncome() {
        return worldwideGrossIncome;
    }

    public float getMetaScore() {
        return metaScore;
    }

    public float getReviewsFromUsers() {
        return reviewsFromUsers;
    }

    public float getReviewsFromCritics() {
        return reviewsFromCritics;
    }

    // MovieRating

    public float getWeightedAverage() {
        return weightedAverage;
    }

    public void setWeightedAverage(float weightedAverage) {
        this.weightedAverage = weightedAverage;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public float getMeanVote() {
        return meanVote;
    }

    public void setMeanVote(float meanVote) {
        this.meanVote = meanVote;
    }

    public float getMedianVote() {
        return medianVote;
    }

    public void setMedianVote(float medianVote) {
        this.medianVote = medianVote;
    }

    public ListaEnlazada<Integer> getVotesRating() {
        return votesRating;
    }

    public void setVotesRating(ListaEnlazada<Integer> votesRating) {
        this.votesRating = votesRating;
    }

}