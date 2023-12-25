package com.example.hellfire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void before(View view) {
        Intent intent = new Intent(MainActivity.this, BeforeStart.class);
        startActivity(intent);
        finish();
    }

    public void dive_in(View view) {
        Intent intent = new Intent(MainActivity.this, LogIn.class);
        startActivity(intent);
        finish();
    }
}