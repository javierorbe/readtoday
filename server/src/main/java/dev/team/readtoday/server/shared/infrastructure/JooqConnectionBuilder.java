package dev.team.readtoday.server.shared.infrastructure;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public final class JooqConnectionBuilder implements AutoCloseable {

  private final HikariDataSource dataSource;
  private final DSLContext ctx;

  public JooqConnectionBuilder(HikariConfig config) {
    dataSource = new HikariDataSource(config);
    ctx = DSL.using(dataSource, SQLDialect.MYSQL);
  }

  public DSLContext getContext() {
    return ctx;
  }

  @Override
  public void close() {
    dataSource.close();
  }
}
