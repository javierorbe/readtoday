package dev.team.readtoday.server.publication.infrastructure.controller.channel;

import dev.team.readtoday.server.shared.interfaces.AcceptanceTest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Category(AcceptanceTest.class)
@CucumberOptions(plugin = "pretty", glue = "dev.team.readtoday.server.publication.infrastructure.controller.channel")
public class RunCucumberTest {

}
