package ru.drip;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = {"classpath:."},
        format = {"html:target/site/cucumber-pretty", "json:target/acceptance.json"},
        tags = { "~@ignore" }
)
public class AcceptanceRunnerTest {
}
