package com.example.hellfire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
        Intent intent = new Intent(MainActivity.this, Events.class);
        startActivity(intent);
        finish();
    }
}