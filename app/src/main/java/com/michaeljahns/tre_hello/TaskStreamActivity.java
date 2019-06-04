package com.michaeljahns.tre_hello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class TaskStreamActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TaskLayoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        List<Task> tasks = getTasks();
        recyclerView = findViewById(R.id.taskRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskLayoutAdapter(tasks);
        recyclerView.setAdapter(adapter);
    }

    public void onCourierForm(View view){
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
    }
}
