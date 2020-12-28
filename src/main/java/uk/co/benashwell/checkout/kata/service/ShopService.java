package uk.co.benashwell.checkout.kata.service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import uk.co.benashwell.checkout.kata.model.Product;
import uk.co.benashwell.checkout.kata.model.Shop;
import uk.co.benashwell.checkout.kata.utils.FilesUtils;

public class ShopService {

    private Shop shop;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public ShopService(String productsFileName) {
        loadProductsIntoShop(productsFileName);
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
}
