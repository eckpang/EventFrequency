package com.company.exercise.formatter;

import com.company.exercise.datastore.data.*;

import java.util.Map;

/***
 * Formats event frequency data into a string
 */
public interface Formatter {
  String format(EventFrequency eventFrequency,
                Map<AccountKey, Account> accounts,
                Map<UserKey, User> users);
}
