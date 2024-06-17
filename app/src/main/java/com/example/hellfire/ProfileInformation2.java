package com.example.hellfire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hellfire.Models.UserModel;
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
    private UserModel userModel;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_information2);
        userModel = (UserModel) getIntent().getSerializableExtra("userModel");

    }

    public void toBio(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.show();

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
        mUser = mAuth.getCurrentUser();
        String userId = mUser.getUid();

        // Создаем ссылку на узел "users" с уникальным идентификатором пользователя
        DatabaseReference userRef = mDatabase.child("users").child(userId);

        userModel.setI_want_see(selectedRadioButton_see.getText().toString())  ;
        userModel.setHere_for(here_for);  //selectedRadioButton_for.getText().toString();
        userModel.setFriends(radioFriends.isChecked());
        userModel.setConcert_buddies(radioConcert_buddies.isChecked());
        // Вызываем метод toMap() у объекта UserModel, чтобы получить данные в формате Map
        Map<String, Object> userMap = userModel.toMap();

        // Сохраняем данные пользователя в базу данных
        userRef.setValue(userMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Данные успешно сохранены

                        Intent intent = new Intent(ProfileInformation2.this, ShortBio.class);
                        intent.putExtra("userModel", userModel);
                        startActivity(intent);
                        progressDialog.dismiss();
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

    public void toProfileInformation(View view) {

        Intent intent = new Intent(ProfileInformation2.this, ProfileInformation.class);
        intent.putExtra("userModel", userModel);
        startActivity(intent);
        finish();
    }
}