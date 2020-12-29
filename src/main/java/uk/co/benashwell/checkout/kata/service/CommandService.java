package uk.co.benashwell.checkout.kata.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import uk.co.benashwell.checkout.kata.exception.InvalidCommandException;
import uk.co.benashwell.checkout.kata.model.Command;
import uk.co.benashwell.checkout.kata.model.Product;

public class CommandService {

    private ShopService shopService;

    public CommandService() {
        shopService = new ShopService("default-products-with-special-offers.txt");
    }

    //this constructor will be used for testing to mock the shop service
    public CommandService(ShopService shopService) {
        this.shopService = shopService;
    }

    /**
     * Get Command that relates to given command
     * @param command value to look up in commands
     * @return Command relating to the value provided
     * @throws InvalidCommandException If value is not found in list of commands
     */
    public Command getCommand(String command) throws InvalidCommandException {
        //check if the command is null or empty prior to checking rest of the command
        if(command == null || command.equals("")) {
            throw new InvalidCommandException();
        }

        //loop through all commands and find one that matches
        //if none match then throw exception
        return Arrays.stream(Command.values())
                .filter(value -> value.getCommandToMatch().equals(command))
                .findFirst()
                .orElseThrow(InvalidCommandException::new);
    }

    /**
     * Get a list of all command values
     * @return List of command values
     */
    private List<String> getListOfCommandValues() {
        return Arrays.stream(Command.values())
                .map(Command::getCommandToMatch)
                .collect(Collectors.toList());
    }

    /**
     * Carry out a given command
     * @param command Command to carry out
     * @param arguments any additional arguments found from user
     * @return return string to log, future iterations could return result object with result text and status etc
     */
    public String processCommand(Command command, List<String> arguments) {
        switch (command) {
            case CLOSE:
                System.exit(0);
                return "";
            case LIST_COMMANDS:
                String listOfCommands = String.join(", ", getListOfCommandValues());
                return "Here is a list of valid commands: " + listOfCommands;
            case LIST_PRODUCTS:
                return processListProducts();
            case ADD_PRODUCT_T0_CART:
                return processAddProductToCart(arguments);
            case LIST_CART:
                return processListCart();
            case CHECKOUT:
                return processCheckout();
            default:
                return "";
        }
    }

    private String processCheckout() {
        String result = "You have no Items in your cart to checkout.";
        Map<Product, Integer> cart = shopService.getCart();

        if (!cart.isEmpty()) {
            result = "Checkout successful, the total cost of your cart was " + shopService.checkout();
        }

        return result;
    }

    private String processListCart() {
        String result = "Your Cart is Empty";
        Map<Product, Integer> cart = shopService.getCart();

        if (!cart.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            for (Map.Entry<Product, Integer> item : cart.entrySet()) {
                builder.append(item.getValue()).append(" x ").append(item.getKey().getName()).append("\n");
            }

            result = builder.toString();
        }

        return result;
    }

    private String processAddProductToCart(List<String> arguments) {
        String result = "No product was found in your command, please provide a product name to add to cart after the command" +
                " in the following form - add_product_to_cart product_name quantity";

        if(!arguments.isEmpty()) {
            Product product = shopService.getProduct(arguments.get(0));

            if(product == null) {
                result = "The product you provide could not be found in the store, to get a list of products please use the list_products command.";
            } else {
                if (arguments.size() == 1) {
                    result = addProductToCart(product, 1);
                } else {
                    try {
                        result = addProductToCart(product, Integer.parseInt(arguments.get(1)));
                    } catch (NumberFormatException e) {
                        result = "You have provided a Quantity that is not recognised please use a number such as 5, adding it to the command in the following form - add_product_to_cart product_name quantity";
                    }
                }
            }
        }

        return result;
    }

    private String addProductToCart(Product product, int quantity) {
        shopService.addProductToCart(product, quantity);
        return "We have added the following to your cart: " + quantity + " x " + product.getName();
    }

    private String processListProducts() {
        List<Product> products = shopService.getProducts();
        StringBuilder builder = new StringBuilder();

        if (products.isEmpty()) {
            builder.append("There are no products currently in the shop");
        } else {
            builder.append("Please find the list of products below: \n");
            products.forEach(product -> builder.append(product.toString()).append("\n"));
        }

        return builder.toString();
    }
}
