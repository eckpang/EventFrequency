package com.company.exercise.datastore;

import com.company.exercise.datastore.data.*;
import com.company.exercise.message.EventMessage;
import com.company.exercise.message.Message;
import com.company.exercise.message.AccountMessage;

import java.util.Map;

/***
 * An implementation of DataStore that stores state information in-memory
 *
 * This class is thread-safe
 */
public class InMemoryDataStore implements DataStore {
  private DataCache cache;

  public InMemoryDataStore() {
    cache = new DataCache();
  }

  @Override
  public void storeMessage(Message message) {
    if (AccountMessage.class.isAssignableFrom(message.getClass())) {
      cache.storeAccountMessage((AccountMessage) message);
    } else if (EventMessage.class.isAssignableFrom(message.getClass())) {
      cache.storeEventMessage((EventMessage) message);
    }
  }

  @Override
  public EventFrequency getEventFrequency() {
    return cache.getEventFrequency();
  }

  @Override
  public Map<AccountKey, Account> getAccounts() {
    return cache.getAccounts();
  }

  @Override
  public Map<UserKey, User> getUsers() {
    return cache.getUsers();
  }
}
