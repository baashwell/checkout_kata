package uk.co.benashwell.checkout.kata.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

        Product productA = products.stream().filter(product -> product.getName().equals("Product A")).findFirst().get();
        assertEquals("Product A", productA.getName());
        assertEquals(1.12D, productA.getValue());

        Product productB = products.stream().filter(product -> product.getName().equals("Product B")).findFirst().get();
        assertEquals("Product B", productB.getName());
        assertEquals(5.00D, productB.getValue());

        Product productC = products.stream().filter(product -> product.getName().equals("Product C")).findFirst().get();
        assertEquals("Product C", productC.getName());
        assertEquals(2.00D, productC.getValue());

        Product productD = products.stream().filter(product -> product.getName().equals("Product D")).findFirst().get();
        assertEquals("Product D", productD.getName());
        assertEquals(15.99D, productD.getValue());
    }

}