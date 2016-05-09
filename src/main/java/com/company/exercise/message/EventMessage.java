package com.company.exercise.message;

/***
 * A POJO that is mapped to an event JSON message
 */
public class EventMessage implements Message {
  // Timestamp in milliseconds
  private long timestamp;

  // The customer identifier
  private String stream;

  private String userId;

  private String accountId;

  private String event;

  public EventMessage() {
  }

  public EventMessage(long timestamp,
                      String stream,
                      String userId,
                      String accountId,
                      String event) {
    this.timestamp = timestamp;
    this.stream = stream;
    this.userId = userId;
    this.accountId = accountId;
    this.event = event;
  }

  @Override
  public long getTimestamp() {
    return timestamp;
  }

  @Override
  public String getStream() {
    return stream;
  }

  @Override
  public String getUserId() {
    return userId;
  }

  @Override
  public String getAccountId() {
    return accountId;
  }

  public String getEvent() {
    return event;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    EventMessage that = (EventMessage) o;

    if (timestamp != that.timestamp) return false;
    if (!stream.equals(that.stream)) return false;
    if (!userId.equals(that.userId)) return false;
    if (!accountId.equals(that.accountId)) return false;
    return event.equals(that.event);

  }

  @Override
  public int hashCode() {
    int result = (int) (timestamp ^ (timestamp >>> 32));
    result = 31 * result + stream.hashCode();
    result = 31 * result + userId.hashCode();
    result = 31 * result + accountId.hashCode();
    result = 31 * result + event.hashCode();
    return result;
  }
}
