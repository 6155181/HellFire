package com.example.hellfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btnGoogleSignUp;
    FirebaseUser mUser;
    //private static final int RC_SIGN_IN = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        btnGoogleSignUp = findViewById(R.id.btnGoogleSignUp);

        btnGoogleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, GoogleSignUp.class);
                startActivity(intent);
            }
        });
    }

    public void signUp(View view) {
        EditText etEmail = findViewById(R.id.etRegEmail);
        EditText etPassword = findViewById(R.id.etRegPassword);
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Email Error");
            etEmail.requestFocus();
            return;
        }
        if(password.length()<8){
            etPassword.setError("Password Error");
            etPassword.requestFocus();
            return;
        }
        //Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();
        //-----   Sign In
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Registration Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, ProfileInformation.class);
                            intent.putExtra("EMAIL_ACCOUNT", email);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUp.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void toMain(View view) {
        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}