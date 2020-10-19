package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {

    private Integer credentialId;
    private String credentialUrl;
    private String credentialUsername;
    private String credentialKey;
    private String credentialPassword;
    private Integer userId;

    public Credential(Integer credentialId, String credentialUrl, String credentialUsername, String credentialKey, String credentialPassword, Integer userId) {
        this.credentialId = credentialId;
        this.credentialUrl = credentialUrl;
        this.credentialUsername = credentialUsername;
        this.credentialKey = credentialKey;
        this.credentialPassword = credentialPassword;
        this.userId = userId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
    }

    public String getCredentialUsername() {
        return credentialUsername;
    }

    public void setCredentialUsername(String credentialUsername) {
        this.credentialUsername = credentialUsername;
    }

    public String getCredentialKey() {
        return credentialKey;
    }

    public void setCredentialKey(String credentialKey) {
        this.credentialKey = credentialKey;
    }

    public String getCredentialPassword() {
        return credentialPassword;
    }

    public void setCredentialPassword(String credentialPassword) {
        this.credentialPassword = credentialPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
