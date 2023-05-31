package org.example.model;
/*
*
* */
public class DouBanLog {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScorenum() {
        return scorenum;
    }

    public void setScorenum(String scorenum) {
        this.scorenum = scorenum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "DouBanLog{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", score='" + score + '\'' +
                ", scorenum='" + scorenum + '\'' +
                ", type='" + type + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    private String id;
    private String name;
    private String year;
    private String score;
    private String scorenum;
    private String type;
    private String country;


}
