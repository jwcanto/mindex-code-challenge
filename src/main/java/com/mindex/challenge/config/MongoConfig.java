package com.mindex.challenge.config;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.NonNull;

import java.net.InetSocketAddress;

/**
 * Configures the in-memory MongoDB server and Spring Data repository scanning.
 */
@EnableMongoRepositories(basePackageClasses = EmployeeRepository.class)
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    /**
     * Returns the database name used by the application.
     *
     * @return configured in-memory database name
     */
    @Override
    @NonNull
    protected String getDatabaseName() {
        return "test";
    }

    /**
     * Starts an in-memory MongoDB server and creates its client.
     *
     * @return MongoDB client connected to the in-memory server
     */
    @Override
    @NonNull
    public MongoClient mongoClient() {
        MongoServer server = new MongoServer(new MemoryBackend());
        InetSocketAddress serverAddress = server.bind();
        String mongoConnectionString = String.format("mongodb://%s:%d", serverAddress.getHostName(), serverAddress.getPort());
        return MongoClients.create(mongoConnectionString);
    }
}
