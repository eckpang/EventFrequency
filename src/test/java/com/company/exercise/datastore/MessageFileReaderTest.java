package com.company.exercise.datastore;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.company.exercise.MainModule;
import com.company.exercise.MessageFileReader;
import com.company.exercise.datastore.testdata.TestMessageFactory;
import com.company.exercise.message.Message;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;

/***
 * Test the MessageFileReader class
 */
public class MessageFileReaderTest {
  private MessageFileReader messageFileReader;

  @BeforeTest
  public void Setup() {
    Injector injector = Guice.createInjector(new MainModule());
    messageFileReader = injector.getInstance(MessageFileReader.class);
  }

  /***
   * Provide test data for fileReaderTest
   */
  @DataProvider(name = "fileReaderTestDataProvider")
  public Object[][] fileReaderTestDataProvider() {
    // The first part of the tuple specifies the message JSON
    // The second part of the tuple specifies the expected list of messages read
    return new Object[][] {
        new Object[] {
            // empty file
            "",
            ImmutableList.of(),
            false
        },
        new Object[] {
            // file with 1 event message
            TestMessageFactory.createEventJson(1),
            ImmutableList.of(TestMessageFactory.createEvent(1)),
            false
        },
        new Object[] {
            // file with 1 account identifier message
            TestMessageFactory.createAccountJson(1),
            ImmutableList.of(TestMessageFactory.createAccount(1)),
            false
        },
        new Object[] {
            // file with multiple event messages and account identifier messages
            TestMessageFactory.createEventJson(1) + "\n" +
                TestMessageFactory.createEventJson(2) + "\n" +
                TestMessageFactory.createAccountJson(1) + "\n" +
                TestMessageFactory.createAccountJson(2) + "\n",
            ImmutableList.of(
                TestMessageFactory.createEvent(1),
                TestMessageFactory.createEvent(2),
                TestMessageFactory.createAccount(1),
                TestMessageFactory.createAccount(2)),
            false
        },
        new Object[] {
            // file with 1 account identifier message
            "bogus_text" + TestMessageFactory.createAccountJson(1),
            ImmutableList.of(),
            true
        },
    };
  }

  /***
   * Test the MessageFileReader with the test cases provided by the data provider
   * @param messageJson The message JSON
   * @param expectedMessages The expected message created
   * @param expectedException Whether exception is expected to be thrown
   */
  @Test(dataProvider = "fileReaderTestDataProvider")
  public void fileReaderTest(
      String messageJson,
      List<Message> expectedMessages,
      boolean expectedException) {
    boolean exception = false;
    List<Message> messages = new LinkedList<>();
    try {
      messageFileReader.open(new ByteArrayInputStream(messageJson.getBytes()));

      Message message;
      while ((message = messageFileReader.getNextMessage()) != null) {
        messages.add(message);
      }
    } catch (Exception ex) {
      exception = true;
    }
    Assert.assertEquals(messages, expectedMessages);
    Assert.assertEquals(exception, expectedException);
  }
}
