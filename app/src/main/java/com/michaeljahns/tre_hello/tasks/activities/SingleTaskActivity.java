package com.michaeljahns.tre_hello.tasks.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.michaeljahns.tre_hello.R;
import com.michaeljahns.tre_hello.tasks.Task;
import com.michaeljahns.tre_hello.teams.Team;
import com.michaeljahns.tre_hello.teams.TeamLayoutAdapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SingleTaskActivity extends AppCompatActivity {
    Task currentTask;

    TextView taskName;
    TextView taskDescription;
    TextView taskPhase;
    FirebaseFirestore database;
    FirebaseUser user;

    List<String> members;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TeamLayoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);

        setUP();

        String taskID = getIntent().getStringExtra("taskID");
        getTask(taskID);


    }

    public void setUP() {
        database = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        taskName = findViewById(R.id.singleViewTaskName);
        taskDescription = findViewById(R.id.singleViewTaskDescription);
        taskPhase = findViewById(R.id.singleViewTaskPhase);

        String teamReference = currentTask.getTeamReference();
        if(teamReference != null){
            setUpTeamRecycler();
        } else{
            Log.d("TEAM", "This task doesnt have a team");
        }
    }

    public void setUpTeamRecycler() {
        getTeamForRecycler();

        recyclerView = findViewById(R.id.recyclerAssignedUsers);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TeamLayoutAdapter(members);
        recyclerView.setAdapter(adapter);
    }

    public void getTeamForRecycler() {
        String teamID = currentTask.getTeamReference();
        database.collection("Teams").document(teamID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snap = task.getResult();
                            Team team = snap.toObject(Team.class).withID(snap.getId());
                            fillMembersList(team);
                        } else {
                            Log.d("TEAMS", "Failure to get team");
                        }
                    }
                });
    }


    public void getTask(final String taskID) {
        database.collection("Tasks").document(taskID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snap = task.getResult();
                            currentTask = snap.toObject(Task.class);
                            fillPage();
                        } else {
                            Log.d("Database", "Failure to get Task with ID " + taskID);
                        }
                    }
                });
    }

    public void fillPage() {
        taskName.setText(currentTask.getTask());
        taskDescription.setText(currentTask.getDescription());
        taskPhase.setText(currentTask.getStatus());
    }

    public void fillMembersList(Team team) {
        HashMap<String, String> map = team.getMembers();
        Collection<String> col = map.values();
        for (String value : col) {
            members.add(value);
        }
    }

    // Events
    public void onAssignSelf(View view) {
        String teamID = currentTask.getTeamReference();
        if (teamID != null) {
            getTeamToJoin(teamID);
        } else {
            newTeam();
        }
    }

    public void getTeamToJoin(final String teamID) {
        database.collection("Tasks").document(teamID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snap = task.getResult();
                            Team team = snap.toObject(Team.class).withID(snap.getId());
                            joinTeam(team);
                        } else {
                            Log.d("TEAM", "Failed to get Team: " + teamID);
                        }
                    }
                });
    }

    public void joinTeam(Team team) {
        team.addMember(user.getUid(), user.getDisplayName());
        database.collection("Teams").document(team.getTeamID()).set(team);
    }

    public void newTeam() {
        Team newTeam = new Team();
        newTeam.addMember(user.getUid(), user.getDisplayName());
        database.collection("Teams")
                .add(newTeam)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TEAM", "Submitted Team with ID: " + documentReference.getId());
                        String teamReference = documentReference.getId();
                        updateTaskWithTeamReference(teamReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TEAM", "Failed to Create Team", e);
                    }
                });
    }

    public void updateTaskWithTeamReference(String teamId) {
        database.collection("Tasks").document(currentTask.getTaskID())
                .update("teamReference", teamId)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TASK", "Updated this Tasks  teamReference!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TASK", "Failed to update this Task");
                    }
                });
    }
}
