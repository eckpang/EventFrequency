package com.company.exercise.datastore.data;

import com.company.exercise.message.AccountMessage;
import com.company.exercise.message.EventMessage;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * Cache that stores state information in memory
 *
 * This class is thread-safe
 */
public class DataCache {
  private final static int MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;

  private final Map<AccountKey, Account> accounts;
  private final Map<UserKey, User> users;
  private final Map<UserKey, AccountKey> userAccounts;
  private final EventFrequency eventFrequency;

  public DataCache() {
    accounts = new ConcurrentHashMap<>();
    users = new ConcurrentHashMap<>();
    userAccounts = new ConcurrentHashMap<>();
    eventFrequency = new EventFrequency();
  }

  public Map<AccountKey, Account> getAccounts() {
    return accounts;
  }

  public Map<UserKey, User> getUsers() {
    return users;
  }

  public Map<UserKey, AccountKey> getUserAccounts() {
    return userAccounts;
  }

  public EventFrequency getEventFrequency() {
    return eventFrequency;
  }

  /***
   * Stores an account message to the data cache
   *
   * @param message An account message
   */
  public void storeAccountMessage(AccountMessage message) {
    AccountKey accountKey = new AccountKey(message.getStream(), message.getAccountId());
    UserKey userKey = new UserKey(message.getStream(), message.getUserId());

    AccountKey accountKeyForUser = userAccounts.get(userKey);
    if (accountKeyForUser != null) {
      if (!accountKeyForUser.equals(accountKey)) {
        throw new IllegalStateException(String.format(
            "Invalid input file - the same user cannot belong to multiple accounts. Stream %s, " +
                "User %s, Account1 %s, Account2 %s",
            userKey.getStream(),
            userKey.getUserId(),
            accountKeyForUser.getAccountId(),
            accountKey.getAccountId()));
      }
    }
    userAccounts.computeIfAbsent(userKey, k -> accountKey);

    accounts.put(
        accountKey,
        new Account(
            message.getStream(),
            message.getAccountId(),
            message.getAccountName(),
            message.getAccountStatus(),
            message.getAccountPlan()));

    users.put(
        userKey,
        new User(
            message.getStream(),
            message.getUserId(),
            message.getUserName(),
            message.getRole()
        )
    );
  }

  /***
   * Stores an event message to the data cache
   *
   * @param message An event message
   */
  public void storeEventMessage(EventMessage message) {
    Date date = new Date(message.getTimestamp() / MILLISECONDS_PER_DAY * MILLISECONDS_PER_DAY);

    eventFrequency.increment(
        message.getStream(),
        message.getAccountId(),
        message.getUserId(),
        date,
        message.getEvent()
    );
  }
}
