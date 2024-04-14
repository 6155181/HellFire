package com.example.hellfire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileSet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_set);
    }

    public void to_match_page(View view) {
        Intent intent = new Intent(ProfileSet.this, MatchPage.class);
        startActivity(intent);
        finish();
    }
}