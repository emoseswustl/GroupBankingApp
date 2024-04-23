package test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ BankAccountTests.class, FileStorageTests.class, PersonalCapitalTests.class,
		UserTests.class, MortgageTests.class, MenuTests.class })
public class AllTests {

}
