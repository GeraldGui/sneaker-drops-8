package com.pluralsight.sneakerdrops;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Sneaker {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private String model;
    private double price;
    private int releaseYear;

    public Sneaker() {
    }

    public Sneaker(String model, double price, int releaseYear) {
        this.model = model;
        this.price = price;
        this.releaseYear = releaseYear;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
