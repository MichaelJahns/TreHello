package com.michaeljahns.tre_hello.teams;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Team {
    private String teamName;
    private List<String> membersIDS;
    private List<String> membersNames;

    @Exclude
    private String teamID;

    public Team(){
        membersIDS = new ArrayList<>();
        membersNames = new ArrayList<>();
    }

    public Team withID(String id){
        this.teamID = id;
        return this;
    }


    public void addMember(String userID, String name){
        membersIDS.add(userID);
        membersNames.add(name);
    }

    public void removeMember(String userID){
        int index = membersIDS.indexOf(userID);
        membersIDS.remove(index);
        membersNames.remove(index);
    }

    public String getTeamID() {
        return teamID;
    }
    public String getTeamName() {
        return teamName;
    }
    public List<String> getMembersNames()  {
        return this.membersNames;
    }
    public List<String> getMembersIDS()  {
        return this.membersIDS;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
