package org.example.model;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author 。。。。
 * @date 2022/6/20 13:23
 */
public class Movie implements Writable {
    private Integer id;
    private String name;
    private Integer year;
    private Double grade;
    private Integer number;
    private String type;
    private String country;

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(id);
        dataOutput.writeUTF(name);
        dataOutput.writeInt(year);
        dataOutput.writeDouble(grade);
        dataOutput.writeInt(number);
        dataOutput.writeUTF(type);
        dataOutput.writeUTF(country);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readInt();
        this.name = dataInput.readUTF();
        this.year = dataInput.readInt();
        this.grade = dataInput.readDouble();
        this.number = dataInput.readInt();
        this.type = dataInput.readUTF();
        this.country = dataInput.readUTF();
    }

    public Movie() {
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", grade=" + grade +
                ", number=" + number +
                ", type='" + type + '\'' +
                ", coutry='" + country + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public Movie(Integer id, String name, Integer year, Double grade, Integer number, String type, String country) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.grade = grade;
        this.number = number;
        this.type = type;
        this.country = country;
    }
}

