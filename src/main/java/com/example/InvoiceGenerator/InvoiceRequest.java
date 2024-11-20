package com.example.InvoiceGenerator;

import java.util.List;

public class InvoiceRequest {
    private String clientName;
    private String projectDetails;
    private List<InvoiceTask> tasks;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(String projectDetails) {
        this.projectDetails = projectDetails;
    }

    public List<InvoiceTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<InvoiceTask> tasks) {
        this.tasks = tasks;
    }
}
