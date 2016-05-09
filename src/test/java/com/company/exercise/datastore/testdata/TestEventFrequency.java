package com.company.exercise.datastore.testdata;

import com.company.exercise.datastore.data.EventFrequency;
import com.company.exercise.datastore.data.FrequencyByDay;
import com.company.exercise.datastore.data.FrequencyByEvent;
import com.company.exercise.datastore.data.StreamFrequency;

import java.util.Date;

/***
 * A subclass of EventFrequency that ease the task of generating EventFrequency for testing
 */
public class TestEventFrequency extends EventFrequency {
  /***
   * Set the account frequency
   *
   * @return this object
   */
  public TestEventFrequency setAccountFrequency(
      String stream,
      String accountId,
      Date date,
      String event,
      Long count) {
    StreamFrequency streamFrequency = getMap()
        .computeIfAbsent(stream, key -> new StreamFrequency(key));
    FrequencyByDay frequencyByDay = streamFrequency.getFrequencyByAccount().getMap()
        .computeIfAbsent(accountId, key -> new FrequencyByDay());
    FrequencyByEvent frequencyByEvent = frequencyByDay.getMap()
        .computeIfAbsent(date, key -> new FrequencyByEvent());
    frequencyByEvent.getMap().put(event, count);
    return this;
  }

  /***
   * Set the user frequency
   *
   * @return this object
   */
  public TestEventFrequency setUserFrequency(
      String stream,
      String userId,
      Date date,
      String event,
      Long count) {
    StreamFrequency streamFrequency = getMap()
        .computeIfAbsent(stream, key -> new StreamFrequency(key));
    FrequencyByDay frequencyByDay = streamFrequency.getFrequencyByUser().getMap()
        .computeIfAbsent(userId, key -> new FrequencyByDay());
    FrequencyByEvent frequencyByEvent = frequencyByDay.getMap()
        .computeIfAbsent(date, key -> new FrequencyByEvent());
    frequencyByEvent.getMap().put(event, count);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;

    EventFrequency that = (EventFrequency) o;

    return super.equals(that);

  }
}
