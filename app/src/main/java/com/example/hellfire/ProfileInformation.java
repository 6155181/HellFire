package com.example.hellfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hellfire.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ProfileInformation extends AppCompatActivity {
    EditText date;
    EditText uname;
    RadioGroup radioGroup;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private String user_yearOfBirth;
    private String user_monthOfBirth;
    private String user_dateOfBirth;
    private String gender;
    private DatabaseReference mDatabase;
// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_information);

        //mDatabase = FirebaseDatabase.getInstance().getReference();
        date = (EditText)findViewById(R.id.etBirthday);
        date.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);

                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);

                        user_yearOfBirth = String.valueOf(year);
                        user_monthOfBirth = String.valueOf(mon);
                        user_dateOfBirth = String.valueOf(day);

                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date.setText(current);
                    date.setSelection(sel < current.length() ? sel : current.length());
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}


        });
    }

    public void userInfo(View view) {

        uname = findViewById(R.id.etName);
        String name = uname.getText().toString().trim();
        if(name.equals("") || name == null) {
            uname.setError("Name Error");
            uname.requestFocus();
            return;
        }
        if(user_yearOfBirth == null || user_monthOfBirth == null || user_dateOfBirth == null) {
            date.setError("Date Error");
            date.requestFocus();
            return;
        }
        radioGroup = findViewById(R.id.who);
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId == -1) {
            // No radio button is selected
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the selected radio button
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

        // Set the gender based on the selected radio button
        gender = selectedRadioButton.getText().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        mUser = mAuth.getCurrentUser();
        String userId = mUser.getUid();
        String email = mUser.getEmail();

        User user = new User(name, email, user_yearOfBirth, user_monthOfBirth, user_dateOfBirth, gender);
        //mDatabase.child("users").child(userId).setValue(user);

        mDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileInformation.this, "User data saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileInformation.this, ProfileInformation2.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileInformation.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void toSignUp(View view) {
        Intent intent = new Intent(ProfileInformation.this, SignUp.class);
        startActivity(intent);
        finish();
    }
}