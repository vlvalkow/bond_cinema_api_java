package dev.vladimirvalkov.api.bondcinema.app;

import org.flywaydb.core.Flyway;

public class DatabaseMigrator {
    public static void migrate(DatabaseConfig config) {
        if (config == null || config.url() == null) {
            return; // No DB configured
        }

        Flyway flyway = Flyway.configure()
                .locations("classpath:db/migration")
                .dataSource(config.url(), config.user(), config.pass())
                .load();

        flyway.migrate();
    }
}
