package uk.co.benashwell.checkout.kata.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import uk.co.benashwell.checkout.kata.model.Product;
import uk.co.benashwell.checkout.kata.model.Shop;
import uk.co.benashwell.checkout.kata.utils.FilesUtils;

public class ShopService {

    private Shop shop;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Map<Product, Integer> cart = new HashMap<>();

    public ShopService(String productsFileName) {
        loadProductsIntoShop(productsFileName);
    }

    //this is used for testing only currently
    public ShopService(List<Product> products) {
        shop = new Shop(products);
    }

    //todo should this be split into another service?

    /**
     * Load products into shop
     * @param productsFileName Products filename
     */
    public void loadProductsIntoShop(String productsFileName) {
        List<String> fileLines = FilesUtils.getLinesFromFileWithName(productsFileName);
        List<Product> products = getProductsFromLines(fileLines);
        shop = new Shop(products);
    }

    private List<Product> getProductsFromLines(List<String> fileLines) {
        if (fileLines.isEmpty()) {
            logger.warning("No products found so creating a shop with an empty products list.");
        }

        return fileLines
                .stream()
                .map(this::getProductFromString)
                .collect(Collectors.toList());
    }

    private Product getProductFromString(String productString) {
        //require product to be formed of productname,1.10
        String[] product = productString.split(",");

        if (product.length == 2) {
            //todo check the parse
            return new Product(product[0], Double.parseDouble(product[1]));
        } else {
            return null;
        }
    }

    /**
     * Get Products list
     * @return List of Products
     */
    public List<Product> getProducts() {
        return shop.getProducts();
    }

    /**
     * Get a product with the given name
     * @param productName Product name to look for
     * @return Product with the given name or null if not found
     */
    public Product getProduct(String productName) {
        return getProducts()
                .stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Add the given Quantity of a product to the cart
     * @param product Product to add to cart
     * @param quantity amount to add to the cart
     */
    public void addProductToCart(Product product, int quantity) {
        if (cart.containsKey(product)) {
            Integer currentQuantity = cart.get(product);
            cart.put(product, currentQuantity + quantity);
        } else {
            cart.put(product, quantity);
        }
    }

    /**
     * Checkout the current cart
     * @return The total amount of the current cart
     */
    public Double checkout() {
        double total = 0D;

        //calculate the total price of each item
        for (Map.Entry<Product, Integer> item : cart.entrySet()) {
            total += item.getKey().getValue() * item.getValue();
        }

        //reset the cart
        cart = new HashMap<>();

        return total;
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<Product, Integer> cart) {
        this.cart = cart;
    }
}
