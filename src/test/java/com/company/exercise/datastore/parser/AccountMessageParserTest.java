package com.company.exercise.datastore.parser;

import com.company.exercise.MainModule;
import com.company.exercise.datastore.testdata.TestMessageFactory;
import com.company.exercise.message.Message;
import com.company.exercise.parser.AccountMessageParser;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

public class AccountMessageParserTest {
  private AccountMessageParser parser;

  @BeforeTest
  public void Setup() {
    Injector injector = Guice.createInjector(new MainModule());
    parser = injector.getInstance(AccountMessageParser.class);
  }

  /***
   * Provide test data for each test cases
   */
  @DataProvider(name = "accountMessageParserTestDataProvider")
  public Object[][] accountMessageParserTestDataProvider() {
    return new Object[][] {
        new Object[]{
            // empty line
            "",
            Optional.empty(),
        },
        new Object[]{
            // valid account identifier message
            TestMessageFactory.createAccountJson(1),
            Optional.of(TestMessageFactory.createAccount(1)),
        },
        new Object[]{
            // invalid account identifier message
            "bogus_text" + TestMessageFactory.createAccountJson(1),
            Optional.empty(),
        },
    };
  }

  /***
   * Test the AccountMessageParser with the test cases provided by the data provider
   *
   * @param line The JSON line
   * @param expectedMessage The expected message created
   */
  @Test(dataProvider = "accountMessageParserTestDataProvider")
  public void accountMessageParserTest(
      String line,
      Optional<Message> expectedMessage) throws Exception {
    Optional<Message> message = parser.parse(line);
    Assert.assertEquals(message, expectedMessage);
  }
}
