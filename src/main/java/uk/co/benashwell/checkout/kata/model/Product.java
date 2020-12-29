package uk.co.benashwell.checkout.kata.model;

public class Product {

    private final String name;
    private final double value;
    private SpecialOffer specialOffer;

    public Product(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public Product(String name, double value, SpecialOffer specialOffer) {
        this.name = name;
        this.value = value;
        this.specialOffer = specialOffer;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public SpecialOffer getSpecialOffer() {
        return specialOffer;
    }

    public String toString() {
        String result = name + " - " + value;

        if(specialOffer != null) {
            result = result + " " + specialOffer.toString();
        }

        return result;
    }
}
