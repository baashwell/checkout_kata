package uk.co.benashwell.checkout.kata.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.co.benashwell.checkout.kata.exception.InvalidCommandException;
import uk.co.benashwell.checkout.kata.model.Command;
import uk.co.benashwell.checkout.kata.model.Product;

@ExtendWith(MockitoExtension.class)
class CommandServiceTest {

    private CommandService commandService;

    @Mock
    private ShopService shopService;

    @BeforeEach
    public void setup() {
        commandService = new CommandService(shopService);
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
        assertEquals(Command.LIST_PRODUCTS, commandService.getCommand("list_products"));
    }

    @Test
    @DisplayName("Process the list of commands command to receive a list with all commands available")
    void processListCommandsCommand() {
        String result = commandService.processCommand(Command.LIST_COMMANDS);
        assertTrue(result.contains("close"));
        assertTrue(result.contains("list_commands"));
        assertTrue(result.contains("list_products"));
    }

    @Test
    @DisplayName("Process the list of products command to receive a list of the products as a string")
    void processListProductsCommand() {
        Product productA = new Product("Product A", 10.99D);
        Product productB = new Product("Product B", 1.99D);
        Product productC = new Product("Product C", 5.00D);
        when(shopService.getProducts()).thenReturn(
                Arrays.asList(
                       productA, productB, productC
                )
        );
        String result = commandService.processCommand(Command.LIST_PRODUCTS);

        assertTrue(result.contains(productA.toString()));
        assertTrue(result.contains(productB.toString()));
        assertTrue(result.contains(productC.toString()));
    }

    @Test
    @DisplayName("Process the list of Products command when there are no products gives relevant message")
    void processListProductsCommandEmptyProductsList() {
        when(shopService.getProducts()).thenReturn(Collections.emptyList());
        assertEquals("There are no products currently in the shop", commandService.processCommand(Command.LIST_PRODUCTS));
    }

}