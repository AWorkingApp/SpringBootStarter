package com.aworkingapp.server.domain;

/**
 * Created by chen on 6/15/17.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chen on 6/4/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbsMongoModel implements Serializable {

    @Id
    protected ObjectId id;

    public String getId() {
        return id.toString();
    }

    @CreatedDate
    @JsonIgnore
    @Indexed
    private Date created;

    @LastModifiedDate
    @JsonIgnore
    @Indexed
    private Date modified;

    // TODO: change time stamp key to created_time
    @JsonProperty("created")
    private long createdTimeMillis;

    @JsonProperty("modified")
    private long lastModifiedTimeMillis;

    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }

    public Date getCreatedDate() {
        return this.created;
    }

    @JsonIgnore
    public void setCreatedDate(Date creationDate) {
        this.created = creationDate;
        this.createdTimeMillis = this.created.getTime();
    }

    @JsonIgnore
    public Date getLastModifiedDate() {
        return this.modified;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.modified = lastModifiedDate;
        this.lastModifiedTimeMillis = this.modified.getTime();
    }

    public void setId(String id) {
        this.id = new ObjectId(id);
    }

    public long getCreatedTimeMillis() {
        return this.created.getTime();
    }

    public long getLastModifiedTimeMillis() {
        return this.modified.getTime();
    }

    //TODO implements this
    @Transient
    @JsonIgnore
    public String getCreatedBy() {
        return null;
    }

    @Transient
    @JsonIgnore
    public void setCreatedBy(String createdBy) {
    }

    @Transient
    @JsonIgnore
    public String getLastModifiedBy() {
        return null;
    }

    @Transient
    @JsonIgnore
    public void setLastModifiedBy(String lastModifiedBy) {
    }

    @JsonIgnore
    @Transient
    public ObjectNode toJsonObjectNode(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.valueToTree(this);
        return result;
    }

    @Transient
    @JsonIgnore
    public String toJsonString(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(this);
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //TODO log it
        }
        return "{}";
    }
}

