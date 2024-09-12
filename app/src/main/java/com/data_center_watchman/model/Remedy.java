package com.data_center_watchman.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Remedy {
    @SerializedName("ResponseRefId")
    @Expose
    private String responseRefId;
    @SerializedName("ApprovalPahse")
    @Expose
    private String approvalPahse;
    @SerializedName("RequestStatus")
    @Expose
    private String requestStatus;
    @SerializedName("Summary")
    @Expose
    private String summary;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Reason For Change")
    @Expose
    private String reasonForChange;

    public String getResponseRefId() {
        return responseRefId;
    }

    public void setResponseRefId(String responseRefId) {
        this.responseRefId = responseRefId;
    }

    public String getApprovalPahse() {
        return approvalPahse;
    }

    public void setApprovalPahse(String approvalPahse) {
        this.approvalPahse = approvalPahse;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getReasonForChange() {
        return reasonForChange;
    }

    public void setReasonForChange(String reasonForChange) {
        this.reasonForChange = reasonForChange;
    }
}
