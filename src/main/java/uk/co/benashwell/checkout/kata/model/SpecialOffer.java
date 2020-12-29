package uk.co.benashwell.checkout.kata.model;

public class SpecialOffer {
    private int amountOfItems;
    private double costForAmount;

    public SpecialOffer(int amountOfItems, double costForAmount) {
        this.amountOfItems = amountOfItems;
        this.costForAmount = costForAmount;
    }

    public int getAmountOfItems() {
        return amountOfItems;
    }

    public double getCostForAmount() {
        return costForAmount;
    }

    @Override
    public String toString() {
        return "with a special offer of " + amountOfItems + " for " + costForAmount;
    }
}
