package com.bdd.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber-reports/Cucumber.json"}, features = {"src/main/test/resources/Features"}, glue = "com.bdd.definitions")
public class CucumberRunnerTest {
}
