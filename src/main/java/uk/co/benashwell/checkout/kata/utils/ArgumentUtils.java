package uk.co.benashwell.checkout.kata.utils;

public class ArgumentUtils {

    /**
     * Simple method to return the value of the first property or a default value
     * Can be built on to take criteria etc at a latter date if required
     * @param args args to search through
     * @param defaultValue default value to use if not found
     * @return first property passed in, if not the default value
     */
    public static String getPropertyValue(String[] args, String defaultValue) {
        return args.length > 0 ? args[0] : defaultValue;
    }
}
