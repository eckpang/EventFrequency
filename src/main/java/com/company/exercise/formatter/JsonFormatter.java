package com.company.exercise.formatter;

import com.company.exercise.datastore.data.*;
import com.google.common.base.Strings;

import java.text.SimpleDateFormat;
import java.util.*;

/***
 * An implementation of Formatter that formats into the JSON format specified in the exercise
 * (Note that the resulting JSON is NOT a valid JSON)
 */
public class JsonFormatter implements Formatter {
  private static final String TAB = "  ";
  private static final String NEWLINE = "\n";
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd");
  {
    DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
  }

  @Override
  public String format(EventFrequency eventFrequency,
                       Map<AccountKey, Account> accounts,
                       Map<UserKey, User> users) {
    StringBuilder builder = new StringBuilder();
    builder.append("[");

    SortedSet<String> sortedKeys =
        asSortedSet(eventFrequency.getMap().keySet());
    Iterator<String> it = sortedKeys.iterator();

    while (it.hasNext()) {
      String stream = it.next();
      builder.append(format(eventFrequency.getMap().get(stream), accounts, users));
      if (it.hasNext()) {
        builder.append(",");
        builder.append(NEWLINE);
      }
    }
    builder.append("]");

    return builder.toString();
  }

  /***
   * Format a StreamFrequency object into JSON
   *
   * @param streamFrequency frequency count of a stream
   * @param accounts map of account info
   * @param users map of user info
   * @return JSON string
   */
  private String format(StreamFrequency streamFrequency,
                        Map<AccountKey, Account> accounts,
                        Map<UserKey, User> users) {
    StringBuilder builder = new StringBuilder();
    builder.append("{").append(NEWLINE);
    int numTabs = 1;
    builder.append(getTabs(numTabs)).append("\"accounts\": [").append(NEWLINE);
    builder.append(format(streamFrequency.getFrequencyByAccount(), accounts, numTabs + 1));
    builder.append(getTabs(numTabs)).append("],").append(NEWLINE);

    builder
        .append(getTabs(numTabs))
        .append("\"stream\": \"")
        .append(streamFrequency.getStream()).append("\",")
        .append(NEWLINE);

    builder.append(getTabs(numTabs)).append("\"users\": [").append(NEWLINE);
    builder.append(format(streamFrequency.getFrequencyByUser(), users, numTabs + 1));
    builder.append(getTabs(numTabs)).append("]").append(NEWLINE);
    builder.append("}");
    return builder.toString();
  }

  /***
   * Format a FrequencyByAccount object into JSON
   *
   * @param frequencyByAccount frequency count by account
   * @param accounts map of account info
   * @param numTabs the number of tabs to start from
   * @return JSON string
   */
  private String format(FrequencyByAccount frequencyByAccount,
                        Map<AccountKey, Account> accounts,
                        int numTabs) {
    StringBuilder builder = new StringBuilder();

    SortedSet<String> sortedKeys =
        asSortedSet(
            frequencyByAccount.getMap().keySet(),
            (accountId1, accountId2) -> {
              try {
                AccountKey accountKey1 = new AccountKey(frequencyByAccount.getStream(), accountId1);
                AccountKey accountKey2 = new AccountKey(frequencyByAccount.getStream(), accountId2);
                return accounts.get(accountKey1).getStatus().compareTo(
                    accounts.get(accountKey2).getStatus());
              } catch (Exception ex) {
                throw new RuntimeException("Cannot fetch account status", ex);
              }
            });
    Iterator<String> it = sortedKeys.iterator();

    while (it.hasNext()) {
      String accountId = it.next();
      AccountKey accountKey = new AccountKey(frequencyByAccount.getStream(), accountId);
      builder
          .append(getTabs(numTabs))
          .append("\"")
          .append(accounts.get(accountKey).getStatus())
          .append("\": [").append(NEWLINE);
      builder
          .append(format(frequencyByAccount.getMap().get(accountId), numTabs + 1));
      builder
          .append(getTabs(numTabs))
          .append("]");
      if (it.hasNext()) {
        builder.append(",");
      }
      builder.append(NEWLINE);
    }

    return builder.toString();
  }

