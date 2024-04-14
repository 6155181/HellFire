package com.example.hellfire;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hellfire.Models.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class AddPicture extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    FirebaseUser mUser;
    private UserModel userModel;
    private Uri filePath;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private int imageIndex = 0;
    Button btnGallery, btnCamera;
    ImageView imageView;
    Bitmap bitmap;

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
                filePath = o;
            }
        }

    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);
        userModel = (UserModel) getIntent().getSerializableExtra("userModel");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imageView = findViewById(R.id.imageView);

        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            // Launch the photo picker and let the userModel choose only images.
                launcher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
                //
            }

        });

    }

    private void uploadImage() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            String userId = mUser.getUid();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Upload Image...");
            progressDialog.show();

            // Create a reference to the userModel's folder using their ID
            StorageReference userRef = storageReference.child("images/" + userId);

            String fileName = String.valueOf(imageIndex);
            StorageReference fileRef = userRef.child(fileName);

            fileRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot snapshot) {
                    progressDialog.dismiss();

                    // Get the download URL after successful upload
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUrl = uri.toString();

                            // Save the download URL to the database
                            saveDownloadUrlToDatabase(userId, downloadUrl);
                            imageIndex++;

                            imageView.setImageResource(R.drawable.baseline_add_24);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddPicture.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    int progress = (int) (100 * (float) snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage(progress + "%");
                }
            });
        }
    }

    private void saveDownloadUrlToDatabase(String userId, String downloadUrl) {
        DatabaseReference userRef = mDatabase.child("users").child(userId);

        // Get the current list of image URLs
        userRef.child("imageUrls").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> imageUrls = new ArrayList<>();

                // Add existing image URLs to the list if available
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String url = snapshot.getValue(String.class);
                        if (url != null) {
                            imageUrls.add(url);
                        }
                    }
                }

                // Add the new download URL to the list
                imageUrls.add(downloadUrl);

                // Save the updated list to the database
                userRef.child("imageUrls").setValue(imageUrls);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors if needed
            }
        });
    }
    public void toMathPage(View view) {

        Intent intent = new Intent(AddPicture.this, Profile.class);

        startActivity(intent);
        intent.putExtra("userModel", userModel);
        finish();
    }

    public void ok(View view) {
        uploadImage();
    }
}