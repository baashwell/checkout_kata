# Checkout Kata
Simple Supermarket Checkout Using No Frameworks - Maven is use for ease of testing dependency management 
but may be stripped out later if required.

## Running Information
This project has been developed to run on Java 11, meaning you will need to have Java 11 or later installed and in use.

### Instructions to run application
In order to run the project on a windows environment please use the following commands to compile.
 This should all be done from the root folder (checkout_kata).
 
```
javac -Xlint:unchecked -d "classes" @sources.txt
java -classpath "classes" uk.co.benashwell.checkout.kata.Runner
```

#### Editing the products in the shop
Currently, the products in the shop are populated on start up from the default-products.txt file that
 can be found in the classes directory. You can add/update this file ensuring you keep to the formatting:
 - One product per line
 - comma separate name of product and price and if present special offer - ProductA,1.99,2 for 3.00
 - Products must not have spaces in the name as currently do not handle this when adding them to the cart.
 - Cannot have duplicate product names currently either as we will just find the first one with that name.

### Instructions to run tests using maven
In order to run the tests without having to bundle JUnit JARS within the project I have added a maven pom file that
will act as dependency management and can allow for easy running of a test suite. In order to run the tests in the root 
of the project use the following command.

```
mvn clean test
```

## Implemented Commands
Here is a list of the current commands that have been implemented:
- close - This will close the application
- list_commands - This will list the commands in the application
- list_products - This will list the products currently in the application
- add_product_to_cart {product name} {quantity} - This will add the given quantity of the product name to your cart. 
If you do not provide a quantity it will default to 1.
- list_cart - This will list the items currently in your cart
- checkout - This will checkout your cart, removing all items and showing you the total cost of the cart

## Task List
Following is a list of high level tasks to carry out, each will need  unit testing, working code, 
manual testing and relevant documentation.

- Allow users to provide a file that will overwrite the current supermarket map
- strip out the use of maven and include JUnit Jar and run instructions

## Future Tasks
- Currently, you cannot have spaces in products or duplicate product names due to how we add products to the cart, this can be solved in future 
by adding an id that is used to add the product to cart - this is a much preferred method.
- Formatting of Double values to always be £x.xx 
- Move all messages out to a separate helper, allowing the command service to be used in commandline but also allowing for other interfaces
- Make Special Offer an interface that will then allow different types of special offer to be created and processed
- String helper that will do the strip trailing and leading and parse the value into a double/integer
- Learn how to implement paramterized testing to reduce test methods for checkout