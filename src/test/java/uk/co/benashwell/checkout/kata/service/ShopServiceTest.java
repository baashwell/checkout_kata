package uk.co.benashwell.checkout.kata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uk.co.benashwell.checkout.kata.model.Product;

class ShopServiceTest {

    private ShopService shopService;

    @Test
    @DisplayName("Create Shop with File that does not exist should still create a shop with no products")
    void createShopWithFileThatDoesNotExist() {
        shopService = new ShopService("FILE-DOES-NOT-EXIST.txt");
        assertEquals(0, shopService.getProducts().size());
    }

    @Test
    @DisplayName("Create Shop with a test file that exists in resources and contains the desired products")
    void createShopWithTestFile() {
        shopService = new ShopService("test-products.txt");
        List<Product> products = shopService.getProducts();
        assertEquals(4, products.size());

        Product productA = products.stream().filter(product -> product.getName().equals("ProductA")).findFirst().get();
        assertEquals("ProductA", productA.getName());
        assertEquals(1.12D, productA.getValue());

        Product productB = products.stream().filter(product -> product.getName().equals("ProductB")).findFirst().get();
        assertEquals("ProductB", productB.getName());
        assertEquals(5.00D, productB.getValue());

        Product productC = products.stream().filter(product -> product.getName().equals("ProductC")).findFirst().get();
        assertEquals("ProductC", productC.getName());
        assertEquals(2.00D, productC.getValue());

        Product productD = products.stream().filter(product -> product.getName().equals("ProductD")).findFirst().get();
        assertEquals("ProductD", productD.getName());
        assertEquals(15.99D, productD.getValue());
    }

    @Test
    @DisplayName("Create Shop with a test file that has Special Offers")
    void createShopWithTestFileWithSpecialOffers() {
        shopService = new ShopService("test-products-with-specials.txt");
        List<Product> products = shopService.getProducts();
        assertEquals(4, products.size());

        Product productA = products.stream().filter(product -> product.getName().equals("ProductA")).findFirst().get();
        assertEquals("ProductA", productA.getName());
        assertEquals(1.12D, productA.getValue());
        assertNull(productA.getSpecialOffer());

        Product productB = products.stream().filter(product -> product.getName().equals("ProductB")).findFirst().get();
        assertEquals("ProductB", productB.getName());
        assertEquals(5.00D, productB.getValue());
        assertNotNull(productB.getSpecialOffer());
        assertEquals(3, productB.getSpecialOffer().getAmountOfItems());
        assertEquals(10D, productB.getSpecialOffer().getCostForAmount());

        Product productC = products.stream().filter(product -> product.getName().equals("ProductC")).findFirst().get();
        assertEquals("ProductC", productC.getName());
        assertEquals(2.00D, productC.getValue());
        assertNotNull(productC.getSpecialOffer());
        assertEquals(5, productC.getSpecialOffer().getAmountOfItems());
        assertEquals(7.99D, productC.getSpecialOffer().getCostForAmount());

        Product productD = products.stream().filter(product -> product.getName().equals("ProductD")).findFirst().get();
        assertEquals("ProductD", productD.getName());
        assertEquals(15.99D, productD.getValue());
        assertNull(productD.getSpecialOffer());
    }

    @Test
    @DisplayName("Get Product when product not found returns null")
    void getProductWhenNotInShop() {
        shopService = new ShopService(Collections.emptyList());
        assertNull(shopService.getProduct("NOT IN SHOP"));
    }

    @Test
    @DisplayName("Get Product when product found returns the product")
    void getProductWhenFound() {
        Product product = new Product("ProductD", 15.99D);
        shopService = new ShopService(Collections.singletonList(product));
        assertEquals(product, shopService.getProduct("ProductD"));
    }

    @Test
    @DisplayName("Add Product To Cart When Product Not Already In Cart Adds A New Entry To Cart")
    void addProductToCartWhenNotInCart() {
        Product product = new Product("ProductD", 15.99D);
        shopService = new ShopService(Collections.singletonList(product));
        shopService.addProductToCart(product, 1);
        assertTrue(shopService.getCart().containsKey(product));
        assertEquals(1, shopService.getCart().get(product));
    }

    @Test
    @DisplayName("Add Product To Cart When Product is Already In Cart Adds the quantity To Cart")
    void addProductToCartWhenAlreadyInCart() {
        Product product = new Product("ProductD", 15.99D);
        shopService = new ShopService(Collections.singletonList(product));

        //initial add
        shopService.addProductToCart(product, 1);
        assertTrue(shopService.getCart().containsKey(product));
        assertEquals(1, shopService.getCart().get(product));

        //Second addition
        shopService.addProductToCart(product, 3);
        assertTrue(shopService.getCart().containsKey(product));
        assertEquals(4, shopService.getCart().get(product));
    }

    @Test
    @DisplayName("Checkout with a singular item returns correct value")
    void checkoutWithSingularItem() {
        Product productA = new Product("ProductA", 2.00D);
        shopService = new ShopService(Collections.singletonList(productA));

        Map<Product, Integer> cartMap = Stream.of(
                new AbstractMap.SimpleEntry<>(productA, 2))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        shopService.setCart(cartMap);

        assertEquals(4.0D, shopService.checkout());
    }

    @Test
    @DisplayName("Checkout with a multiple items returns correct value")
    void checkoutWithMultipleItems() {
        Product productA = new Product("ProductA", 2.00D);
        Product productB = new Product("ProductB", 3.00D);
        shopService = new ShopService(Arrays.asList(productA, productB));

        Map<Product, Integer> cartMap = Stream.of(
                new AbstractMap.SimpleEntry<>(productA, 2),
                new AbstractMap.SimpleEntry<>(productB, 5))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        shopService.setCart(cartMap);

        assertEquals(19.0D, shopService.checkout());
    }

    @Test
    @DisplayName("Checkout with a clears the cart")
    void checkoutClearsTheCart() {
        Product productA = new Product("ProductA", 2.00D);
        Product productB = new Product("ProductB", 3.00D);
        shopService = new ShopService(Arrays.asList(productA, productB));

        Map<Product, Integer> cartMap = Stream.of(
                new AbstractMap.SimpleEntry<>(productA, 2),
                new AbstractMap.SimpleEntry<>(productB, 5))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        shopService.setCart(cartMap);

        shopService.checkout();
        assertTrue(shopService.getCart().isEmpty());
    }
}