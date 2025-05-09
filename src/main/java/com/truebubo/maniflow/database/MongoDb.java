package com.truebubo.maniflow.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Properties;

/// Accessors for MongoDB
public class MongoDb {
    private static MongoClient instance;

    /// Lazily gets instance
    /// @return Reused or new mongo client
    public static @NonNull MongoClient getInstance() {
        if (instance != null) return instance;

        final Properties properties = new Properties();
        final var dbSettingsFile = "application.properties";

        try (var resourceStream = MongoDb.class.getClassLoader().getResourceAsStream(dbSettingsFile)) {
            properties.load(resourceStream);
            final var username = properties.getProperty("spring.data.mongodb.username");
            final var password = properties.getProperty("spring.data.mongodb.password");
            final var port = properties.getProperty("spring.data.mongodb.port");
            final var authDb = properties.getProperty("spring.data.mongodb.authentication-database");
            instance = MongoClients.create(
                    MongoClientSettings.builder().applyConnectionString(new ConnectionString(String.format("mongodb://%s:%s@127.0.0.1:%s/%s?connectTimeoutMS=2000", username, password, port, authDb)))
                            .build()
            );
            return instance;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
