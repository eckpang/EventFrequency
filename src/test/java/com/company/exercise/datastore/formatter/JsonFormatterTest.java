package com.company.exercise.datastore.formatter;

import com.company.exercise.MainModule;
import com.company.exercise.datastore.InMemoryDataStore;
import com.company.exercise.datastore.data.EventFrequency;
import com.company.exercise.datastore.testdata.TestMessageFactory;
import com.company.exercise.formatter.JsonFormatter;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.company.exercise.datastore.testdata.TestEventFrequency;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class JsonFormatterTest {
  private JsonFormatter formatter;
  private InMemoryDataStore inMemoryDataStore;

  @BeforeTest
  public void Setup() {
    Injector injector = Guice.createInjector(new MainModule());
    formatter = injector.getInstance(JsonFormatter.class);
    inMemoryDataStore = new InMemoryDataStore();
    inMemoryDataStore.storeMessage(TestMessageFactory.createAccount(1));
    inMemoryDataStore.storeMessage(TestMessageFactory.createAccount(2));
    inMemoryDataStore.storeMessage(TestMessageFactory.createAccountWithStream(1, "stream2"));
    inMemoryDataStore.storeMessage(TestMessageFactory.createAccountWithStream(2, "stream1"));
    inMemoryDataStore.storeMessage(TestMessageFactory.createAccountWithUser(
        1,
        "userId2",
        "userName2",
        "userRole2"));
    inMemoryDataStore.storeMessage(TestMessageFactory.createAccountWithUser(
        2,
        "userId1",
        "userName1",
        "userRole1"));
    inMemoryDataStore.storeMessage(TestMessageFactory.createAccountWithAccount(
        1,
        "accountId2",
        "accountName2",
        "accountStatus2",
        "accountPlan2"));
    inMemoryDataStore.storeMessage(TestMessageFactory.createAccountWithAccount(
        2,
        "accountId1",
        "accountName1",
        "accountStatus1",
        "accountPlan1"));
  }

  /***
   * Provide test data for fileReaderTest
   */
  @DataProvider(name = "jsonFormatterTestDataProvider")
  public Object[][] jsonFormatterTestDataProvider() {
    return new Object[][] {
        new Object[]{
            // simple test
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
                    1L),
            "[{\n" +
                "  \"accounts\": [\n" +
                "    \"accountStatus1\": [\n" +
                "      \"1970-01-01\": {\n" +
                "        \"event1\": 1\n" +
                "      }\n" +
                "    ]\n" +
                "  ],\n" +
                "  \"stream\": \"stream1\",\n" +
                "  \"users\": [\n" +
                "    \"userRole1\": [\n" +
                "      \"1970-01-01\": {\n" +
                "        \"event1\": 1\n" +
                "      }\n" +
                "    ]\n" +
                "  ]\n" +
                "}]"
        },
        new Object[]{
            // multiple streams
            new TestEventFrequency()
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    1L)
                .setUserFrequency(
                  TestMessageFactory.getStream(2),
                  TestMessageFactory.getUserId(2),
                  TestMessageFactory.getDate(2),
                  TestMessageFactory.getEvent(2),
                  1L),
            "[{\n" +
                "  \"accounts\": [\n" +
                "    \"accountStatus1\": [\n" +
                "      \"1970-01-01\": {\n" +
                "        \"event1\": 1\n" +
                "      }\n" +
                "    ]\n" +
                "  ],\n" +
                "  \"stream\": \"stream1\",\n" +
                "  \"users\": [\n" +
                "  ]\n" +
                "},\n" +
                "{\n" +
                "  \"accounts\": [\n" +
                "  ],\n" +
                "  \"stream\": \"stream2\",\n" +
                "  \"users\": [\n" +
                "    \"userRole2\": [\n" +
                "      \"1970-01-02\": {\n" +
                "        \"event2\": 1\n" +
                "      }\n" +
                "    ]\n" +
                "  ]\n" +
                "}]"
        },
        new Object[]{
            // multiple dates
            new TestEventFrequency()
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    2L)
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(1),
                    TestMessageFactory.getDate(2),
                    TestMessageFactory.getEvent(1),
                    2L)
                .setUserFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getUserId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    2L)
                .setUserFrequency(
                  TestMessageFactory.getStream(1),
                  TestMessageFactory.getUserId(1),
                  TestMessageFactory.getDate(2),
                  TestMessageFactory.getEvent(1),
                2L),
            "[{\n" +
                "  \"accounts\": [\n" +
                "    \"accountStatus1\": [\n" +
                "      \"1970-01-01\": {\n" +
                "        \"event1\": 2\n" +
                "      },\n" +
                "      \"1970-01-02\": {\n" +
                "        \"event1\": 2\n" +
                "      }\n" +
                "    ]\n" +
                "  ],\n" +
                "  \"stream\": \"stream1\",\n" +
                "  \"users\": [\n" +
                "    \"userRole1\": [\n" +
                "      \"1970-01-01\": {\n" +
                "        \"event1\": 2\n" +
                "      },\n" +
                "      \"1970-01-02\": {\n" +
                "        \"event1\": 2\n" +
                "      }\n" +
                "    ]\n" +
                "  ]\n" +
                "}]"
        },
        new Object[]{
            // multiple events
            new TestEventFrequency()
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    2L)
                .setAccountFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getAccountId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(2),
                    2L)
                .setUserFrequency(
                    TestMessageFactory.getStream(1),
                    TestMessageFactory.getUserId(1),
                    TestMessageFactory.getDate(1),
                    TestMessageFactory.getEvent(1),
                    2L)
                .setUserFrequency(
                TestMessageFactory.getStream(1),
                TestMessageFactory.getUserId(1),
                TestMessageFactory.getDate(1),
                TestMessageFactory.getEvent(2),
                2L),
            "[{\n" +
                "  \"accounts\": [\n" +
                "    \"accountStatus1\": [\n" +
                "      \"1970-01-01\": {\n" +
                "        \"event1\": 2,\n" +
                "        \"event2\": 2\n" +
                "      }\n" +
                "    ]\n" +
                "  ],\n" +
                "  \"stream\": \"stream1\",\n" +
                "  \"users\": [\n" +
                "    \"userRole1\": [\n" +
                "      \"1970-01-01\": {\n" +
                "        \"event1\": 2,\n" +
                "        \"event2\": 2\n" +
                "      }\n" +
                "    ]\n" +
                "  ]\n" +
                "}]"
        },
    };
  }

  /***
   * Test the JsonFormatter with the test cases provided by the data provider
   *
   * @param eventFrequency The event frequency object to translate to JSON
   * @param expectedJson The expected JSON string
   */
  @Test(dataProvider = "jsonFormatterTestDataProvider")
  public void jsonFormatterTest(
      EventFrequency eventFrequency,
      String expectedJson) throws Exception {
    String json = formatter.format(
        eventFrequency,
        inMemoryDataStore.getAccounts(),
        inMemoryDataStore.getUsers());
    Assert.assertEquals(json, expectedJson);
  }
}
