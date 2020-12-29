package uk.co.benashwell.checkout.kata.model;

public enum Command {
    CLOSE("close"),
    LIST_COMMANDS("list_commands"),
    LIST_PRODUCTS("list_products"),
    ADD_PRODUCT_T0_CART("add_product_to_cart"),
    LIST_CART("list_cart"),
    CHECKOUT("checkout"),
    CHANGE_PRODUCTS("change_products");

    final String commandToMatch;

    Command(String commandToMatch) {
        this.commandToMatch = commandToMatch;
    }

    public String getCommandToMatch() {
        return commandToMatch;
    }
}
