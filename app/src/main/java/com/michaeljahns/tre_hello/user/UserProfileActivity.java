package com.michaeljahns.tre_hello.user;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.michaeljahns.tre_hello.R;

public class UserProfileActivity extends AppCompatActivity {

    TextView profileName;
    TextView profileBio;
    TextView profileSign;

    FirebaseUser user;
    FirebaseUser pageUser;
    String userID;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setUP();

        String pageOwner = getIntent().getStringExtra("pageUserID");
        getUserInformation(pageOwner);
    }

    public void setUP() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseFirestore.getInstance();
    }

    public void prepareView(DocumentSnapshot doc) {
        profileName = findViewById(R.id.editPreferredName);
        profileBio = findViewById(R.id.editBiography);
        profileSign = findViewById(R.id.editSignSpinner);

        profileName.setText(doc.get("Preferred Name").toString());
        profileBio.setText(doc.get("Biography").toString());
        profileSign.setText(doc.get("Sign").toString());
    }

    public void getUserInformation(String pageOwner) {
        database.collection("Profiles").document(pageOwner)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot snap = task.getResult();
                            if (snap.exists()) {
                                prepareView(snap);
                            }
                        } else {
                            Log.d("PROFILE", "Get Profile Failed:", task.getException());
                        }
                    }
                });
    }
}
