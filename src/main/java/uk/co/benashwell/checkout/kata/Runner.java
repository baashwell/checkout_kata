package uk.co.benashwell.checkout.kata;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import uk.co.benashwell.checkout.kata.exception.InvalidCommandException;
import uk.co.benashwell.checkout.kata.model.Command;
import uk.co.benashwell.checkout.kata.service.CommandService;
import uk.co.benashwell.checkout.kata.service.ShopService;
import uk.co.benashwell.checkout.kata.utils.ArgumentUtils;

/**
 * Main Runner class that will continually loop and ask for commands from the user.
 */
public class Runner {

    private static final Logger LOGGER = Logger.getLogger(Runner.class.getName());

    public static void main(String[] args) {

        ShopService shopService = new ShopService(ArgumentUtils.getPropertyValue(args, "default-products-with-special-offers.txt"));
        CommandService commandService = new CommandService(shopService);
        Scanner scanner = new Scanner(System.in);

        //todo no tests for this loop as there is very little unit logic. Perhaps think about refactoring all other than the loop into a testable method
        do {
            try {
                LOGGER.info("Please input a command");
                String input = scanner.nextLine();

                //split the input on space so we can get the command and the arguments in a list
                List<String> inputAsList = Arrays.asList(input.split(" "));
                //get the command from the first element of the list
                Command command = commandService.getCommand(inputAsList.get(0));
                //process the command and log the outcome, passing the command and the list of arguments by skipping the first element of the list
                LOGGER.info(commandService.processCommand(command, inputAsList.stream().skip(1).collect(Collectors.toList())));
            } catch (InvalidCommandException e) {
                //this is a catch for an invalid command being provided this will then continue the loop to ask for another command.
                LOGGER.warning("The command you have provided is not valid. Please use the list_commands command" +
                        " to see all commands currently available");
            }

        } while (true);
    }
}
