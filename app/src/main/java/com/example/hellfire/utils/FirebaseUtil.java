package com.example.hellfire.utils;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;

public class FirebaseUtil {
    private static String userPath;
    public static String currentUserId() {return FirebaseAuth.getInstance().getUid();}


    public static boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public static StorageReference currentUserDetails() {
        // Получаем уникальный идентификатор текущего пользователя
        String userId = currentUserId();

        // Создаем путь к директории пользователя в Firebase Storage
        userPath = "users/" + userId;

        // Получаем ссылку на директорию пользователя в Firebase Storage
        return FirebaseStorage.getInstance().getReference().child(userPath);
    }

    public static DatabaseReference allUserDatabaseReference() {

        String allUsersPath = "users";
        return FirebaseDatabase.getInstance().getReference().child(allUsersPath);
    }

    public static StorageReference  getCurrentProfilePicStorageRef(){

        StorageReference imageUrlRef = FirebaseStorage.getInstance().getReference()
                .child("images")
                .child(currentUserId())
                .child("0");

        return imageUrlRef;
    }
    public static StorageReference getOtherProfilePicStorageRef(String otherUserId) {
        if (otherUserId != null && !otherUserId.isEmpty()) {
            return FirebaseStorage.getInstance().getReference().child("users")
                    .child(otherUserId)
                    .child("imageUrls")
                    .child("0");
        } else {
            String defaultUrl = "https://firebasestorage.googleapis.com/v0/b/hellfire-fc145.appspot." +
                    "com/o/images%2F1JiUqAX9g0OYRFXMbCwv5A14BLr1%2F82de8e23-2ba9-472c-bb8b-44e63bc7f3fc?" +
                    "alt=media&token=cb82db2b-169d-45bd-b458-0374d337d7f6" +
                    "" +
                    "" +
                    "";
            return FirebaseStorage.getInstance().getReferenceFromUrl(defaultUrl);
        }
    }

    public static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }

}
