package com.company.exercise;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.company.exercise.datastore.DataStore;
import com.company.exercise.datastore.InMemoryDataStore;
import com.company.exercise.formatter.Formatter;
import com.company.exercise.formatter.JsonFormatter;
import com.company.exercise.output.ConsoleOutput;
import com.company.exercise.output.Output;
import com.company.exercise.parser.AccountMessageParser;
import com.company.exercise.parser.EventMessageParser;
import com.company.exercise.parser.MessageParser;

/***
 * Guice binding configuration
 */
public class MainModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(DataStore.class)
        .to(InMemoryDataStore.class)
        .in(Scopes.SINGLETON);

    bind(Formatter.class)
        .to(JsonFormatter.class)
        .in(Scopes.SINGLETON);

    bind(Output.class)
        .to(ConsoleOutput.class)
        .in(Scopes.SINGLETON);

    bindMessageParsers();
  }

  /***
   * Register message parsers. Extend to add new message types
   */
  private void bindMessageParsers() {
    binder().bind(MessageProcessor.class).in(Scopes.SINGLETON);

    Multibinder<MessageParser> multibinder
        = Multibinder.newSetBinder(binder(), TypeLiteral.get(MessageParser.class));

    multibinder.addBinding().to(EventMessageParser.class);
    multibinder.addBinding().to(AccountMessageParser.class);
  }
}
