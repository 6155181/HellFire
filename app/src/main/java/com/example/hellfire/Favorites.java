package com.example.hellfire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hellfire.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Favorites extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        user = (User) getIntent().getSerializableExtra("user");

    }

    public void shortBio(View view) {
        Intent intent = new Intent(Favorites.this, ShortBio.class);
        startActivity(intent);
        intent.putExtra("user", user);
        finish();
    }

    public void toAddPicture(View view) {
        Intent intent = new Intent(Favorites.this, AddPicture.class);
        startActivity(intent);
        intent.putExtra("user", user);
        finish();
    }
}