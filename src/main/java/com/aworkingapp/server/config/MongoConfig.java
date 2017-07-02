package com.aworkingapp.server.config;

import com.aworkingapp.server.config.properties.ApplicationProperties;
import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chen on 2017-06-29.
 */
@Configuration
@EnableMongoAuditing
@EnableMongoRepositories({"com.aworkingapp.server.auth.repositories", "com.aworkingapp.server.repositories"})
public class MongoConfig extends AbstractMongoConfiguration{

    @Autowired
    ApplicationProperties applicationProperties;

    private final List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();

    @Override
    protected String getDatabaseName() {
        return applicationProperties.getMongoProperties().getDatabase();
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(mongoClientURI());
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        String authBase =  "com.aworkingapp.server.auth.model";
        String base =  "com.aworkingapp.server.domain";

        List<String> bases = new LinkedList<>();
        bases.add(authBase);
        bases.add(base);
        return bases;
    }

    @Bean
    public MongoClientURI mongoClientURI() throws Exception{
        return new MongoClientURI(applicationProperties.getMongoProperties().getUri(),
                MongoClientOptions.builder().writeConcern(WriteConcern.ACKNOWLEDGED));
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory((MongoClient) mongo(), applicationProperties.getMongoProperties().getDatabase());
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo(), applicationProperties.getMongoProperties().getDatabase());
        mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);

        return mongoTemplate;
    }
}
