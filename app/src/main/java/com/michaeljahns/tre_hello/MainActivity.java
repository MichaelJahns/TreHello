package com.michaeljahns.tre_hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.michaeljahns.tre_hello.tasks.activities.TaskStreamActivity;
import com.michaeljahns.tre_hello.user.EditProfileActivity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore database;
    FirebaseUser user;


    private ToggleButton toggleLogActions;
    private Button courierLog;
    private Button courierProfile;
    private Button courierAllTasks;

    private TextView displayName;
    private static final int RC_SIGN_IN = 1024;

    //Setup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUP();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggleLogState();
    }

    public void setUP(){
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    toggleLogState();
                } else{
                    toggleLogState();
                }
            }
        };
        firebaseAuth.addAuthStateListener(authStateListener);

        toggleLogActions = findViewById(R.id.toggleLogActions);
        courierLog = findViewById(R.id.toggleLogActions);
        courierProfile = findViewById(R.id.courierProfileEdit);
        courierAllTasks = findViewById(R.id.courierNewTask);

        displayName = findViewById(R.id.viewDisplayName);
    }

    //Log Actions
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            // New user? What is this block?
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                user = FirebaseAuth.getInstance().getCurrentUser();
                String userID = user.getUid();
                DocumentReference documentReference = database.collection("Profiles").document(userID);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            if(doc.exists()){
                                //User has profile Information
                            }else{
                                //User has no profile information
                                onCourierEditProfile(courierProfile.getRootView());
                            }
                        }else{
                            // Error retrieving user information
                            Log.d("USER", "get failed with", task.getException());
                        }
                    }
                });
            }
        }
    }

    public void onLogout(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("USER", "Active User has logged out, has logged out");
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

    private void toggleLogState(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            displayName.setText("Tre-Hello! " + user.getDisplayName() +"!!");
            toggleLogActions.setChecked(false);
            courierProfile.setVisibility(View.VISIBLE);
            courierAllTasks.setVisibility(View.VISIBLE);
        } else {
            displayName.setText("Tre-Hello, guest");
            toggleLogActions.setChecked(true);
            courierProfile.setVisibility(View.INVISIBLE);
            courierAllTasks.setVisibility(View.INVISIBLE);
        }
    }

    // Couriers
    public void onCourierTaskStream(View view) {
        Intent intent = new Intent(this, TaskStreamActivity.class);
        startActivity(intent);
    }

    public void onCourierEditProfile(View view){
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
}
