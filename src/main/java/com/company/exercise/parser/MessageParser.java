package com.company.exercise.parser;

import com.company.exercise.message.Message;

import java.util.Optional;

/***
 * Parses a generic JSON message
 */
public interface MessageParser {
  /***
   * Parses a line that contains a JSON message
   * @param line The line that contains the JSON message
   * @return If the line is not a valid JSON object, returns Optional.empty.
   *         Otherwise returns the message in the Optional value.
   */
  Optional<Message> parse(String line);
}
