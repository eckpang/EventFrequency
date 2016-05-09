# Event Frequency Exercise

# Build and Run Instruction

- git checkout git@github.com:eckpang/eventfrequency.git
- mvn package
- java -jar target/event-processing-bundle.jar short_stream.json

# Code Overview

This solution uses an in-memory data store to count event frequency. Here is a top-down walk-thru of the important classes:

- The MessageProcessor class opens the message file, calls the MessageParser interface to parse each line into a Message interface. It then stores each Message in the DataStore interface, which produces a EventFrequency object that contains the event frequency counts. Finally it calls the Formatter interface to convert the frequency counts into JSON, and it uses the Output interface to write the JSON output to the console.
- The MessageParser Interface has two implementations - AccountMessageParser and EventMessageParser that are responsible for parsing an account and an event message, respectively. It usees the Jackson Json library to parse the JSON format.
- The Message interface has two implementation - AccountMessage and EventMessage that represent an account and event message. These objects are mapped directly to the JSON using the Jackson library
- The DataStore interface is responsible for processing the messages and keeping track of frequency counts. In the interest of time, only an in-memory data store is implemented. See the Possible Enhancement section to see how this can be enhanced.
- The EventFrequency class stores the state information for frequency counts. It is composed of other classes that keeps track of frequency by day, by account ID, etc
- The Formatter interface is responsible for formatting the EventFrequency class into a serialized string. The only implementation is to format to a JSON string
- The Output interface displays the state to the user. The only implementation now writes out to the console

Note that:
- Java 1.8 is used
- Guice injection is used extensively to make unit testing easier
- Unit testing is included. All tests uses DataProvider. All data provider parameters specifies the input to the test and the expected value

# Possible Enhancements

The solution presented here only uses a in-memory cache - this was done in the interest of development time. I opted for less features but more thoroughness in the current solution. The ideal solution would also involve using a database to flush state information. Here are what can be done to enhance the solution:

- Implement a MySqlDataStore to store EventFrequency, Accounts and Users in Mysql (or some other database)
- Change MessageProcessor to use both InMemoryDataStore and MySqlDataStore. Events are recorded in InMemoryDataStore, and is flushed to MySqlDataStore periodically (like every min). After the flush, clear the InMemoryDataStore to reset the counts.
- Add a UploadProgress table to track the file that is being uploaded and the current line number being processed. Use a transaction to atomically update the UploadProgress and Event Frequency table. 
- When updating the Event Frequency table, increment instead of setting the count so multiple machines can be load balanced to process the stream. 
- The current solution is thread-safe, so it can potentially uses multiple threads to process multiple files at the same time.



