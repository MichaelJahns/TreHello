package com.michaeljahns.tre_hello.teams;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Team {
    private String teamName;
    private List<String> membersIDs;
    private List<String> membersNames;

    @Exclude
    private String teamID;

    public Team(){
        membersIDs = new ArrayList<>();
        membersNames = new ArrayList<>();
    }

    public Team withID(String id){
        this.teamID = id;
        return this;
    }


    public void addMember(String userID, String name){
        membersIDs.add(userID);
        membersNames.add(name);
    }

    public void removeMember(String userID){
        int index = membersIDs.indexOf(userID);
        membersIDs.remove(index);
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
    public List<String> getMembersIDs()  {
        return this.membersIDs;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
