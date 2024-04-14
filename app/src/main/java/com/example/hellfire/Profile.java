package com.example.hellfire;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hellfire.Models.UserModel;
import com.example.hellfire.utils.AndroidUtil;
import com.example.hellfire.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    ImageView profilePicture;
    EditText userName;
    EditText userAge;
    EditText userPriority;
    private UserModel userModel;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private DatabaseReference mDatabase;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userModel = (UserModel) getIntent().getSerializableExtra("userModel");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userName = findViewById(R.id.et_profile_name);
        userAge = findViewById(R.id.et_profile_age);
        userPriority = findViewById(R.id.et_my_priority);

        DatabaseReference userRef = mDatabase.child("users").child(mUser.getUid());

        userRef.addListenerForSingleValueEvent(new ProfileValueEventListener());

        profilePicture = findViewById(R.id.iv_profile_image);

        FirebaseUtil.getCurrentProfilePicStorageRef().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()) {
                    Uri uri  = task.getResult();
                    //Toast.makeText(Profile.this, ""+userName, Toast.LENGTH_SHORT).show();
                    AndroidUtil.setProfilePic(Profile.this,uri,profilePicture);
                }
            }
        });

    }
    private class ProfileValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                // Retrieve the user data and set the values
                userModel = dataSnapshot.getValue(UserModel.class);
                if (userModel != null) {
                    userName.setText(userModel.getUsername());
                    userAge.setText(String.valueOf(userModel.getUserAge()));

                    String userBio = dataSnapshot.child("user_bio").getValue(String.class);
                    userPriority.setText(userBio);
                    //Toast.makeText(Profile.this, "bio"+userModel.getUsername(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d("TAG", "Database Error: " + databaseError.getMessage());
        }

    }

    public void toSearchUser(View view) {
        Intent intent = new Intent(Profile.this, SearchUser.class);
        startActivity(intent);
        finish();
    }
}