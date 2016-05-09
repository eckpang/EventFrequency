package com.company.exercise.message;

/***
 * Generic message.
 *
 * This can be either a event or account identification message.
 */
public interface Message {
  long getTimestamp();

  String getStream();

  String getUserId();

  String getAccountId();
}
