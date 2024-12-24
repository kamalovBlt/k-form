package ru.itis.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private static volatile DatabaseConfig instance;

    private static HikariDataSource dataSource;

    private DatabaseConfig() {
        try {

            Class.forName("org.postgresql.Driver");

            Properties properties = new Properties();
            properties.load(Files.newInputStream(Paths.get("C:\\Users\\poloy\\Desktop\\ITIS\\Oris\\k-form\\src\\main\\resources\\properties\\db.properties")));

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.username"));
            config.setPassword(properties.getProperty("db.password"));
            config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.maximumPoolSize")));

            dataSource = new HikariDataSource(config);

            Flyway flyway = Flyway.configure().locations("classpath:db/migration").dataSource(dataSource).load();
            flyway.migrate();

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            synchronized (DatabaseConfig.class) {
                if (instance == null) {
                    instance = new DatabaseConfig();
                }
            }
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        dataSource.close();
    }

}
