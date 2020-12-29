package uk.co.benashwell.checkout.kata.utils;

import uk.co.benashwell.checkout.kata.model.SpecialOffer;

public class MathHelper {

    public static Double getTotal(Integer amountOfItems, Double costOfItem, SpecialOffer specialOffer) {
        double total = 0D;

        while (amountOfItems > 0) {
            if (specialOffer != null && amountOfItems >= specialOffer.getAmountOfItems()) {
                total += specialOffer.getCostForAmount();
                amountOfItems -= specialOffer.getAmountOfItems();
            } else {
                total += amountOfItems * costOfItem;
                amountOfItems = 0;
            }
        }

        return total;
    }
}
