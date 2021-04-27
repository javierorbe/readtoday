package dev.team.readtoday.server.channel.infrastructure.controller.search;

import dev.team.readtoday.server.shared.infrastructure.AcceptanceTest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Category(AcceptanceTest.class)
@CucumberOptions(plugin = "pretty", glue = "dev.team.readtoday.server.channel.infrastructure.controller.search")
public class RunCucumberTest {

}
