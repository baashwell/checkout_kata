package uk.co.benashwell.checkout.kata.service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import uk.co.benashwell.checkout.kata.exception.InvalidCommandException;
import uk.co.benashwell.checkout.kata.model.Command;
import uk.co.benashwell.checkout.kata.model.Product;

public class CommandService {

    private ShopService shopService;

    public CommandService() {
        shopService = new ShopService("default-products.txt");
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
     * @return return string to log, future iterations could return result object with result text and status etc
     */
    public String processCommand(Command command) {
        switch (command) {
            case CLOSE:
                System.exit(0);
                return "";
            case LIST_COMMANDS:
                String listOfCommands = String.join(", ", getListOfCommandValues());
                return "Here is a list of valid commands: " + listOfCommands;
            case LIST_PRODUCTS:
                return processListProducts();
            default:
                return "";
        }
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
