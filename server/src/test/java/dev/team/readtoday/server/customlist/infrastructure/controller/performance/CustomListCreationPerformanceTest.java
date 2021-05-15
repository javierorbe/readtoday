package dev.team.readtoday.server.customlist.infrastructure.controller.performance;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.CUSTOM_LIST;
import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import dev.team.readtoday.server.customlist.application.CreateCustomListCommand;
import dev.team.readtoday.server.shared.domain.UserId;
import dev.team.readtoday.server.shared.domain.bus.command.Command;
import dev.team.readtoday.server.shared.domain.bus.command.CommandBus;
import dev.team.readtoday.server.shared.infrastructure.PerformanceTest;
import dev.team.readtoday.server.shared.infrastructure.controller.AcceptanceTestAppContext;
import dev.team.readtoday.server.shared.infrastructure.controller.BaseAcceptanceTest;
import dev.team.readtoday.server.user.domain.EmailAddressMother;
import dev.team.readtoday.server.user.domain.Role;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.domain.UsernameMother;

@RunWith(JUnit4.class)
@Category(PerformanceTest.class)
public class CustomListCreationPerformanceTest extends BaseAcceptanceTest {

  @Rule
  public ContiPerfRule rule = new ContiPerfRule();

  private AcceptanceTestAppContext context;
  private CommandBus commandBus;

  @Test
  @PerfTest(invocations = 50)
  public void customListCreatinPerfTest() {
    context = new AcceptanceTestAppContext();
    context.clearTables(CUSTOM_LIST, USER);
    commandBus = context.getBean(CommandBus.class);

    UserRepository userRepository = context.getBean(UserRepository.class);
    UserId userId = UserId.random();
    User user = new User(userId, UsernameMother.random(), EmailAddressMother.random(), Role.USER);

    userRepository.save(user);
    Command command = new CreateCustomListCommand("Custom list", userId.toString());
    commandBus.dispatch(command);

    context.clearTables(CUSTOM_LIST, USER);
    context.close();
  }
}
