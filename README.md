# Event Frequency Exercise

# Build and Run Instruction

```
git init
git checkout git@github.com:eckpang/eventfrequency.git
mvn package
java -jar target/event-processing-bundle.jar short_stream.json
```

# Code Overview

This solution uses an in-memory data store to count event frequency. Here is a top-down walk-thru of the important classes:

- The MessageProcessor class is the top level driver of the program. It opens the message file, calls the MessageParser interface to parse each line into a Message interface. It then ingest each Message into the DataStore interface, which keeps track of the frequency count and returns a EventFrequency object. Finally it calls the Formatter interface to convert the EventFrequency into JSON, and it uses the Output interface to write the JSON output to the console.
- The MessageParser Interface has two implementations - AccountMessageParser and EventMessageParser that are responsible for parsing an account and an event message. It uses the Jackson JSON library to parse the JSON input.
- The Message interface has two implementations - AccountMessage and EventMessage that represent an account and event message. These objects are mapped directly to JSON using the Jackson library's ObjectMapper.
- The DataStore interface is responsible for processing the messages and keeping track of frequency counts. In the interest of development time, only an in-memory data store is implemented. See the Potential Enhancements section to see how this can be enhanced.
- The EventFrequency class stores the state information of frequency counts. It is composed of other classes that keeps track of frequency by day, by account ID, etc
- The Formatter interface is responsible for formatting the EventFrequency class into a serialized string. The only implementation formats to a JSON string
- The Output interface displays the state to the user. The only implementation writes out to the console

# Design Note

- Java 1.8 is used
- Guice injection is used extensively to make unit testing easier, make switching interface implementations easier, and keep the number of constructor parameters small.
- Unit testing is included. All tests use DataProvider. All data provider tuples specify the inputs to the test and the expected values in a consistent manner to make the unit tests more readable and maintainable.
- The design is meant to be extensible. New message types, data stores, input source, display format, etc can be added without requiring core changes to the design.
- The current solution is thread-safe, so it can potentially use multiple threads to do processing at the same time.
- Bonus point #3 of checking a user cannot belong to multiple accounts is included.

# Potential Enhancements

The solution presented here only uses an in-memory cache - this was done in the interest of development time. In implementing this I opted for a more thorough implementation instead of adding in all possible features. With more time, my ideal solution would involve using both an in-memory datastore as a cache and a MySQL datastore (or some other database) to persist state information periodically. The advantages of using both an in-memory cache and a database instead of just a database is that a database write is not required on every single message.

Here are what can be done to enhance the solution in more detail:
- Implement a MySqlDataStore to store EventFrequency, Accounts and Users in Mysql
- Change MessageProcessor to use both InMemoryDataStore and MySqlDataStore. Events are recorded in InMemoryDataStore, and is flushed to MySqlDataStore periodically (like every min). After the flush, clear the InMemoryDataStore to reset the counts.
- Add a UploadProgress table in MySQL to track the file that is being uploaded and the current line number being processed. Use a transaction to atomically update the UploadProgress and Event Frequency tables so on a program or database failure an event is not missed.
- On a program restart, the program can continue from where it left off using the information in the UploadProgress table.
- When updating the Event Frequency table, increment the count instead of setting the count value so multiple instances of the program can modify the same count without stepping on each other. 
- With the enhanced solution, the program should be able to handle a very high thoroughput message stream, where multiple instances can run on 1 or more machines in a cluster to process the stream concurrently. 

# Sample output

Here is the output running the shortstream.json input file:
```
[{
  "accounts": [
    "paying": [
      "2016-04-05": {
        "buy": 471,
        "login": 10,
        "redeem": 477,
        "view": 499
      },
      "2016-04-06": {
        "buy": 1868,
        "redeem": 1832,
        "view": 1737
      }
    ],
    "trial": [
      "2016-04-05": {
        "buy": 216,
        "login": 4,
        "redeem": 204,
        "view": 204
      },
      "2016-04-06": {
        "buy": 748,
        "redeem": 742,
        "view": 731
      }
    ]
  ],
  "stream": "development",
  "users": [
    "admin": [
      "2016-04-05": {
        "buy": 55,
        "login": 1,
        "redeem": 48,
        "view": 52
      },
      "2016-04-06": {
        "buy": 188,
        "redeem": 182,
        "view": 159
      }
    ],
    "user": [
      "2016-04-05": {
        "buy": 46,
        "login": 1,
        "redeem": 47,
        "view": 45
      },
      "2016-04-06": {
        "buy": 178,
        "redeem": 175,
        "view": 168
      }
    ]
  ]
}]
```

