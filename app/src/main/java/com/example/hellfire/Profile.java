package com.example.hellfire;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hellfire.Models.UserModel;
import com.example.hellfire.utils.AndroidUtil;
import com.example.hellfire.utils.FirebaseUtil;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    private ImageView profilePicture;
    private TextView userName;
    private TextView userAge;
    private TextView userPriority;
    private TextView lookingFor;
    private UserModel userModel;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    private FlexboxLayout genreContainer;
    private LinearLayout photosContainer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userModel = (UserModel) getIntent().getSerializableExtra("userModel");

        userName = findViewById(R.id.et_profile_name);
        userAge = findViewById(R.id.et_profile_age);
        userPriority = findViewById(R.id.et_my_priority);
        profilePicture = findViewById(R.id.iv_profile_image);
        genreContainer = findViewById(R.id.genre_container);
        lookingFor = findViewById(R.id.looking_for);
        photosContainer = findViewById(R.id.photos_container);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        loadUserData();
        loadProfilePicture();
        loadUserGenres();
        loadUserPhotos();

    }


    private void loadUserData() {
        if (mUser != null) {
            String userId = mUser.getUid();
            Log.d("id", userId);
            DatabaseReference userRef = mDatabase.child("users").child(mUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userPriority.setMovementMethod(new ScrollingMovementMethod());
                    String priority = dataSnapshot.child("user_bio").getValue(String.class);
                    String looking_for = dataSnapshot.child("here_for").getValue(String.class);

                    if (priority != null) {
                        userPriority.setText(priority);
                    } else {
                        userPriority.setText("not data");
                    }
                    if (looking_for != null) {
                        lookingFor.setText("");  //looking_for
                    } else {
                        lookingFor.setText("");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Profile", "Error loading user data", databaseError.toException());
                }
            });
        }
    }
    private void loadUserGenres() {
        if (mUser != null) {
            DatabaseReference favoritesReference = mDatabase.child("users").child(mUser.getUid()).child("favorites");
            favoritesReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot genreSnapshot : dataSnapshot.getChildren()) {
                            String genre = genreSnapshot.getValue(String.class);
                            Log.d("genre", genre);
                            if (genre != null) {
                                addGenreButton(genre);
                            } else {
                                Log.d("Profile", "Genre value is null for key: ");
                            }
                        }
                    } else {
                        Log.d("Profile", "No genres found for user");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Profile", "Error loading genres", databaseError.toException());
                }
            });
        }
    }

    private void addGenreButton(String genre) {
        Button genreButton = new Button(this);
        genreButton.setText(genre);
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 10, 10, 10);
        genreButton.setLayoutParams(params);
        genreButton.setBackground(getResources().getDrawable(R.drawable.btn_bg_custom));
        genreButton.setTextSize(10);
        genreContainer.addView(genreButton);
    }


    private void loadProfilePicture() {
        FirebaseUtil.getCurrentProfilePicStorageRef().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri uri = task.getResult();
                    AndroidUtil.setProfilePic(Profile.this, uri, profilePicture);
                } else {
                    Log.e("Profile", "Error loading profile picture", task.getException());
                }
            }
        });
    }

    // Function to load user photos /////    надо доделать //////
    private void loadUserPhotos() {
        DatabaseReference userPhotosRef = FirebaseDatabase.getInstance().getReference("user_photos").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userPhotosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot photoSnapshot : dataSnapshot.getChildren()) {
                    String photoUrl = photoSnapshot.getValue(String.class);
                    if (photoUrl != null) {
                        addPhotoToContainer(photoUrl);
                    }
                    else {
                        Log.d("photo", "null");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Profile", "Database error: " + databaseError.getMessage());
            }
        });
    }


    // Function to add a photo to the photos_container
    private void addPhotoToContainer(String photoUrl) {
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 8, 8, 8);
        imageView.setLayoutParams(params);

        Picasso.get().load(photoUrl).into(imageView);
        photosContainer.addView(imageView);
    }

    public void toSearchUser(View view) {
        Intent intent = new Intent(Profile.this, SearchUser.class);
        startActivity(intent);
        finish();
    }
}