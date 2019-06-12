package com.michaeljahns.tre_hello.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.michaeljahns.tre_hello.R;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "PROFILE";
    EditText profileName;
    EditText profileBio;
    Spinner profileSign;
    String deviceID;
    ArrayAdapter<String> signAdapter;

    FirebaseUser user;
    String userID;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setUP();
        if (user != null) {
            userID = user.getUid();
            getUserInformation();
            getUserToken();
        }
    }

    public void setUP() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseFirestore.getInstance();
        profileName = (EditText) findViewById(R.id.viewPreferredName);
        profileBio = findViewById(R.id.viewBiography);
        profileSign = findViewById(R.id.viewSignSpinner);
        signAdapter = initializeSpinner();
        profileSign.setAdapter(signAdapter);
    }

    public ArrayAdapter<String> initializeSpinner() {
        ArrayAdapter<String> output = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.profileSigns));
        output.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return output;
    }

    public void getUserInformation() {
        userID = user.getUid();
        database.collection("Profiles").document(userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snap = task.getResult();
                            if (snap.exists()) {
                                autoFillKnown(snap);
                            }
                        } else {
                            Log.d("PROFILE", "Get Profile Failed:", task.getException());
                        }
                    }
                });
    }

    public void autoFillKnown(DocumentSnapshot doc) {
        profileName.setText(doc.get("Preferred Name").toString());
        profileBio.setText(doc.get("Biography").toString());
        String previouslySelectedSign = doc.get("Sign").toString();
        profileSign.setSelection(signAdapter.getPosition(previouslySelectedSign));
    }

    public void onSubmitProfile(View view) {
        HashMap<String, String> profileData = new HashMap<>();
        profileData.put("Preferred Name", profileName.getText().toString());
        profileData.put("Biography", profileBio.getText().toString());
        profileData.put("Sign", profileSign.getSelectedItem().toString());
        //TODO: There is alot to think about in storing multiple device ids. Should I store everyone
        // I have ever encountered in an array, even if they are all of the same device with multiple resets on ID.
        // Should I store the most recently seen and potentially ignore secondary devices?
        profileData.put("deviceID", deviceID);
        database.collection("Profiles").document(userID).set(profileData);
        this.finish();
    }

    public void getUserToken(){
        FirebaseInstanceId instanceID = FirebaseInstanceId.getInstance();

        instanceID
                .getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(!task.isSuccessful()){
                            Log.w(TAG, "Unable to get the ID!");
                            return;
                        }
                        String token = task.getResult().getToken();
                        deviceID = token;

                        Log.d(TAG, token);

                    }
                });
    }
}
