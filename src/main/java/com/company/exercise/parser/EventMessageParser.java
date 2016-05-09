package com.company.exercise.parser;

import com.company.exercise.message.Message;
import com.google.inject.Inject;
import com.company.exercise.message.EventMessage;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

/***
 * Parses an event message using the Jackson POJO mapper
 */
public class EventMessageParser implements MessageParser {
  private ObjectMapper objectMapper;

  @Inject
  EventMessageParser(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Optional<Message> parse(String line) {
    try {
      return Optional.of(objectMapper.readValue(line, EventMessage.class));
    } catch (IOException ex) {
      return Optional.empty();
    }
  }
}
