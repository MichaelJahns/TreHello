package com.michaeljahns.tre_hello.tasks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.michaeljahns.tre_hello.R;
import com.michaeljahns.tre_hello.tasks.Task;
import com.michaeljahns.tre_hello.tasks.TaskLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

public class TaskStreamActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TaskLayoutAdapter adapter;
    FirebaseFirestore db;

    //TODO: Filter tasks by status

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        db = FirebaseFirestore.getInstance();
        List<Task> tasks = getTasks();

        recyclerView = findViewById(R.id.taskRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskLayoutAdapter(tasks);
        recyclerView.setAdapter(adapter);
    }

    public List<Task> getTasks() {
        final List<Task> tasks = new ArrayList<>();

        db.collection("Tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snaps = task.getResult();

                            for (DocumentSnapshot snap : snaps.getDocuments()) {
                                Task taskObject = snap.toObject(Task.class).withID(snap.getId());
                                tasks.add(taskObject);
                            }
                            adapter.setTasks(tasks);
                        } else {
                            Log.d("Database", "Failure to get Collection");
                        }
                    }
                });
        return tasks;
    }

    public void onCourierForm(View view) {
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
    }
}