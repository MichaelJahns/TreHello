package com.michaeljahns.tre_hello.tasks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.michaeljahns.tre_hello.R;
import com.michaeljahns.tre_hello.tasks.Task;
import com.google.firebase.auth.FirebaseUser;



public class EditTaskActivity extends AppCompatActivity {
    private static final String TAG = "EDIT-TASK";
    FirebaseUser user;
    FirebaseFirestore database;
    Task currentTask;

    TextView taskName;
    EditText editTaskDescription;
    Spinner editTaskPhase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        setUP();


    }

    private void setUP(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseFirestore.getInstance();

        Intent from = getIntent();
        getTaskInformation(from.getStringExtra("taskID"));
    }


    public void updateUI(){
        taskName = findViewById(R.id.viewTaskName);
        editTaskDescription = findViewById(R.id.editTaskDescription);
        editTaskPhase = findViewById(R.id.spinnerEditTaskSpinner);
        ArrayAdapter<String> phases = initializeSpinner();
        editTaskPhase.setAdapter(phases);

        taskName.setText(currentTask.getTask());
        editTaskDescription.setText(currentTask.getDescription());
        editTaskPhase.setSelection(phases.getPosition(currentTask.getStatus()));
    }

    public ArrayAdapter<String> initializeSpinner(){
        ArrayAdapter<String> output = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.phases));
        output.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return output;
    }

    public void getTaskInformation(final String taskID) {
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
                            Log.d(TAG, "Failure to get Task with ID " + taskID);
                        }
                    }
                });
    }

    public void onUpdateTask(View view){
        String updatedDescription = editTaskDescription.getText().toString();
        String updatedPhase = editTaskPhase.getSelectedItem().toString();
        database.collection("Tasks").document(currentTask.getTaskID())
                .update(
                        "description", updatedDescription,
                        "status", updatedPhase
                )
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Task has been updated");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Error updating document", e);
            }
        });
        this.finish();
    }
}
