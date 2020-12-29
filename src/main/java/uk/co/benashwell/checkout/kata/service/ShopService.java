package uk.co.benashwell.checkout.kata.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import uk.co.benashwell.checkout.kata.utils.MathHelper;
import uk.co.benashwell.checkout.kata.model.Product;
import uk.co.benashwell.checkout.kata.model.Shop;
import uk.co.benashwell.checkout.kata.model.SpecialOffer;
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
        //requires product to be formed of productname,1.10,3 for 2.00
        String[] product = productString.split(",");

        try {
            if (product.length == 2) {
                return new Product(product[0], Double.parseDouble(product[1]));
            } else if (product.length == 3) {
                return new Product(product[0], Double.parseDouble(product[1].stripLeading().stripTrailing()), getSpecialOfferFromString(product[2]));
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            logger.warning("A Product had a price that could not be parsed, please check the input file.");
            return null;
        }
    }

    private SpecialOffer getSpecialOfferFromString(String specialOfferString) {
        String[] specialOffer = specialOfferString.split(" for ");

        try {
            if (specialOffer.length == 2) {
                return new SpecialOffer(Integer.parseInt(specialOffer[0].stripLeading().stripTrailing()), Double.parseDouble(specialOffer[1].stripLeading().stripTrailing()));
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            logger.warning("A Product had a Special Offer that could not be parsed, please check the input file.");
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
            total += MathHelper.getTotal(item.getValue(), item.getKey().getValue(), item.getKey().getSpecialOffer());
        }

        //reset the cart
        cart = new HashMap<>();

        return total;
    }

    /**
     * Change the products that are currently in use in the store
     * This will clear the current cart as they may not be in the new products list
     * @param filename File to populate the products with
     */
    public void changeProductsInShop(String filename) {
        loadProductsIntoShop(filename);
        //reset the cart
        cart = new HashMap<>();
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<Product, Integer> cart) {
        this.cart = cart;
    }
}
