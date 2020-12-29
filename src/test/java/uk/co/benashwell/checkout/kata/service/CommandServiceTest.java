package uk.co.benashwell.checkout.kata.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
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
    void getCommandWhenProvidedIsNullCommand() {
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
        assertEquals(Command.ADD_PRODUCT_T0_CART, commandService.getCommand("add_product_to_cart"));
        assertEquals(Command.LIST_CART, commandService.getCommand("list_cart"));
    }

    @Test
    @DisplayName("Process the list of commands command to receive a list with all commands available")
    void processListCommandsCommand() {
        String result = commandService.processCommand(Command.LIST_COMMANDS, Collections.emptyList());
        assertTrue(result.contains("close"));
        assertTrue(result.contains("list_commands"));
        assertTrue(result.contains("list_products"));
        assertTrue(result.contains("add_product_to_cart"));
        assertTrue(result.contains("list_cart"));
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
        String result = commandService.processCommand(Command.LIST_PRODUCTS, Collections.emptyList());

        assertTrue(result.contains(productA.toString()));
        assertTrue(result.contains(productB.toString()));
        assertTrue(result.contains(productC.toString()));
    }

    @Test
    @DisplayName("Process the list of Products command when there are no products gives relevant message")
    void processListProductsCommandEmptyProductsList() {
        when(shopService.getProducts()).thenReturn(Collections.emptyList());
        assertEquals("There are no products currently in the shop", commandService.processCommand(Command.LIST_PRODUCTS, Collections.emptyList()));
    }

    @Test
    @DisplayName("Process the add to cart command when no product found after then gives relevant error message")
    void processAddToCartNoProduct() {
        assertEquals("No product was found in your command, please provide a product name to add to cart after the command in the following form - add_product_to_cart product_name quantity",
                commandService.processCommand(Command.ADD_PRODUCT_T0_CART, Collections.emptyList()));
    }

    @Test
    @DisplayName("Process the add to cart command when product provided is not found gives relevant error message")
    void processAddToCartProductNotFound() {
               assertEquals("The product you provide could not be found in the store, to get a list of products please use the list_products command.",
                commandService.processCommand(Command.ADD_PRODUCT_T0_CART, Collections.singletonList("PRODUCT NOT THERE")));
    }

    @Test
    @DisplayName("Process the add to cart command when product found but no quantity adds one of the product")
    void processAddToCartProductWithNoQuantity() {
        when(shopService.getProduct("Product")).thenReturn(new Product("Product", 1.00D));
        assertEquals("We have added the following to your cart: 1 x Product",
                commandService.processCommand(Command.ADD_PRODUCT_T0_CART, Collections.singletonList("Product")));
    }

    @Test
    @DisplayName("Process the add to cart command with product and quantity adds the quantity of the product")
    void processAddToCartProductWithQuantity() {
        when(shopService.getProduct("Product")).thenReturn(new Product("Product", 1.00D));
        assertEquals("We have added the following to your cart: 4 x Product",
                commandService.processCommand(Command.ADD_PRODUCT_T0_CART, Arrays.asList("Product", "4")));
    }

    @Test
    @DisplayName("Process the add to cart command with product and quantity where quantity is text")
    void processAddToCartProductWithQuantityThatIsNotANumber() {
        when(shopService.getProduct("Product")).thenReturn(new Product("Product", 1.00D));
        assertEquals("You have provided a Quantity that is not recognised please use a number such as 5, adding it to the command in the following form - add_product_to_cart product_name quantity",
                commandService.processCommand(Command.ADD_PRODUCT_T0_CART, Arrays.asList("Product", "TEXT")));
    }

    @Test
    @DisplayName("Process the list cart command with an empty cart shows you relevant message")
    void processListCartWithAnEmptyCart() {
        when(shopService.getCart()).thenReturn(Collections.emptyMap());
        assertEquals("Your Cart is Empty", commandService.processCommand(Command.LIST_CART, Collections.emptyList()));
    }

    @Test
    @DisplayName("Process the list cart command with a populated cart shows you relevant message")
    void processListCart() {
        Product productA = new Product("ProductA", 2.00D);
        Product productB = new Product("ProductB", 3.00D);
        Map<Product, Integer> cartMap = Stream.of(
                new AbstractMap.SimpleEntry<>(productA, 2),
                new AbstractMap.SimpleEntry<>(productB, 5))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        when(shopService.getCart()).thenReturn(cartMap);

        assertTrue(commandService.processCommand(Command.LIST_CART, Collections.emptyList()).contains("2 x ProductA"));
        assertTrue(commandService.processCommand(Command.LIST_CART, Collections.emptyList()).contains("5 x ProductB"));
    }

}