package com.example.movieticketbooking.Domain;

public class FilmGeneralData {
    private Integer id;
    private String posterURL;
    private String score;
    private String title;

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public String getTitle() {
        return title;
    }

    public Integer getId() {
        return id;
    }

    public String getScore() {
        return score;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
