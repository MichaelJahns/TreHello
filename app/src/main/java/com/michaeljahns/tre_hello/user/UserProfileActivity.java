package com.michaeljahns.tre_hello.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.michaeljahns.tre_hello.R;

import java.util.HashMap;

public class UserProfileActivity extends AppCompatActivity {

    EditText profileName;
    EditText profileBio;
    Spinner profileSign;
    ArrayAdapter<String> signAdapter;

    FirebaseUser user;
    String userID;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseFirestore.getInstance();


        setUP();
        if(user != null){
            userID = user.getUid();
            getUserInformation();
        }

    }


    public ArrayAdapter<String> initializeSpinner(){
        ArrayAdapter<String> output = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.profileSigns));
        output.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return output;
    }

    public void getUserInformation(){
        String userID = user.getUid();
        DocumentReference docReference = database.collection("Profiles").document(userID);
        docReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()){
                        autoFillKnown(doc);
                    } else{}
                } else {
                    Log.d("Profile", "Get Profile Failed:", task.getException());
                }
            }
        });
    }

    public void autoFillKnown(DocumentSnapshot doc){
        profileName.setText(doc.get("Preferred Name").toString());
        profileBio.setText(doc.get("Biography").toString());
        String sign = doc.get("Sign").toString();
        profileSign.setSelection(signAdapter.getPosition(sign));
    }
    public void setUP(){
        profileName = findViewById(R.id.profilePreferredName);
        profileBio = findViewById(R.id.profileBiography);
        profileSign = findViewById(R.id.profileSignSpinner);
        signAdapter = initializeSpinner();
        profileSign.setAdapter(signAdapter);
    }

    public void onSubmitProfile(View view){
        HashMap<String, String> profileData = new HashMap<>();
        profileData.put("Preferred Name", profileName.getText().toString());
        profileData.put("Biography", profileBio.getText().toString());
        profileData.put("Sign", profileSign.getSelectedItem().toString());
        database.collection("Profiles").document(userID).set(profileData);
        this.finish();
    }

}
