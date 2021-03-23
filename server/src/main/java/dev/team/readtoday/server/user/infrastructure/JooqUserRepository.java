package dev.team.readtoday.server.user.infrastructure;

import static dev.team.readtoday.server.shared.infrastructure.jooq.Tables.USER;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import dev.team.readtoday.server.user.domain.EmailAddress;
import dev.team.readtoday.server.user.domain.Role;
import dev.team.readtoday.server.user.domain.User;
import dev.team.readtoday.server.user.domain.UserId;
import dev.team.readtoday.server.user.domain.UserRepository;
import dev.team.readtoday.server.user.domain.Username;
import java.util.Map;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Record3;

public final class JooqUserRepository implements UserRepository {

  private static final BiMap<Role, String> ROLE_MAP = HashBiMap.create(Map.of(
      Role.USER, "user",
      Role.ADMIN, "admin"
  ));

  private final DSLContext dsl;

  public JooqUserRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  @Override
  public void save(User user) {
    dsl.insertInto(USER, USER.ID, USER.USERNAME, USER.EMAIL, USER.ROLE_NAME)
        .values(
            user.getId().toString(),
            user.getUsername().toString(),
            user.getEmailAddress().toString(),
            ROLE_MAP.get(user.getRole())
        ).onDuplicateKeyUpdate()
        .set(USER.ROLE_NAME, ROLE_MAP.get(user.getRole()))
        .execute();
  }

  @Override
  public Optional<User> getByEmailAddress(EmailAddress email) {
    Record3<String, String, String> record =
        dsl.select(USER.ID, USER.USERNAME, USER.ROLE_NAME)
            .from(USER)
            .where(USER.EMAIL.eq(email.toString()))
            .fetchOne();

    if (record == null) {
      return Optional.empty();
    }

    UserId id = UserId.fromString(record.get(USER.ID));
    Username username = new Username(record.get(USER.USERNAME));
    Role role = ROLE_MAP.inverse().get(record.get(USER.ROLE_NAME));

    return Optional.of(new User(id, username, email, role));
  }
}
