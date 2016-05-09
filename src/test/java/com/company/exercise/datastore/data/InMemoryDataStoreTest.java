package com.company.exercise.datastore.data;

import com.company.exercise.datastore.testdata.TestMessageFactory;
import com.company.exercise.message.Message;
import com.google.common.collect.ImmutableList;
import com.company.exercise.datastore.InMemoryDataStore;
import com.company.exercise.datastore.testdata.TestEventFrequency;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/***
 * Test the InMemoryDataStore class
 */
public class InMemoryDataStoreTest {
  private InMemoryDataStore inMemoryDataStore;

  @BeforeMethod
  public void beforeMethod() {
    inMemoryDataStore = new InMemoryDataStore();
  }

  /***
   * Provide test data for each test cases
   */
  @DataProvider(name = "inMemoryDataStoreTestDataProvider")
  public Object[][] inMemoryDataStoreTestDataProvider() {
    // The first part of the tuple is a list of messages to ingest
    // The second part of the tuple is the expected EventFrequency object
    return new Object[][] {
        new Object[] {
            // two events on different streams.
            // Expect 2 counts of 1 in each stream for account and user
            ImmutableList.of(
                TestMessageFactory.createAccount(1),
                TestMessageFactory.createAccount(2),
                TestMessageFactory.createEvent(1, 1, 1, 1, 1),
                TestMessageFactory.createEvent(2, 2, 2, 2, 2)),
            new TestEventFrequency()
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    1L)
                .setUserFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getUserId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    1L)
                .setAccountFrequency(
                    TestMessageFactory.getStream(2),
                    TestMessageFactory.getAccountId(2),
                    TestMessageFactory.getDate(2),
                    TestMessageFactory.getEvent(2),
                    1L)
                .setUserFrequency(
                    TestMessageFactory.getStream(2),
                    TestMessageFactory.getUserId(2),
                    TestMessageFactory.getDate(2),
                    TestMessageFactory.getEvent(2),
                    1L)},
        new Object[] {
            // two events sharing the same stream.
            // Expect 2 counts of 1 in each stream for account and user
            ImmutableList.of(
                TestMessageFactory.createAccount(1),
                TestMessageFactory.createAccount(2),
                TestMessageFactory.createEvent(1, 1, 1, 1, 1),
                TestMessageFactory.createEvent(2, 1, 2, 2, 2)),
            new TestEventFrequency()
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    1L)
                .setUserFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getUserId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    1L)
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(2),
                    TestMessageFactory.getDate(2),
                    TestMessageFactory.getEvent(2),
                    1L)
                .setUserFrequency(
                TestMessageFactory.getStream(1),
                TestMessageFactory.getUserId(2),
                TestMessageFactory.getDate(2),
                TestMessageFactory.getEvent(2),
                1L)},
        new Object[] {
            // two events sharing the same stream, user id, account id, and timestamp (event name
            // still differs).
            // Expect 2 counts of 1 in each stream for account and user
            ImmutableList.of(
                TestMessageFactory.createAccount(1),
                TestMessageFactory.createAccount(2),
                TestMessageFactory.createEvent(1, 1, 1, 1, 1),
                TestMessageFactory.createEvent(1, 1, 1, 1, 2)),
            new TestEventFrequency()
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    1L)
                .setUserFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getUserId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    1L)
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(2),
                    1L)
                .setUserFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getUserId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(2),
                    1L)},
        new Object[] {
            // two events sharing the same user and account.
            // Expect counts of 2 in each stream for account and user
            ImmutableList.of(
                TestMessageFactory.createAccount(1),
                TestMessageFactory.createAccount(2),
                TestMessageFactory.createEvent(1, 1, 1, 1, 1),
                TestMessageFactory.createEvent(1, 1, 1, 1, 1)),
            new TestEventFrequency()
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    2L)
                .setUserFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getUserId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    2L)},
        new Object[] {
            // Test time boundaries with multiple events
            ImmutableList.of(
                TestMessageFactory.createAccount(1),
                TestMessageFactory.createAccount(2),
                TestMessageFactory.createAccount(3),
                TestMessageFactory.createEventWithTimestamp(TestMessageFactory.MILLISECONDS_PER_DAY - 2L, 1, 1, 1, 1),
                TestMessageFactory.createEventWithTimestamp(TestMessageFactory.MILLISECONDS_PER_DAY - 1L, 1, 1, 1, 1),
                TestMessageFactory.createEventWithTimestamp(TestMessageFactory.MILLISECONDS_PER_DAY, 1, 1, 1, 1),
                TestMessageFactory.createEventWithTimestamp(TestMessageFactory.MILLISECONDS_PER_DAY + 1L, 1, 1, 1, 1),
                TestMessageFactory.createEventWithTimestamp(TestMessageFactory.MILLISECONDS_PER_DAY + 2L, 1, 1, 1, 1)),
            new TestEventFrequency()
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    2L)
                .setUserFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getUserId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    2L)
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(1),
                    TestMessageFactory.getDate(2),
                    TestMessageFactory.getEvent(1),
                    3L)
                .setUserFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getUserId(1),
                    TestMessageFactory.getDate(2),
                    TestMessageFactory.getEvent(1),
                    3L)},
    };
  }

  /***
   * Test the EventMessageParser with the test cases provided by the data provider
   * @param messages The messages to ingest
   * @param eventFrequency The expected event frequency
   */
  @Test(dataProvider = "inMemoryDataStoreTestDataProvider")
  public void inMemoryDataStoreTest(
      List<Message> messages,
      EventFrequency eventFrequency) throws Exception {
    for (Message message : messages) {
      inMemoryDataStore.storeMessage(message);
    }
    Assert.assertEquals(inMemoryDataStore.getEventFrequency(), eventFrequency);
  }

  @DataProvider(name = "sameUserInMultipleAccountTestDataProvider")
  public Object[][] sameUserInMultipleAccountTestDataProvider() {
    return new Object[][] {
        new Object[] {
            // One user belong to two seperate accounts in the same stream. An exception
            // should be thorwn
            ImmutableList.of(
                TestMessageFactory.createAccount(1),
                TestMessageFactory.createAccountWithAccount(
                    1,
                    TestMessageFactory.getAccountId(2),
                    TestMessageFactory.getAccountName(2),
                    TestMessageFactory.getAccountPlan(2),
                    TestMessageFactory.getAccountStatus(2)
                    )),
            true
        },
        new Object[] {
            // One user belong to the same account ID, but in different stream. This is valid and
            // no exceptions should be thrown
            ImmutableList.of(
                TestMessageFactory.createAccount(1),
                TestMessageFactory.createAccountWithStream(
                    1,
                    "stream2"
                )),
            false
        },
        new Object[] {
            // The same user is set to the same account twice. This is not an error and no
            // exceptions should be thrown
            ImmutableList.of(
                TestMessageFactory.createAccount(1),
                TestMessageFactory.createAccount(1)),
            false
        }
    };
  }

  @Test(dataProvider = "sameUserInMultipleAccountTestDataProvider")
  public void sameUserInMultipleAccountTest(
      List<Message> messages,
      boolean exceptionExpected) {
    boolean exception = false;
    try {
      for (Message message : messages) {
        inMemoryDataStore.storeMessage(message);
      }
    } catch (IllegalStateException ex) {
      exception = true;
    }
    Assert.assertEquals(exception, exceptionExpected);
  }

}
