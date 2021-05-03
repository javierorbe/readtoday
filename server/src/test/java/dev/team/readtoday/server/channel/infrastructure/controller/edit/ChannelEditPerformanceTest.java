package dev.team.readtoday.server.channel.infrastructure.controller.edit;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CATEGORY;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CHANNEL_CATEGORIES;

import dev.team.readtoday.server.channel.application.edit.EditChannelCommandMother;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.shared.domain.ChannelId;
import dev.team.readtoday.server.shared.domain.bus.command.Command;
import dev.team.readtoday.server.shared.domain.bus.command.CommandBus;
import dev.team.readtoday.server.shared.infrastructure.PerformanceTest;
import dev.team.readtoday.server.shared.infrastructure.controller.AcceptanceTestAppContext;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseAcceptanceTest;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@Category(PerformanceTest.class)
public class ChannelEditPerformanceTest extends BaseAcceptanceTest {

  private static AcceptanceTestAppContext context;
  private static CommandBus commandBus;
  private static ChannelRepository channelRepository;

  private static Channel channel;

  @Rule
  public ContiPerfRule rule = new ContiPerfRule();

  @BeforeClass
  public static void setup() {
    context = new AcceptanceTestAppContext();
    clearRepositories();

    channelRepository = context.getBean(ChannelRepository.class);
    commandBus = context.getBean(CommandBus.class);
    createChannelToEdit();
  }

  private static void createChannelToEdit() {
    ChannelId id = ChannelId.random();
    channel = ChannelMother.withId(id);
    channelRepository.save(channel);
  }

  private static void clearRepositories() {
    context.clearTables(CHANNEL_CATEGORIES, CATEGORY, CHANNEL);
  }

  @Test
  @PerfTest(invocations = 100)
  public void dispatchEditCommand() {
    Command command = EditChannelCommandMother.withId(channel.getId().toString());
    commandBus.dispatch(command);
  }

  @AfterClass
  public static void close() {
    clearRepositories();
    context.close();
  }
}
