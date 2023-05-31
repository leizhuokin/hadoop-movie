package org.example.model;

public class MovieLog {
    private String rate;
    private String name;
    private String time;
    private String type;
    private String director;
    private String boxoffice;
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getBoxoffice() {
        return boxoffice;
    }

    public void setBoxoffice(String boxoffice) {
        this.boxoffice = boxoffice;
    }
    @Override
    public String toString() {
        return "MovieLog{" +
                "rate='" + rate + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", director='" + director + '\'' +
                ", boxoffice='" + boxoffice + '\'' +
                '}';
    }

}
