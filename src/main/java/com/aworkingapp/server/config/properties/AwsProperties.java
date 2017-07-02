package com.aworkingapp.server.config.properties;

/**
 * Created by chen on 2017-06-20.
 */
public class AwsProperties {
    private String baseUrl;
    private String accessKey;
    private String secretKey;
    private String imageBucketName;
    private String csvBucketName;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getImageBucketName() {
        return imageBucketName;
    }

    public void setImageBucketName(String imageBucketName) {
        this.imageBucketName = imageBucketName;
    }

    public String getCsvBucketName() {
        return csvBucketName;
    }

    public void setCsvBucketName(String csvBucketName) {
        this.csvBucketName = csvBucketName;
    }
}
