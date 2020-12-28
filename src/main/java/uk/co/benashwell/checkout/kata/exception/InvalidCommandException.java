package uk.co.benashwell.checkout.kata.exception;

public class InvalidCommandException extends Exception {

    @Override
    public String getMessage() {
        return "The provided message is invalid";
    }


}
