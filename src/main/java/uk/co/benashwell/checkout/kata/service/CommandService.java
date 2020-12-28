package uk.co.benashwell.checkout.kata.service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import uk.co.benashwell.checkout.kata.exception.InvalidCommandException;
import uk.co.benashwell.checkout.kata.model.Command;

public class CommandService {

    private Logger logger = Logger.getLogger(CommandService.class.getName());

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
    public List<String> getListOfCommandValues() {
        return Arrays.stream(Command.values())
                .map(Command::getCommandToMatch)
                .collect(Collectors.toList());
    }

    /**
     * Carry out a given command
     * @param command Command to carry out
     */
    public void processCommand(Command command) {
        if (command.equals(Command.CLOSE)) {
            // doing an exit here makes this hard to test.
            System.exit(0);
        } else if (command.equals(Command.LIST_COMMANDS)) {
            String listOfCommands = String.join(", ", getListOfCommandValues());
            logger.info(() -> "Here is a list of valid commands: " + listOfCommands);
        }
    }
}
