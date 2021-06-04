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
    private final ListaEnlazada<String> genre;
    private final int duration;
    private final ListaEnlazada<String> country;
    private final String language;
    private final ListaEnlazada<String> director;
    private final ListaEnlazada<String> writer;
    private final String productionCompany;
    private final ListaEnlazada<String> actors;
    private final String description;
    private final float avgVote;
    private final int votes;
    private final String budget;
    private final String usaGrossIncome;
    private final String worldwideGrossIncome;
    private final float metaScore;
    private final float reviewsFromUsers;
    private final float reviewsFromCritics;
    private MovieRating movieRating;


    public Movie(String imbdTitleId, String title, String originalTitle,
                 int year, Date datePublished, ListaEnlazada<String> genre,
                 int duration, ListaEnlazada<String> country, String language,
                 ListaEnlazada<String> director, ListaEnlazada<String> writer,
                 String productionCompany, ListaEnlazada<String> actors,
                 String description, float avgVote, int votes, String budget,
                 String usaGrossIncome, String worldwideGrossIncome,
                 float metaScore, float reviewsFromUsers,
                 float reviewsFromCritics, MovieRating movieRating) {

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
        this.movieRating = movieRating;

    }

    public Movie(String[] metadata) throws ParseException {
        this.imbdTitleId = metadata[0];
        this.title = metadata[1];
        this.originalTitle = metadata[2];
        this.year = !metadata[3].isEmpty() ? parseInt(metadata[3]) : 0;
        this.datePublished = new SimpleDateFormat("yyyy-MM-dd").parse(metadata[4]);
        this.genre = listFromString(metadata[5]);
        this.duration = !metadata[6].isEmpty() ? parseInt(metadata[6]) : 0;
        this.country = listFromString(metadata[7]);
        this.language = metadata[8];
        this.director = listFromString(metadata[9]);
        this.writer = listFromString(metadata[10]);
        this.productionCompany = metadata[11];
        this.actors = listFromString(metadata[12]);
        this.description = metadata[13];
        this.avgVote = !metadata[14].isEmpty() ? parseFloat(metadata[14]) : 0;
        this.votes = !metadata[15].isEmpty() ? parseInt(metadata[15]) : 0;
        this.budget = metadata[16];
        this.usaGrossIncome = metadata[17];
        this.worldwideGrossIncome = metadata[18];
        this.metaScore = !metadata[19].isEmpty() ? parseFloat(metadata[19]) : 0;
        this.reviewsFromUsers = !metadata[20].isEmpty() ? parseFloat(metadata[20]) : 0;
        this.reviewsFromCritics = !metadata[21].isEmpty() ? parseFloat(metadata[21]) : 0;
    }


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

    public void setMovieRating(MovieRating movieRating) {
        this.movieRating = movieRating;
    }

    @Override
    public int hashCode() {
        int sum = 0;
        for(char c : this.imbdTitleId.toCharArray()){
            sum += c;
        }
        return sum;
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