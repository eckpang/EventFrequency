package com.company.exercise.output;

/***
 * An implementation of Output that displays result in the console.
 */
public class ConsoleOutput implements Output {
  @Override
  public void outputError(String error) {
    System.out.println(error);
  }

  @Override
  public void outputEventFrequencyJson(String json) {
    System.out.println(json);
  }
}
