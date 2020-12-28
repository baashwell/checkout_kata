# Checkout Kata
Simple Supermarket Checkout Using No Frameworks - Maven is use for ease of testing dependency management 
but may be stripped out later if required.

## Running Information
This project has been developed to run on Java 11, there is nothing that is specific to Java 11 in use currently but
in order to use the current POM Compile plugin you will need to have Java 11 or later installed and in use.
### Instructions to run application
In order to run the project on a windows environment please use the following commands to compile.
 This should all be done from the root folder (checkout_kata).
 
```
javac -Xlint:unchecked -d "classes" @sources.txt
java -classpath "classes" uk.co.benashwell.checkout.kata.Runner
```

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

## Task List
Following is a list of high level tasks to carry out, each will need  unit testing, working code, 
manual testing and relevant documentation.

- Add Import of data from a file to populate map of supermarket items, this will be done from a static file on start up
- Add 'List Items' command to allow users to show all items in supermarket
- Add 'Add Item {id}' to allow users to add an item to their basket
- Add 'Checkout' to allow users to calculate their basket, printing the total cost and clearing the basket
- Add in special offers map from static file
- Update checkout to consult this map in calculation
- Allow users to provide a file that will overwrite the current supermarket map
- strip out the use of maven and include JUnit Jar and run instructions