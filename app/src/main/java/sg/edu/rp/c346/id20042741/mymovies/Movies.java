package sg.edu.rp.c346.id20042741.mymovies;

import java.io.Serializable;

public class Movies implements Serializable {
    private int _id;
    private String title;
    private String genre;
    private String rating;
    private int year;


    public Movies (int _id, String title, String genre, String rating, int year){
        this._id = _id;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.year = year;

    }

    public int get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
