package com.example.hellfire;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hellfire.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddPicture extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private User user;
    private Uri filePath;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    Button btnGallery;
    ImageView imageView;

    ActivityResultLauncher<PickVisualMediaRequest> launcher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(),
            new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri o) {
            // Check if an image is selected
            if (o == null) {
                Toast.makeText(AddPicture.this, "No image Selected", Toast.LENGTH_SHORT).show();
            } else {
                // Load the selected image into the ImageView using Glide library
                Glide.with(getApplicationContext()).load(o).into(imageView);
                // Store the selected image URI in the filePath variable
               // filePath = o;
            }
        }

    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);
        user = (User) getIntent().getSerializableExtra("user");

        Button btnCamera = findViewById(R.id.btnCamera);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnGallery = findViewById(R.id.btnGallery);
        imageView = findViewById(R.id.imageView);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            // Launch the photo picker and let the user choose only images.
                launcher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }

        });
        //uploadImage();

    }


    
    public void chooseImage() {


    }

    public void toFavorites(View view) {
        Intent intent = new Intent(AddPicture.this, Favorites.class);
        startActivity(intent);
        intent.putExtra("user", user);
        finish();
    }
}