package dev.team.readtoday.server.shared.infrastructure.persistence;

import com.zaxxer.hikari.HikariConfig;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.jooq.Table;
import org.jooq.impl.DSL;

public abstract class BaseJooqIntegrationTest {

  private static List<Table<?>> tablesToClear;
  private static JooqConnectionBuilder jooq;

  /**
   * Start the database connection and clear the indicated tables.
   *
   * @param tablesToClearArr tables to clear before and after all the tests are completed
   */
  protected static void start(Table<?>... tablesToClearArr) {
    tablesToClear = List.of(tablesToClearArr);
    jooq = new JooqConnectionBuilder(new HikariConfig("/datasource-test.properties"));
    clearTables(tablesToClear);
  }

  /** Clear the specified tables and shutdown the database connection. */
  protected static void clearAndShutdown() {
    clearTables(tablesToClear);
    jooq.close();
  }

  /**
   * Instantiate and return the specified repository.
   *
   * @param clazz the class of the repository to instantiate
   * @param <T> the type of the repository to get an instance of
   * @return an instance of the specified repository
   */
  @SuppressWarnings("unchecked")
  protected static <T> T getRepository(Class<T> clazz) {
    try {
      return (T) clazz.getConstructors()[0].newInstance(jooq.getContext());
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new IllegalArgumentException("Invalid repository class.", e);
    }
  }

  private static void clearTables(Iterable<? extends Table<?>> tables) {
    for (Table<?> table : tables) {
      jooq.getContext().transaction(config -> DSL.using(config).deleteFrom(table).execute());
    }
  }
}
