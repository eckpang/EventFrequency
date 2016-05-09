package com.company.exercise.datastore.testdata;

import com.company.exercise.message.AccountMessage;
import com.company.exercise.message.EventMessage;

import java.util.Date;

/***
 * Factory for generating Message for testing
 */
public class TestMessageFactory {
  public static final long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000L;

  private static final Long timestampBase = 0L;
  private static final String streamBase = "stream";
  private static final String userIdBase = "userId";
  private static final String accountIdBase = "accountId";
  private static final String eventBase = "event";

  private static final String userNameBase = "userName";
  private static final String userRoleBase = "userRole";
  private static final String accountNameBase = "accountName";
  private static final String accountStatusBase = "accountStatus";
  private static final String accountPlanBase = "accountPlan";

  /***
   * Generate a test event message based on an index. All the fields are set to a base value with
   * the index appended at the end
   *
   * @param index Index based on which to set the event fields
   * @return The EventMessage object
   */
  public static EventMessage createEvent(Integer index) {
    return createEvent(index, index, index, index, index);
  }

  /***
   * Generate a test event message based on an index per field. All the fields are set to a
   * base value with the index appended at the end
   *
   * @param indexTimestamp Index for the timestamp
   * @param indexStream Index for the stream
   * @param indexUserId Index for the user ID
   * @param indexAccountId Index for the account ID
   * @param indexEvent Index for the event
   * @return The EventMessage object
   */
  public static EventMessage createEvent(
      Integer indexTimestamp,
      Integer indexStream,
      Integer indexUserId,
      Integer indexAccountId,
      Integer indexEvent) {
    return new EventMessage(
        getTimestamp(indexTimestamp),
        getStream(indexStream),
        getUserId(indexUserId),
        getAccountId(indexAccountId),
        getEvent(indexEvent));
  }

  /***
   * Generate a test event message based on an index per field, except for the timestamp which is
   * specified in the parameter.
   *
   * @param timestamp The timestamp value
   * @param indexStream Index for the stream
   * @param indexUserId Index for the user ID
   * @param indexAccountId Index for the account ID
   * @param indexEvent Index for the event
   * @return The EventMessage object
   */
  public static EventMessage createEventWithTimestamp(
      Long timestamp,
      Integer indexStream,
      Integer indexUserId,
      Integer indexAccountId,
      Integer indexEvent) {
    return new EventMessage(
        timestamp,
        getStream(indexStream),
        getUserId(indexUserId),
        getAccountId(indexAccountId),
        getEvent(indexEvent));
  }

  /***
   * Create a JSON object for an event. All the fields are set to a
   * base value with the index appended at the end
   *
   * @param index Index based on which to set the event fields
   * @return The JSON object
   */
  public static String createEventJson(Integer index) {
    return createEventJson(index, index, index, index, index);
  }

  /***
   * Create a JSON object for an event. All the fields are set to a
   * base value with the index appended at the end
   *
   * @param indexTimestamp The timestamp value
   * @param indexStream Index for the stream
   * @param indexUserId Index for the user ID
   * @param indexAccountId Index for the account ID
   * @param indexEvent Index for the event
   * @return The JSON object
   */
  public static String createEventJson(
      Integer indexTimestamp,
      Integer indexStream,
      Integer indexUserId,
      Integer indexAccountId,
      Integer indexEvent) {
    return String.format(
        "{" +
            "\"timestamp\": %d," +
            "\"stream\": \"%s\"," +
            "\"userId\": \"%s\"," +
            "\"accountId\": \"%s\"," +
            "\"event\": \"%s\"" +
            "}",
        getTimestamp(indexTimestamp),
        getStream(indexStream),
        getUserId(indexUserId),
        getAccountId(indexAccountId),
        getEvent(indexEvent));
  }

  /***
   * Create an account message. All the fields are set to a
   * base value with the index appended at the end
   *
   * @param index Index based on which to set the fields
   * @return The account message object
   */
  public static AccountMessage createAccount(Integer index) {
    return new AccountMessage(
        getTimestamp(index),
        getStream(index),
        getUserId(index),
        getUserName(index),
        getUserRole(index),
        getAccountId(index),
        getAccountName(index),
        getAccountStatus(index),
        getAccountPlan(index));
  }

