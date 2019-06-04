package com.michaeljahns.tre_hello;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FormActivity extends AppCompatActivity {
    EditText taskName;
    EditText taskDescription;
    EditText taskStatus;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        taskName = findViewById(R.id.newTaskName);
        taskDescription = findViewById(R.id.newTaskDescription);
        taskStatus = findViewById(R.id.newTaskStatus);

        database = FirebaseFirestore.getInstance();
    }

    public void onTaskSubmission(View view) {
        Task task = new Task();
        task.setTask(taskName.getText().toString());
        task.setDescription(taskDescription.getText().toString());
        task.setStatus(taskStatus.getText().toString());

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

    }
}
