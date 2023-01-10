package com.ig.unofficial.resources;

public class ApplicationStatus {
    String status;
    String api;

    public ApplicationStatus(String status, String api) {
        this.status = status;
        this.api = api;
    }

    public String getStatus() {
        return status;
    }

    public String getApi() {
        return api;
    }
}
