package com.example.hellfire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PrivacyPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);
    }
    public void toBefore(View view) {
        Intent intent = new Intent(PrivacyPolicy.this, BeforeStart.class);
        startActivity(intent);
        finish();
    }
}