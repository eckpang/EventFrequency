package com.company.exercise.output;

/***
 * Output object that displays program output
 */
public interface Output {
  /***
   * Print an error to the console
   * @param error The error message
   */
  void outputError(String error);

  /***
   * Print the event frequency JSON to the console
   * @param json The JSON string
   */
  void outputEventFrequencyJson(String json);
}
