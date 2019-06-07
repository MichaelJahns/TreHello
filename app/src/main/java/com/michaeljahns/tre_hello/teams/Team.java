package com.michaeljahns.tre_hello.teams;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Team {

    // I am really starting to think we would be better served by ArrayLists
    private String teamName;
    private Map<String, String> members;
    @Exclude
    private String teamID;

    public Team(){
        members = new HashMap<>();
    }

    public Team withID(String id){
        this.teamID = id;
        return this;
    }

    public Map<String, String> getMembers() {
        return members;
    }

    public void addMember(String userID, String name){
        members.put(userID, name);
    }

    public void removeMember(String userID){
        members.remove(userID);
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
