package com.company.exercise;

import com.company.exercise.formatter.Formatter;
import com.company.exercise.message.Message;
import com.google.inject.Inject;
import com.company.exercise.datastore.DataStore;
import com.company.exercise.output.Output;

import java.io.IOException;

public final class MessageProcessor {
  private DataStore dataStore;
  private MessageFileReader messageFileReader;
  private Formatter formatter;
  private Output output;

  @Inject
  public MessageProcessor(
      DataStore dataStore,
      MessageFileReader messageFileReader,
      Formatter formatter,
      Output output) {
    this.dataStore = dataStore;
    this.messageFileReader = messageFileReader;
    this.formatter = formatter;
    this.output = output;
  }

  public void processFile(String fileName) {
    try {
      messageFileReader.open(fileName);
    } catch (IOException ex) {
      output.outputError(String.format("Error opening message file %s", ex));
      return;
    }

    try {
      Message message;
      while ((message = messageFileReader.getNextMessage()) != null) {
        dataStore.storeMessage(message);
      }
      output.outputEventFrequencyJson(
          formatter.format(
              dataStore.getEventFrequency(),
              dataStore.getAccounts(),
              dataStore.getUsers()));
    } catch (IOException ex) {
      output.outputError(String.format("Error while reading message file %s", ex));
    } finally {
      try {
        messageFileReader.close();
      } catch (IOException ex) {
        output.outputError(String.format("Error while closing message file %s", ex));
      }
    }
  }
}
