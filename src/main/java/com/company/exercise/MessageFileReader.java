package com.company.exercise;

import com.company.exercise.message.Message;
import com.company.exercise.parser.MessageParser;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Set;

/***
 * Reader that reads the message file
 */
public class MessageFileReader {
  private BufferedReader reader;
  private Set<MessageParser> messageParsers;

  @Inject
  public MessageFileReader(Set<MessageParser> messageParsers) {
    this.messageParsers = messageParsers;
  }

  /***
   * Open the file for reading
   *
   * @param fileName File name to open
   * @throws IOException Exception opening the file
   */
  public void open(String fileName) throws IOException {
    InputStream inputStream = new FileInputStream(fileName);
    reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
  }

  /***
   * Open the file for reading
   *
   * @param inputStream The input stream to read
   * @throws IOException Exception opening the file
   */
  public void open(InputStream inputStream) {
    reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
  }

  /***
   * Close the file
   *
   * @throws IOException Exception opening the file
   */
  public void close() throws IOException {
    reader.close();
  }

  /***
   * Get the next message in the file
   * @return The message
   * @throws IOException Exception parsing the next message
   */
  public Message getNextMessage() throws IOException {
    Preconditions.checkNotNull(
        reader,
        "MessageFileParser is not opened before getNextMessage is called");

    String line;
    if ((line = reader.readLine()) == null) {
      return null;
    }

    for (MessageParser messageParser : messageParsers) {
      Optional<Message> message = messageParser.parse(line);
      if (message.isPresent()) {
        return message.get();
      }
    }

    throw new IOException(
        String.format("Invalid message JSON in input file. Line: %s", line));
  }


}
