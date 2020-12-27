# Checkout Kata
Simple Supermarket Checkout Using No Frameworks

## Running Information
In order to run the project please use the following commands to compile the Main class
 and then run the main class. This should all be done from the root folder (checkout_kata).
 If the classes directory does not exist you may need to add it.

```
javac -d "classes" "src/main/java/uk/co/benashwell/checkout/kata/Runner.java"
java -classpath "classes" uk.co.benashwell.checkout.kata.Runner
```

## Task List
Following is a list of high level tasks to carry out, each will need  unit testing, working code, 
manual testing and relevant documentation.

- Create main run cycle that will ask for "commands" from the user
- Add Import of data from a file to populate map of supermarket items, this will be done from a static file on start up
- Add 'List Items' command to allow users to show all items in supermarket
- Add 'Add Item {id}' to allow users to add an item to their basket
- Add 'Checkout' to allow users to calculate their basket, printing the total cost and clearing the basket
- Add in special offers map from static file
- Update checkout to consult this map in calculation
- Allow users to provide a file that will overwrite the current supermarket map