package uk.co.benashwell.checkout.kata.model;

public class Product {

    private final String name;
    private final double value;

    public Product(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public String toString() {
        return name + " - " + value;
    }
}
