package com.example.hellfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btnGoogleAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        btnGoogleAuth = findViewById(R.id.btnGoogleAuth);
        mUser = mAuth.getCurrentUser();   // ************************************  //

        String forgotText = getString(R.string.forgot);
        SpannableString ss = new SpannableString(forgotText);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(LogIn.this, ResetPassword.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        // Примените ClickableSpan ко всей строке
        ss.setSpan(clickableSpan, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView textView = (TextView) findViewById(R.id.tvForgot);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);

        btnGoogleAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, GoogleSignInActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(LogIn.this, "Welcome back " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LogIn.this, UpcomingShow.class);
            intent.putExtra("EMAIL_ACCOUNT", currentUser.getEmail());
            startActivity(intent);
            finish();
        }
    }

    public void login(View view) {
        EditText etEmail = findViewById(R.id.etLoginEmail);
        EditText etPassword = findViewById(R.id.etLoginPassword);
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
        Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();
        //-----   Sign In
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LogIn.this, "Auth Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogIn.this, Profile.class);
                            intent.putExtra("EMAIL_ACCOUNT", email);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LogIn.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void toMain(View view) {
        Intent intent = new Intent(LogIn.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void to_reset(View view) {
        Intent intent = new Intent(LogIn.this, ResetPassword.class);
        startActivity(intent);
        finish();
    }

}