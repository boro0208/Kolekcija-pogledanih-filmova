package rs.gomex.kolekcijapogledanihfilmova.helper.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "movie")
public class MovieDb {
    @DatabaseField(columnName = "id",generatedId = true)
    private int id;

    @DatabaseField(columnName = "movieId")
    private String movieId;

    @DatabaseField(columnName = "poster")
    private String poster;

    @DatabaseField(columnName = "genre")
    private String genre;

    @DatabaseField(columnName = "title")
    private String title;

    @DatabaseField(columnName = "year")
    private String year;

    @DatabaseField(columnName = "runtime")
    private String runtime;

    @DatabaseField(columnName = "plot")
    private String plot;

    @DatabaseField(columnName = "language")
    private String language;

    @DatabaseField(columnName = "awards")
    private String awards;

    public MovieDb() {
    }

    public MovieDb(int id, String movieId, String poster, String genre, String title, String year, String runtime, String plot, String language, String awards) {
        this.id = id;
        this.movieId = movieId;
        this.poster = poster;
        this.genre = genre;
        this.title = title;
        this.year = year;
        this.runtime = runtime;
        this.plot = plot;
        this.language = language;
        this.awards = awards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }
}

