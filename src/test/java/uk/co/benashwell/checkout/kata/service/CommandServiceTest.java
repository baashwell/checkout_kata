package uk.co.benashwell.checkout.kata.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.co.benashwell.checkout.kata.exception.InvalidCommandException;
import uk.co.benashwell.checkout.kata.model.Command;

class CommandServiceTest {

    private CommandService commandService;

    @BeforeEach
    public void setup() {
        commandService = new CommandService();
    }

    @Test
    @DisplayName("Get Command when providing a null value throws exception")
    void getCommandWhenProvidedIsNullCommand() throws InvalidCommandException {
        assertThrows(InvalidCommandException.class, () -> commandService.getCommand(null));
    }

    @Test
    @DisplayName("Get Command when providing an empty value throws exception")
    void getCommandWhenProvidedEmptyCommand() {
        assertThrows(InvalidCommandException.class, () -> commandService.getCommand(""));
    }

    @Test
    @DisplayName("Get Command when providing an invalid value throws an exception")
    void getCommandWhenProvidedCommandIsNotValid() {
        assertThrows(InvalidCommandException.class, () -> commandService.getCommand("INVALID COMMAND"));
    }

    @Test
    @DisplayName("Get command when providing valid commands gives the expected commands")
    void getCommandWhenProvidedCommandIsValid() throws InvalidCommandException {
        assertEquals(Command.CLOSE, commandService.getCommand("close"));
        assertEquals(Command.LIST_COMMANDS, commandService.getCommand("list_commands"));
    }

    @Test
    @DisplayName("Process the list of commands command to receive a list with all commands available")
    void processListCommandsCommand() {
        assertTrue(commandService.getListOfCommandValues().contains("close"));
        assertTrue(commandService.getListOfCommandValues().contains("list_commands"));
    }

}