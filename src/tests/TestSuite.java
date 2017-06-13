package tests;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ BusinessFiguresTest.class, ParserTests.class, RouteFinderTest.class, UserDatabaseTest.class} )
public final class TestSuite {} // or ModuleFooSuite, and that in AllTests