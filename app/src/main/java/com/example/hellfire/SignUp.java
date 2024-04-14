package com.example.hellfire;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class SignUp extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    Button btnGoogleSignUp;
    FirebaseUser mUser;
    private GoogleSignInClient mGoogleSignInClient;
    //private static final int RC_SIGN_IN = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        btnGoogleSignUp = findViewById(R.id.btnGoogleSignUp);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                //.setAccountName() // Enable account chooser
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnGoogleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(SignUp.this, GoogleSignUp.class);
                //startActivity(intent);
                googleSignIn();
            }
        });
    }

    private void googleSignIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            }
            catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();

                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", user.getUid());

                        }
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