package com.michaeljahns.tre_hello;

public class Task {
    private String task;
    private String description;
    private String status;

    public Task(){

    }

    public Task(String task, String description, String status){
        this.task = task;
        this.description = description;
        this.status = status;
    }


    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
