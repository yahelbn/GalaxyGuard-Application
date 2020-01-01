package com.example.game2;

public class UserRecord {

    private double latitude;
    private double longtitude;
    private String text;
    private int Score;

    public UserRecord(double latitude, double longtitude, String text, int score) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.text = text;
        Score = score;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public String getText() {
        return text;
    }

    public int getScore() {
        return Score;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setScore(int score) {
        Score = score;
    }
}
