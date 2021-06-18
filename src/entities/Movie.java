package entities;

import TADs.arrayList.ArrayListImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class Movie implements Comparable<Movie>{

    private final String imbdTitleId;
    private final String title;
    private final String originalTitle;
    private final int year;
    private final Date datePublished;
    private final ArrayListImpl<String> genre;
    private final int duration;
    private final ArrayListImpl<String> country;
    private final String language;
    private final ArrayListImpl<String> director;
    private final ArrayListImpl<String> writer;
    private final String productionCompany;
    private final ArrayListImpl<String> actors;
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

    public Movie(String[] metadata) throws ParseException {
        this.imbdTitleId = metadata[0];
        this.title = metadata[1];
        this.originalTitle = metadata[2];
        this.year = !metadata[3].isEmpty() ? parseInt(metadata[3].replaceAll("[^0-9]","")) : 0;
        this.datePublished = parseDate(metadata[4]);
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

    private Date parseDate(String date) throws ParseException {

        if(date.length() == 4){
            return new SimpleDateFormat("yyyy").parse(date);
        } else {
            String onlyNumbers = date.replaceAll("[^0-9]","");
            if (onlyNumbers.length() == 4){
                return new SimpleDateFormat("yyyy").parse(onlyNumbers);
            } else {
                if (date.contains("-")){
                    return new SimpleDateFormat("yyyy-MM-dd").parse(date);
                } else {
                    return new SimpleDateFormat("dd/MM/yyyy").parse(date);
                }
            }
        }

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

    public ArrayListImpl<String> getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayListImpl<String> getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public ArrayListImpl<String> getDirector() {
        return director;
    }

    public ArrayListImpl<String> getWriter() {
        return writer;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public ArrayListImpl<String> getActors() {
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

    public MovieRating getMovieRating() {
        return movieRating;
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

    private ArrayListImpl<String> listFromString (String s) {
        ArrayListImpl<String> list = new ArrayListImpl<>(10);
        String[] array = s.split(",");

        for (String st : array) {
            list.add(st);
        }

        return list;
    }

    @Override
    public int compareTo(Movie that) {
        if(this.movieRating.getWeightedAverage() > that.movieRating.getWeightedAverage()){
            return 1;
        } else if (this.movieRating.getWeightedAverage() < that.movieRating.getWeightedAverage()) {
            return -1;
        }
        return 0;
    }
}