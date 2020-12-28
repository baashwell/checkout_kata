package uk.co.benashwell.checkout.kata;

import java.util.Scanner;
import java.util.logging.Logger;

import uk.co.benashwell.checkout.kata.exception.InvalidCommandException;
import uk.co.benashwell.checkout.kata.service.CommandService;

/**
 * Main Runner class that will continually loop and ask for commands from the user.
 */
public class Runner {

    private static final Logger LOGGER = Logger.getLogger(Runner.class.getName());

    public static void main(String[] args) {

        CommandService commandService = new CommandService();
        Scanner scanner = new Scanner(System.in);

        //todo no tests for this loop as there is very little unit logic. Perhaps think about refactoring all other than the loop into a testable method
        do {
            try {
                LOGGER.info("Please input a command");
                String input = scanner.nextLine();

                //todo perhaps have this return a boolean that will tell us if we should continue processing rather than system exit lower down
                commandService.processCommand(commandService.getCommand(input));
            } catch (InvalidCommandException e) {
                //this is a catch for an invalid command being provided this will then continue the loop to ask for another command.
                LOGGER.warning("The command you have provided is not valid. Please use the list_commands command" +
                        " to see all commands currently available");
            }

        } while (true);
    }
}
