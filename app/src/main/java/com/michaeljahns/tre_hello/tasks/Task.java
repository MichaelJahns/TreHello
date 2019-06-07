package com.michaeljahns.tre_hello.tasks;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Task {
    private String task;
    private String description;
    private String status;
    private String teamReference;

    @Exclude
    private String taskID;


    public Task(){

    }

    public Task(String task, String description, String status){
        this.task = task;
        this.description = description;
        this.status = "New";
    }

    public Task withID(String id){
        this.taskID = id;
        return this;
    }

    public String getTask() {
        return task;
    }
    public String getDescription() {
        return description;
    }
    public String getStatus() {
        return status;
    }
    public String getTaskID() {
        return taskID;
    }
    public String getTeamReference() {
        return teamReference;
    }



    public void setTask(String task) {
        this.task = task;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setTeamReference(String teamReference){ this.teamReference = teamReference ;}


}
