# Tyche - A Small Betting Engine

Tyche is my stab at building a small, very basic betting engine.

## Why the Name?

[Tyche](https://en.wikipedia.org/wiki/Tyche) is the Greek Goddess of luck hence the name of the betting engine.

## Approach

I tried to go for the simplest approach possible. Most of the action happens in the `domain` package.

I am fairly new to the domain so I took a data driven approach:

* Each domain object is responsible for ensuring it is constructed correctly.
* Each domain object is responsible for enforcing its rules.
* Domain objects are immutable.
    * "Modifications" result in a new domain object being returned.
    * This immutability makes the code far more deterministic and easier to test.
    * All domain objects are data classes which makes creating new copies of the object really easy using a data classe's `copy` method.

I treat the DAO as a port (as in a port in a hexagonal architecture):

* Mutation is allowed here but only for the storage structures used by the DAO.
    * Domain objects are still used internally but these themselves are immutable.
* A basic in-memory implementation was created.
* A single DAO class was used, if this gets more complex/bigger in future it can be split apart as needed.
    
 Have a look at the `Main` class or/and the tests to get a feel for how these objects interact and operate.

## Howtos

### Tests

From the root of the project simply run:

```bash
./gradlew test
```

### Example

There is an example in the `Main` class. To run this simply run:

```bash
./gradlew runFinalJar

# Or just run a build which will execute this at the end of the build
./gradlew build
```


