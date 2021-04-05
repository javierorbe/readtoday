package dev.team.readtoday.server.channel.infrastructure.controller.create;

import dev.team.readtoday.server.channel.application.CreateChannel;
import dev.team.readtoday.server.channel.domain.ChannelRepository;
import dev.team.readtoday.server.shared.infrastructure.controller.JwtTokenManager;
import dev.team.readtoday.server.user.application.SearchUserById;
import dev.team.readtoday.server.user.domain.UserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

final class CreateChannelTestingJerseyConfig extends ResourceConfig {

  private static final String READTODAY_SERVER_PACKAGE = "dev.team.readtoday.server";

  CreateChannelTestingJerseyConfig(JwtTokenManager jwtTokenManager,
                                   UserRepository userRepository,
                                   ChannelRepository channelRepository) {

    packages(READTODAY_SERVER_PACKAGE);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(jwtTokenManager).to(JwtTokenManager.class);

        bind(new SearchUserById(userRepository)).to(SearchUserById.class);
        bind(new CreateChannel(channelRepository)).to(CreateChannel.class);
      }
    });
  }
}
