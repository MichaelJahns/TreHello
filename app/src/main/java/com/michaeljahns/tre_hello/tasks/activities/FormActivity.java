package com.michaeljahns.tre_hello.tasks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.michaeljahns.tre_hello.R;
import com.michaeljahns.tre_hello.tasks.Task;

public class FormActivity extends AppCompatActivity {
    EditText taskName;
    EditText taskDescription;
    Spinner taskPhase;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        taskName = findViewById(R.id.newTaskName);
        taskDescription = findViewById(R.id.newTaskDescription);
        taskPhase = findViewById(R.id.newTaskPhase);

        ArrayAdapter<String> phases = initializeSpinner();
        taskPhase.setAdapter(phases);

        database = FirebaseFirestore.getInstance();
    }

    public ArrayAdapter<String> initializeSpinner(){
        ArrayAdapter<String> output = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.phases));
        output.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return output;
    }

    public void onTaskSubmission(View view) {
        Task task = new Task();
        task.setTask(taskName.getText().toString());
        task.setDescription(taskDescription.getText().toString());
        task.setStatus(taskPhase.getSelectedItem().toString());

        database.collection("Tasks")
                .add(task)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TaskSubmission", "Submitted Task with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TaskSubmission", "Failed to Submit Task", e);
                    }
                });

        Intent intent = new Intent(this, TaskStreamActivity.class);
        startActivity(intent);
    }
}
