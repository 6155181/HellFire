package com.example.hellfire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.hellfire.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShortBio extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private User user;
    String user_bio;
    EditText bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_bio);
        user = (User) getIntent().getSerializableExtra("user");
    }

    public void toProfileInformation2(View view) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String userId = mUser.getUid();

        mDatabase.child("users").child(userId).child("i_want_see").removeValue();//setValue(null);
        mDatabase.child("users").child(userId).child("here_for").removeValue();//setValue(null);
        mDatabase.child("users").child(userId).child("friends").removeValue();//setValue(null);
        mDatabase.child("users").child(userId).child("concert_buddies").removeValue();//setValue(null);

        Intent intent = new Intent(ShortBio.this, ProfileInformation2.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void addBio(View view) {
        bio = findViewById(R.id.etShortBio);
        String userBio = bio.getText().toString().trim();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String userId = mUser.getUid();

        mDatabase.child("users").child(userId).child("user_bio").setValue(userBio);

        Intent intent = new Intent(ShortBio.this, Favorites.class);
        startActivity(intent);
        intent.putExtra("user", user);
        finish();
    }
}