package com.example.hellfire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class UpcomingShow extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_show);
        mAuth = FirebaseAuth.getInstance();

        String emailAccount = mAuth.getCurrentUser().getEmail();

        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvEmail.setText(emailAccount);
    }

    public void logOut (View view){
        mAuth.signOut();
        Toast.makeText(this, "Good Buy", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpcomingShow.this, LogIn.class);
        startActivity(intent);
        finish();
    }
}