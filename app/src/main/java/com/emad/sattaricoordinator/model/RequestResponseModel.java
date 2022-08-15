package com.emad.sattaricoordinator.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RequestResponseModel implements Serializable {

    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("nationalCode")
    private String nid;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("isAdmin")
    private boolean isAdmin;
    @SerializedName("isDeleted")
    private boolean isDeleted;
    @SerializedName("requests")
    private List<RequestItem> requests;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<RequestItem> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestItem> requests) {
        this.requests = requests;
    }
}
