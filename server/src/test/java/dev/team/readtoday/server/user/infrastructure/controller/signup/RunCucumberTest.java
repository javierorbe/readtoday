package dev.team.readtoday.server.user.infrastructure.controller.signup;

import dev.team.readtoday.server.shared.infrastructure.AcceptanceTest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@Category(AcceptanceTest.class)
@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", glue = "dev.team.readtoday.server.user.infrastructure.controller.signup")
public class RunCucumberTest {

}
