package com.michaeljahns.tre_hello.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.michaeljahns.tre_hello.R;

public class UserTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tasks);
    }

    //TODO: Recycler View
    //TODO: Pull Tasks with user ID as assigned
}
