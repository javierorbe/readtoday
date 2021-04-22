package dev.team.readtoday.server.channel.application;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.team.readtoday.server.channel.application.edit.EditChannel;
import dev.team.readtoday.server.channel.domain.Channel;
import dev.team.readtoday.server.channel.domain.ChannelMother;
import dev.team.readtoday.server.channel.domain.ChannelNotFound;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class EditChannelTest {

  @Test
  public void shouldThrowsExceptionIfChannelDoesNotExists() {

    Channel channel = ChannelMother.random();
    ChannelRepository repository = mock(ChannelRepository.class);
    when(repository.getFromId(channel.getId())).thenReturn(Optional.empty());
    EditChannel editChannel = new EditChannel(repository);

    assertThrows(ChannelNotFound.class, () -> editChannel.edit(channel));
  }

  @Test
  public void shouldNotThrowsExceptionIfChannelExists() {
    Channel channel = ChannelMother.random();
    ChannelRepository repository = mock(ChannelRepository.class);
    when(repository.getFromId(channel.getId())).thenReturn(Optional.of(channel));
    EditChannel editChannel = new EditChannel(repository);

    assertDoesNotThrow(() -> editChannel.edit(channel));
  }
}
