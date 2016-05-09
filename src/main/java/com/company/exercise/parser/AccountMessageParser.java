package com.company.exercise.parser;

import com.google.inject.Inject;
import com.company.exercise.message.AccountMessage;
import com.company.exercise.message.Message;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

/***
 * Parses an account message using the Jackson POJO mapper
 */
public class AccountMessageParser implements MessageParser {
  private ObjectMapper objectMapper;

  @Inject
  AccountMessageParser(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Optional<Message> parse(String line) {
    try {
      return Optional.of(objectMapper.readValue(line, AccountMessage.class));
    } catch (IOException ex) {
      return Optional.empty();
    }
  }
}
