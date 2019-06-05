package com.michaeljahns.tre_hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button courierLog;
    FirebaseUser user;
    private static final int RC_SIGN_IN = 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        courierLog = findViewById(R.id.toggleLogActions);

        user = FirebaseAuth.getInstance().getCurrentUser();
        toggleDisplayName();
    }

    private void toggleDisplayName(){
        TextView displayName = findViewById(R.id.viewDisplayName);
        ToggleButton toggleLogActions = findViewById(R.id.toggleLogActions);
        if(user != null){
            displayName.setText("Tre-Hello! " + user.getDisplayName() +"!!");
            toggleLogActions.setChecked(false);
        } else {
            displayName.setText("Tre-Hello, guest");
            toggleLogActions.setChecked(true);
        }
    }

    public void onLogin(View view) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        startActivityForResult(
                AuthUI.getInstance().
                        createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    public void onLogout(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("USER", "User has logged out");
                    }
                });
    }

    public void onToggleLogActions(View view) {
        if (((ToggleButton) view).isChecked()) {
            onLogout(view);
        } else {
            onLogin(view);
        }
    }

    public void onCourierTaskStream(View view) {
        Intent intent = new Intent(this, TaskStreamActivity.class);
        startActivity(intent);
    }
}
