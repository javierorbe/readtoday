package dev.team.readtoday.server.customlist.application;

import static org.mockito.Mockito.mock;

import dev.team.readtoday.server.customlist.domain.CustomListRepository;
import dev.team.readtoday.server.customlist.infrastructure.persistence.JooqCustomListRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class CreateCustomListCommandHandlerTest {

  @Test
  void shouldHandleCorrectly() {
    CustomListRepository repository = mock(JooqCustomListRepository.class);
    CreateCustomList createCustomList = new CreateCustomList(repository);
    CreateCustomListCommandHandler handler = new CreateCustomListCommandHandler(createCustomList);
    CreateCustomListCommand command = CreateCustomListCommandMother.random();
    handler.handle(command);
  }

}
