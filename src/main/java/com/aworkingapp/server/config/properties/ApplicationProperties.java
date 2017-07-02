package com.aworkingapp.server.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by chen on 6/14/17.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private BasicToken basicToken;

    private MongoProperties mongoProperties;

    private EncryptionProperties encryptionProperties;

    private boolean debug;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public BasicToken getBasicToken() {
        return basicToken;
    }

    public void setBasicToken(BasicToken basicToken) {
        this.basicToken = basicToken;
    }

    public MongoProperties getMongoProperties() {
        return mongoProperties;
    }

    public void setMongoProperties(MongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }

    public EncryptionProperties getEncryptionProperties() {
        return encryptionProperties;
    }

    public void setEncryptionProperties(EncryptionProperties encryptionProperties) {
        this.encryptionProperties = encryptionProperties;
    }
}

