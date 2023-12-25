package com.example.hellfire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);
    }

    public void toBefore(View view) {
        Intent intent = new Intent(TermsActivity.this, BeforeStart.class);
        startActivity(intent);
        finish();
    }
}