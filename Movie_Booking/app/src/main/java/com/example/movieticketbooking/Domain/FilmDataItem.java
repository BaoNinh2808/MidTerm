package com.example.movieticketbooking.Domain;

public class FilmDataItem {
    private String actors;
    private String poster;
    private String releasedDay;
    private String runTime;
    private String score;
    private String summary;
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public String getTitle() {
        return title;
    }

    public String getActors() {
        return actors;
    }

    public String getPoster() {
        return poster;
    }

    public String getReleasedDay() {
        return releasedDay;
    }

    public String getRunTime() {
        return runTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setReleasedDay(String releasedDay) {
        this.releasedDay = releasedDay;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
