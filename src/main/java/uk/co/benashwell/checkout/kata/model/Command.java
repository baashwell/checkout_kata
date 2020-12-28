package uk.co.benashwell.checkout.kata.model;

public enum Command {
    CLOSE("close"),
    LIST_COMMANDS("list_commands"),
    LIST_PRODUCTS("list_products");

    final String commandToMatch;

    Command(String commandToMatch) {
        this.commandToMatch = commandToMatch;
    }

    public String getCommandToMatch() {
        return commandToMatch;
    }
}