  /***
   * Create an account message. All the fields are set to a
   * base value with the index appended at the end, except for the stream name which is
   * specified directly
   *
   * @param index Index based on which to set the fields
   * @param stream The stream name
   * @return The account message object
   */
  public static AccountMessage createAccountWithStream(Integer index, String stream) {
    return new AccountMessage(
        getTimestamp(index),
        stream,
        getUserId(index),
        getUserName(index),
        getUserRole(index),
        getAccountId(index),
        getAccountName(index),
        getAccountStatus(index),
        getAccountPlan(index));
  }


  /***
   * Create an account message. All the fields are set to a
   * base value with the index appended at the end, except for the account fields which are
   * specified directly
   *
   * @param index Index based on which to set the fields
   * @param accountName The account id
   * @param accountName The account name
   * @param accountStatus The account status
   * @param accountPlan The account plan
   * @return The account message object
   */
  public static AccountMessage createAccountWithAccount(
      Integer index,
      String accountId,
      String accountName,
      String accountStatus,
      String accountPlan) {
    return new AccountMessage(
        getTimestamp(index),
        getStream(index),
        getUserId(index),
        getUserName(index),
        getUserRole(index),
        accountId,
        accountName,
        accountStatus,
        accountPlan);
  }

  /***
   * Create an account JSON string. All the fields are set to a
   * base value with the index appended at the end
   *
   * @param index Index based on which to set the fields
   * @return The JSON string
   */
  public static String createAccountJson(Integer index) {
    return String.format(
        "{" +
            "\"timestamp\": %d," +
            "\"stream\": \"%s\"," +
            "\"userId\": \"%s\"," +
            "\"userName\": \"%s\"," +
            "\"userData\": {" +
            "  \"role\": \"%s\"" +
            "  }," +
            "\"accountId\": \"%s\"," +
            "\"accountName\": \"%s\"," +
            "\"accountData\": {" +
            "  \"status\": \"%s\"," +
            "  \"plan\": \"%s\"" +
            "  }" +
            "}",
        getTimestamp(index),
        getStream(index),
        getUserId(index),
        getUserName(index),
        getUserRole(index),
        getAccountId(index),
        getAccountName(index),
        getAccountStatus(index),
        getAccountPlan(index));
  }

  /***
   * @param index Index based on which to set the field
   * @return The stream name with index appended
   */
  public static String getStream(Integer index) {
    return streamBase + index.toString();
  }

  /***
   * @param index Index based on which to set the field
   * @return The timestamp that is the beginning of epoch plus the number of days specified by
   * the index - 1
   */
  public static Long getTimestamp(Integer index) {
    return timestampBase + (index - 1) * MILLISECONDS_PER_DAY;
  }

  /***
   * @param index Index based on which to set the field
   * @return The date that is the beginning of epoch plus the number of days indicated by
   * the index - 1
   */
  public static Date getDate(Integer index) {
    return new Date(getTimestamp(index) / MILLISECONDS_PER_DAY * MILLISECONDS_PER_DAY);
  }

  /***
   * @param index Index based on which to set the field
   * @return The user ID with index appended
   */
  public static String getUserId(Integer index) {
    return userIdBase + index.toString();
  }

  /***
   * @param index Index based on which to set the field
   * @return The user name with index appended
   */
  public static String getUserName(Integer index) {
    return userNameBase + index.toString();
  }

  /***
   * @param index Index based on which to set the field
   * @return The user role with index appended
   */
  public static String getUserRole(Integer index) {
    return userRoleBase + index.toString();
  }

  /***
   * @param index Index based on which to set the field
   * @return The account ID with index appended
   */
  public static String getAccountId(Integer index) {
    return accountIdBase + index.toString();
  }

  /***
   * @param index Index based on which to set the field
   * @return The account name with index appended
   */
  public static String getAccountName(Integer index) {
    return accountNameBase + index.toString();
  }

  /***
   * @param index Index based on which to set the field
   * @return The account status with index appended
   */
  public static String getAccountStatus(Integer index) {
    return accountStatusBase + index.toString();
  }

  /***
   * @param index Index based on which to set the field
   * @return The account plan with index appended
   */
  public static String getAccountPlan(Integer index) {
    return accountPlanBase + index.toString();
  }

  /***
   * @param index Index based on which to set the field
   * @return The event with index appended
   */
  public static String getEvent(Integer index) {
    return eventBase + index.toString();
  }
}