  /***
   * Format a FrequencyByUser object into JSON
   *
   * @param frequencyByUser frequency count by user
   * @param users map of user info
   * @param numTabs the number of tabs to start from
   * @return JSON string
   */
  private String format(FrequencyByUser frequencyByUser,
                        Map<UserKey, User> users,
                        int numTabs) {
    StringBuilder builder = new StringBuilder();

    SortedSet<String> sortedKeys =
        asSortedSet(
            frequencyByUser.getMap().keySet(),
            (userId1, userId2) -> {
              String userRole1, userRole2;
              try {
                UserKey userKey1 = new UserKey(frequencyByUser.getStream(), userId1);
                userRole1 = users.get(userKey1).getRole();
              } catch (Exception ex) {
                throw new RuntimeException(
                    String.format(
                        "Cannot fetch account status for stream %s, user %s",
                        frequencyByUser.getStream(),
                        userId1),
                    ex);
              }
              try {
                UserKey userKey2 = new UserKey(frequencyByUser.getStream(), userId2);
                userRole2 = users.get(userKey2).getRole();
              } catch (Exception ex) {
                throw new RuntimeException(
                    String.format(
                        "Cannot fetch account status for stream %s, user %s",
                        frequencyByUser.getStream(),
                        userId2),
                    ex);
              }
              return userRole1.compareTo(userRole2);
            });
    Iterator<String> it = sortedKeys.iterator();

    while (it.hasNext()) {
      String userId = it.next();
      UserKey userKey = new UserKey(frequencyByUser.getStream(), userId);
      builder
          .append(getTabs(numTabs))
          .append("\"")
          .append(users.get(userKey).getRole())
          .append("\": [").append(NEWLINE);
      builder
          .append(format(frequencyByUser.getMap().get(userId), numTabs + 1));
      builder.append(getTabs(numTabs)).append("]");
      if (it.hasNext()) {
        builder.append(",");
      }
      builder.append(NEWLINE);
    }
    return builder.toString();
  }

  /***
   * Format a FrequencyByDay object into JSON
   *
   * @param frequencyByDay frequency count by day
   * @param numTabs the number of tabs to start from
   * @return JSON string
   */
  private String format(FrequencyByDay frequencyByDay, int numTabs) {
    StringBuilder builder = new StringBuilder();

    SortedSet<Date> sortedKeys =
        asSortedSet(frequencyByDay.getMap().keySet());
    Iterator<Date> it = sortedKeys.iterator();

    while (it.hasNext()) {
      Date date = it.next();
      builder
          .append(getTabs(numTabs))
          .append("\"")
          .append(DATE_FORMAT.format(date))
          .append("\": {").append(NEWLINE);
      builder.append(format(frequencyByDay.getMap().get(date), numTabs + 1));
      builder.append(getTabs(numTabs)).append("}");
      if (it.hasNext()) {
        builder.append(",");
      }
      builder.append(NEWLINE);
    }
    return builder.toString();

  }

  /***
   * Format a FrequencyByEvent object into JSON
   *
   * @param frequencyByEvent frequency count by event
   * @param numTabs the number of tabs to start from
   * @return JSON string
   */
  private String format(FrequencyByEvent frequencyByEvent,
                        int numTabs) {
    StringBuilder builder = new StringBuilder();

    SortedSet<String> sortedKeys =
        asSortedSet(frequencyByEvent.getMap().keySet());
    Iterator<String> it = sortedKeys.iterator();

    while (it.hasNext()) {
      String event = it.next();
      builder
          .append(getTabs(numTabs))
          .append("\"")
          .append(event)
          .append("\": ")
          .append(frequencyByEvent.getMap().get(event));
      if (it.hasNext()) {
        builder.append(",");
      }
      builder.append(NEWLINE);
    }
    return builder.toString();
  }

  /***
   * Sort a set by the element's natural order
   *
   * @param s set to sort
   * @return a sorted set
   */
  private static <T> SortedSet<T> asSortedSet(Set<T> s) {
    return new TreeSet<>(s);
  }

  /***
   * Sort a set by order specified by comparator
   *
   * @param s set to sort
   * @return a sorted set
   */
  private static <T> SortedSet<T> asSortedSet(Set<T> s, Comparator<T> comparator) {
    SortedSet set = new TreeSet<>(comparator);
    set.addAll(s);
    return set;
  }

  /***
   * @param numTabs the number of tabs
   * @return a string with the specified number of tabs
   */
  private String getTabs(int numTabs) {
    return Strings.repeat(TAB, numTabs);
  }
}
