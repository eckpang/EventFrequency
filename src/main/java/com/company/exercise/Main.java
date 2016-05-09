package com.company.exercise;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    if (args.length < 1) {
      PrintUsage();
      return;
    }

    Injector injector = Guice.createInjector(getModules());
    MessageProcessor messageProcessor = injector.getInstance(MessageProcessor.class);
    messageProcessor.processFile(args[0]);
  }

  /***
   * Prints the usage on the console
   */
  private static void PrintUsage() {
    System.out.println("Usage: java com.zuora.exercise.event-processing.Main <input_file>");
  }

  /***
   * @return A list of Guice module to inject
   */
  private static List<Module> getModules() {
    return ImmutableList.of(
        new MainModule()
    );
  }
}
