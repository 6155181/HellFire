package com.example.hellfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.Map;

public class ProfileInformation2 extends AppCompatActivity {

    RadioGroup radioGroup_see;
    RadioGroup radioGroup_for;
    RadioGroup radioGroup_Friends;
    RadioGroup radioGroup_concert;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private String i_want_see;
    private String here_for;
    private Boolean friends;
    private Boolean concert_buddies;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_information2);
        user = (User) getIntent().getSerializableExtra("user");

    }

    public void toBio(View view) {
        radioGroup_see = findViewById(R.id.who);
        int selected_see = radioGroup_see.getCheckedRadioButtonId();

        radioGroup_for = findViewById(R.id.here);
        int selected_for = radioGroup_for.getCheckedRadioButtonId();

        radioGroup_Friends = findViewById(R.id.rgFriends);

        if(selected_see == -1) {
            // No radio button is selected
            Toast.makeText(this, "Please select your choose", Toast.LENGTH_SHORT).show();
            return;
        }
        // Get the selected radio button
        RadioButton selectedRadioButton_see = findViewById(selected_see);
        i_want_see = selectedRadioButton_see.getText().toString();

        if(selected_for == -1) {
            //Toast.makeText(this, "Please select your choose", Toast.LENGTH_SHORT).show();
            //return;
        }
        else{
            RadioButton selectedRadioButton_for = findViewById(selected_for);
            here_for = selectedRadioButton_for.getText().toString();
        }
        // Get the selected radio button
        RadioButton radioFriends = findViewById(R.id.btnFriends);
        if(radioFriends.isChecked()) {
            //friends = !friends;
            friends = true;
        }

        RadioButton radioConcert_buddies = findViewById(R.id.btnConcert);
        if(radioConcert_buddies.isChecked()) {
            //concert_buddies = !concert_buddies;
            concert_buddies = true;
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        mUser = mAuth.getCurrentUser();
        String userId = mUser.getUid();

        //User user = new User(i_want_see, here_for, friends, concert_buddies);
        //mDatabase.child("users").child(userId).setValue(user);


        // Создаем ссылку на узел "users" с уникальным идентификатором пользователя
        DatabaseReference userRef = mDatabase.child("users").child(userId);

        user.i_want_see = selectedRadioButton_see.getText().toString();
        user.here_for = here_for;  //selectedRadioButton_for.getText().toString();
        user.friends = radioFriends.isChecked();
        user.concert_buddies = radioConcert_buddies.isChecked();
        // Вызываем метод toMap() у объекта User, чтобы получить данные в формате Map
        Map<String, Object> userMap = user.toMap();

        // Сохраняем данные пользователя в базу данных
        userRef.setValue(userMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Данные успешно сохранены

                        Intent intent = new Intent(ProfileInformation2.this, ShortBio.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Произошла ошибка при сохранении данных
                        Toast.makeText(ProfileInformation2.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void toProfileInformation(View view) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String userId = mUser.getUid();

        mDatabase.child("users").child(userId).child("username").setValue(null);
        mDatabase.child("users").child(userId).child("email").setValue(null);
        mDatabase.child("users").child(userId).child("user_yearOfBirth").setValue(null);
        mDatabase.child("users").child(userId).child("user_monthOfBirth").setValue(null);
        mDatabase.child("users").child(userId).child("user_dateOfBirth").setValue(null);
        mDatabase.child("users").child(userId).child("user_gender").setValue(null);


        Intent intent = new Intent(ProfileInformation2.this, ProfileInformation.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void clear(View view) {
        radioGroup_see = findViewById(R.id.who);
        radioGroup_for = findViewById(R.id.here);
        radioGroup_Friends = findViewById(R.id.rgFriends);
        radioGroup_concert = findViewById(R.id.concert);

        radioGroup_see.clearCheck();
        radioGroup_for.clearCheck();
        radioGroup_Friends.clearCheck();
        radioGroup_concert.clearCheck();
    }
}