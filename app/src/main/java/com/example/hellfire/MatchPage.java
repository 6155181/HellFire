package com.example.hellfire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.hellfire.Models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MatchPage extends AppCompatActivity {

    ImageView imageView;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private UserModel userModel;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_page);
        userModel = (UserModel) getIntent().getSerializableExtra("userModel");

        imageView = findViewById(R.id.ivPic);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            loadImageForCurrentUser();
        } else {
            // Handle the case where the user is not authenticated
            // You may want to redirect the user to the login screen or take appropriate action
        }
    }

    private void loadImageForCurrentUser() {
        progressDialog.setMessage("Loading Image...");
        progressDialog.show();

        // Assuming your UserModel has a method to get the user ID
        String userId = mUser.getUid();

        if (userId != null && !userId.isEmpty()) {
            // Use Glide to load the image into the ImageView
            Glide.with(this)
                    .load(getProfilePicStorageRef(userId))
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressDialog.dismiss();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressDialog.dismiss();
                            return false;
                        }
                    })
                    .into(imageView);
        } else {
            // Handle the case where userId is null or empty
            // For example, you can set a default image or show an error message
            imageView.setImageResource(R.drawable.error_image);
        }
    }

    private StorageReference getProfilePicStorageRef(String userId) {
        // Assuming your profile pictures are stored under "users" -> "userId" -> "imageUrls" -> "0"
        return storageReference.child("images")
                .child(userId)
                //.child("imageUrls")
                .child("0");
    }


    public void toProfile(View view) {
        Intent intent = new Intent(MatchPage.this, Profile.class);
        intent.putExtra("userModel", userModel);
        startActivity(intent);
        finish();
    }
}
