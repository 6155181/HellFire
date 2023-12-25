package com.example.hellfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();
    }

    public void toLogin(View view) {
        Intent intent = new Intent(ResetPassword.this, LogIn.class);
        startActivity(intent);
        finish();
    }

    public void reset(View view) {
        EditText etResetEmail = findViewById(R.id.etResetEmail);
        String resetEmail = etResetEmail.getText().toString().trim();

        mAuth.sendPasswordResetEmail(resetEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Успешное выполнение операции
                    Intent intent = new Intent(ResetPassword.this, LogIn.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Обработка ошибки
                    Exception e = task.getException();
                    if (e != null) {
                        Toast.makeText(ResetPassword.this, "Reset Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ResetPassword.this, "Reset Failed: Unknown Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}