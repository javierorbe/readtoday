package dev.team.readtoday.server.subscription.infrastructure.controller.post;

import dev.team.readtoday.server.shared.infrastructure.AcceptanceTest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", glue = "dev.team.readtoday.server.subscription.infrastructure.controller.post")
@Category(AcceptanceTest.class)
public class RunCucumberTest {

}
