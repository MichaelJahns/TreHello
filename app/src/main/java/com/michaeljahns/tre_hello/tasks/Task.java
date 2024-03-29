package com.michaeljahns.tre_hello.tasks;

import java.util.HashSet;

public class Task {
    private String task;
    private String description;
    private String status;
    private HashSet<String> assigned;

    public Task(){

    }

    public Task(String task, String description, String status){
        this.task = task;
        this.description = description;
        this.status = "New";
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
