package com.company.exercise.datastore;

import com.company.exercise.datastore.data.*;
import com.company.exercise.message.Message;

import java.util.Map;

/***
 * A datastore that stores account, user and event frequency state information by
 * ingesting account and event messages.
 */
public interface DataStore {
  /***
   * Stores a message into the datastore
   * @param message A message that contains account, user or event information
   */
  void storeMessage(Message message);

  /***
   * @return Event frequency counts grouped by account, user, date and event
   */
  EventFrequency getEventFrequency();

  /***
   * @return All accounts keyed by AccountKey
   */
  Map<AccountKey, Account> getAccounts();

  /***
   * @return All users keyed by UserKey
   */
  Map<UserKey, User> getUsers();
}
