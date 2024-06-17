package com.example.hellfire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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


public class SignUp extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    Button btnGoogleSignUp;
    FirebaseUser mUser;
    private GoogleSignInClient mGoogleSignInClient;
    private EditText etRegPassword;
    private EditText etConfirmRegPassword;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        btnGoogleSignUp = findViewById(R.id.btnGoogleSignUp);
        etRegPassword = findViewById(R.id.etRegPassword);
        etConfirmRegPassword = findViewById(R.id.etConfirmRegPassword);

        ImageButton togglePasswordVisibility = findViewById(R.id.togglePasswordVisibility);
        togglePasswordVisibility.setOnClickListener(v -> {
            if (isPasswordVisible) {
                etRegPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePasswordVisibility.setImageResource(R.drawable.closed_eye2); // Closed eye icon
            } else {
                etRegPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePasswordVisibility.setImageResource(R.drawable.unhide_eye); // Open eye icon
            }
            isPasswordVisible = !isPasswordVisible;
            etRegPassword.setSelection(etRegPassword.getText().length()); // Move cursor to the end
        });

        ImageButton toggleConfirmPasswordVisibility = findViewById(R.id.toggleConfirmPasswordVisibility);
        toggleConfirmPasswordVisibility.setOnClickListener(v -> {
            if (isConfirmPasswordVisible) {
                etConfirmRegPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                toggleConfirmPasswordVisibility.setImageResource(R.drawable.closed_eye2); // Closed eye icon
            } else {
                etConfirmRegPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                toggleConfirmPasswordVisibility.setImageResource(R.drawable.unhide_eye); // Open eye icon
            }
            isConfirmPasswordVisible = !isConfirmPasswordVisible;
            etConfirmRegPassword.setSelection(etConfirmRegPassword.getText().length()); // Move cursor to the end
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set up sign-in button listener
        btnGoogleSignUp = findViewById(R.id.btnGoogleSignUp);
        btnGoogleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }

    private void googleSignIn() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w("SignUp", "Google sign in failed", e);
                Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w("SignUp", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(SignUp.this, ProfileInformation.class);
            intent.putExtra("EMAIL_ACCOUNT", user.getEmail());
            startActivity(intent);
            finish();
        }
    }

    public void signUp(View view) {

        EditText etEmail = findViewById(R.id.etRegEmail);
        EditText etPassword = findViewById(R.id.etRegPassword);
        EditText etConfirmRegPassword = findViewById(R.id.etConfirmRegPassword);
        TextView tvPasswordError = findViewById(R.id.tvPasswordError);
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmRegPassword = etConfirmRegPassword.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email Error");
            etEmail.requestFocus();
            return;
        }
        if (password.length() < 8) {
            etPassword.setError("Password Error");
            etPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmRegPassword)) {
            tvPasswordError.setVisibility(View.VISIBLE);
            return;
        } else {
            tvPasswordError.setVisibility(View.GONE);
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sign In...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Registration Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, ProfileInformation.class);
                            intent.putExtra("EMAIL_ACCOUNT", email);
                            startActivity(intent);
                            progressDialog.dismiss();
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
