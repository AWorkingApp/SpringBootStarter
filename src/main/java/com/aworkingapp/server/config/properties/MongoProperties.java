package com.aworkingapp.server.config.properties;

/**
 * Created by chen on 6/14/17.
 */
public class MongoProperties {

    private String uri;
    private String database;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

}
