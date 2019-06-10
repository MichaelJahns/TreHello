package com.michaeljahns.tre_hello.tasks.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.michaeljahns.tre_hello.R;
import com.michaeljahns.tre_hello.tasks.Task;
import com.michaeljahns.tre_hello.teams.Team;
import com.michaeljahns.tre_hello.teams.TeamLayoutAdapter;

import java.util.List;

public class SingleTaskActivity extends AppCompatActivity {
    Task currentTask;
    Team currentTeam;

    TextView taskName;
    TextView taskDescription;
    TextView taskPhase;
    TextView taskTeam;
    ToggleButton toggleTeamActions;
    FirebaseFirestore database;
    FirebaseUser user;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TeamLayoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
        setUP();
    }

    //TODO: Update task

    public void setUP() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseFirestore.getInstance();

        String taskID = getIntent().getStringExtra("taskID");
        getTask(taskID);
        getTeamDelay(300);
    }

    public void updateUI(){
        prepareView();
        toggleTeamState();
    }


    public void getTask(final String taskID) {
        database.collection("Tasks").document(taskID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snap = task.getResult();
                            currentTask = snap.toObject(Task.class).withID(snap.getId());
                            updateUI();
                        } else {
                            Log.d("Database", "Failure to get Task with ID " + taskID);
                        }
                    }
                });
    }

    public void getTeam() {
        String teamID = currentTask.getTeamReference();
        database.collection("Teams").document(teamID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snap = task.getResult();
                            currentTeam = snap.toObject(Team.class).withID(snap.getId());
                            updateUI();
                        } else {
                            Log.d("TEAMS", "Failure to get team");
                        }
                    }
                });
    }

    public void getTeamDelay(int delay) {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        try {
                            getTeam();
                        } catch (NullPointerException exception) {
                            Log.d("TEAM", "This Task does not have a team");
                        }
                    }
                },
                delay);
    }

    public void prepareView() {
        taskName = findViewById(R.id.singleViewTaskName);
        taskDescription = findViewById(R.id.singleViewTaskDescription);
        taskPhase = findViewById(R.id.singleViewTaskPhase);
        toggleTeamActions = findViewById(R.id.toggleTeamActions);

        taskName.setText(currentTask.getTask());
        taskDescription.setText(currentTask.getDescription());
        taskPhase.setText(currentTask.getStatus());
    }

    public void onToggleTeamActions(View view) {
        if (((ToggleButton) view).isChecked()) {
            onAssignSelf(view);
        } else {
            String teamID = currentTeam.getTeamID();
            leaveTeam(teamID);
        }
    }

    private void toggleTeamState(){
        if (currentTeam != null) {
            taskTeam = findViewById(R.id.singleViewTaskTeam);
            taskTeam.setText("Team:");

            recyclerView = findViewById(R.id.recyclerAssignedUsers);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            List<String> recyclablesNames = currentTeam.getMembersNames();
            List<String> recyclablesIDs = currentTeam.getMembersIDs();
            adapter = new TeamLayoutAdapter(recyclablesNames, recyclablesIDs);
            recyclerView.setAdapter(adapter);

            if (currentTeam.getMembersNames().contains(user.getDisplayName())) {
                Log.d("TEAM", "I matter");
                toggleTeamActions.setChecked(true);
            } else {
                toggleTeamActions.setChecked(false);
            }
        }
    }

    // Events
    public void onAssignSelf(View view) {
        String teamID = currentTask.getTeamReference();
        if (teamID != null) {
            joinTeam(teamID);
        } else {
            newTeam();
        }
    }

    public void joinTeam(String teamID) {
        DocumentReference reference = database.collection("Teams").document(teamID);
        reference.update("membersIDs", FieldValue.arrayUnion(user.getUid()));
        reference.update("membersNames", FieldValue.arrayUnion(user.getDisplayName()))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        getTeam();
                        updateUI();
                        Log.d("TEAM", "Team Update completed");
                    }
                });
    }
    public void leaveTeam(String teamID){
        database.collection("Team").document(teamID);
//                .delete()

        updateUI();
    }

    public void newTeam() {
        Team newTeam = new Team();
        newTeam.setTeamName("Team: " + currentTask.getTask());
        newTeam.addMember(user.getUid(), user.getDisplayName());
        database.collection("Teams")
                .add(newTeam)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TEAM", "Submitted Team with ID: " + documentReference.getId());
                        String teamID = documentReference.getId();
                        taskUpdateTeamReference(teamID);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TEAM", "Failed to Create Team", e);
                    }
                });
    }

    public void taskUpdateTeamReference(String teamID) {
        Task update = currentTask;
        update.setTeamReference(teamID);
        database.collection("Tasks").document(currentTask.getTaskID())
                .set(update)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TASK", "team Reference appended to Task");
                        getTeamDelay(300);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TASK", "Failure to update task");
                    }
                });
    }
}
