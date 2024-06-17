package com.example.hellfire;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.core.content.FileProvider;

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
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import com.example.hellfire.BuildConfig;

public class AddPicture extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;
    private UserModel userModel;
    private Uri filePath;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private int imageIndex = 0;
    private Button btnGallery, btnCamera;
    private ImageView imageView;

    private Uri photoURI;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;

    ActivityResultLauncher<PickVisualMediaRequest> launcher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri == null) {
                        Toast.makeText(AddPicture.this, "No image selected", Toast.LENGTH_SHORT).show();
                    } else {
                        Picasso.get().load(uri).into(imageView);
                        filePath = uri;
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
                launcher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
    }

    private void openCamera() {
        // Проверяем, есть ли приложение для обработки Intent для захвата изображения
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Создаем файл для сохранения изображения
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("AddPicture", "Error occurred while creating the file", ex);
            }

            if (photoFile != null) {
                // Создаем URI для сохранения изображения с использованием FileProvider
                photoURI = FileProvider.getUriForFile(this, "com.example.hellfire.provider", photoFile);

                // Добавляем URI в Intent для сохранения изображения по указанному пути
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Проверяем наличие разрешения на использование камеры
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Если разрешение не предоставлено, запрашиваем его у пользователя
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                } else {
                    // Если разрешение предоставлено, запускаем Intent для захвата изображения
                    startActivityForResult(intent, CAMERA_PERMISSION_REQUEST_CODE);
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && resultCode == RESULT_OK) {
            Picasso.get().load(photoURI).into(imageView);
            filePath = photoURI;
        }
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private void uploadImage() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            String userId = mUser.getUid();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Image...");
            progressDialog.show();

            StorageReference userRef = storageReference.child("images/" + userId);
            String fileName = String.valueOf(imageIndex);
            StorageReference fileRef = userRef.child(fileName);

            fileRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot snapshot) {
                    progressDialog.dismiss();

                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUrl = uri.toString();
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
                    int progress = (int) (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage(progress + "%");
                }
            });
        }
    }

    private void saveDownloadUrlToDatabase(String userId, String downloadUrl) {
        DatabaseReference userRef = mDatabase.child("users").child(userId);

        userRef.child("imageUrls").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> imageUrls = new ArrayList<>();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String url = snapshot.getValue(String.class);
                        if (url != null) {
                            imageUrls.add(url);
                        }
                    }
                }

                imageUrls.add(downloadUrl);
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
        intent.putExtra("userModel", userModel);
        startActivity(intent);
        finish();
    }

    public void ok(View view) {
        uploadImage();
    }
}

