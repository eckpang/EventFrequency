package com.company.exercise.datastore.parser;

import com.company.exercise.MainModule;
import com.company.exercise.datastore.testdata.TestMessageFactory;
import com.company.exercise.message.Message;
import com.company.exercise.parser.EventMessageParser;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

public class EventMessageParserTest {
  private EventMessageParser parser;

  @BeforeTest
  public void Setup() {
    Injector injector = Guice.createInjector(new MainModule());
    parser = injector.getInstance(EventMessageParser.class);
  }

  /***
   * Provide test data for fileReaderTest
   */
  @DataProvider(name = "eventMessageParserTestDataProvider")
  public Object[][] eventMessageParserTestDataProvider() {
    return new Object[][] {
        new Object[]{
            // empty line
            "",
            Optional.empty(),
        },
        new Object[]{
            // valid event message
            TestMessageFactory.createEventJson(1),
            Optional.of(TestMessageFactory.createEvent(1)),
        },
        new Object[]{
            // invalid event message
            "bogus_text" + TestMessageFactory.createEventJson(1),
            Optional.empty(),
        },
    };
  }

  /***
   * Test the EventMessageParser with the test cases provided by the data provider
   *
   * @param line The JSON line
   * @param expectedMessage The expected message created
   */
  @Test(dataProvider = "eventMessageParserTestDataProvider")
  public void eventMessageParserTest(
      String line,
      Optional<Message> expectedMessage) throws Exception {
    Optional<Message> message = parser.parse(line);
    Assert.assertEquals(message, expectedMessage);
  }
}
