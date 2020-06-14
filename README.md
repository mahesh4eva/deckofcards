# deckofcards
API validation on Deck Of Cards application http://deckofcardsapi.com/

# Prerequisites
1. java IDE (ex: eclipse)
2. Maven configured.
3. Preferably testNG plug-in configured on your IDE for execution.

# Installation
1. Clone this repo on to your local system.
2. Open your IDE (like eclipse) and import the project as Maven -> Existing Maven Projects
    -- This is going to take little time to download missing artifacts and configure
    -- Make sure your IDE is using correct version of JDK (1.8)
3. Run testng.xml file at root directory.

# Structure of tests
All the tests are located under package <b>core.tests</b> <br>
Each REST endpoint has its own class (Tests file) unless if there are similar to each other. <br>
Example: Add and Shuffle card piles are merged to a single Tests file.<br>

<br>core.library</b> package currently has only one class <i>CardsClient</i>. This client is responsible for making the end calls to application. Headers, authentication, baseURLs should be handled here if they are required in future. Also general assertions can be added here to like checking for 5XX response codes and timeouts. <br>
All tests will make use of this class methods instead of directly calling RestAssured methods. (This is to remove direct dependency of REST library in the tests)<br>

### Assertions
Individual tests files get their own assert methods implemented from a Interface.<br>
This helps in properly planning and asserting the response of a particular REST call.<br>
We can also add a grouping feature here where a method will provide a set of assertions. This helps in future if we need to add a particular assertion to all tests.<br>

### Conclusion
Goal in this sample project is to demostrate that tests should be independent of libraries we use. <br>
Ex: If in future, I want to use a different library to make REST calls, we can update the CardsClient. Similarly, Assert library can be easily modified without any changes to test classes.<br>

