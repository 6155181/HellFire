package com.example.hellfire.utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public static Task<DataSnapshot> getAllUsers() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        return usersRef.get();
    }


    public static StorageReference  getCurrentProfilePicStorageRef(){

        StorageReference imageUrlRef = FirebaseStorage.getInstance().getReference()
                .child("images")
                .child(currentUserId())
                .child("0");

        return imageUrlRef;
    }
    public static DatabaseReference getOtherProfilePicStorageRef(String otherUserId) {
        if (otherUserId != null && !otherUserId.isEmpty()) {
            return FirebaseDatabase.getInstance().getReference().child("users")
                    .child(otherUserId)
                    .child("imageUrls")
                    .child("0");
        } else {
            String defaultUrl = "https://firebasestorage.googleapis.com/v0/b/hellfire-fc145.appspot." +
                    "com/o/images%2F1JiUqAX9g0OYRFXMbCwv5A14BLr1%2F82de8e23-2ba9-472c-bb8b-44e63bc7f3fc?" +
                    "alt=media&token=cb82db2b-169d-45bd-b458-0374d337d7f6";
            // Возвращаем ссылку на базу данных, которая содержит URL по умолчанию
            DatabaseReference defaultRef = FirebaseDatabase.getInstance().getReferenceFromUrl(defaultUrl);
            return defaultRef;
        }
    }


    public static String timestampToString(long timestamp) {
        Date date = new Date(timestamp);
        return new SimpleDateFormat("HH:mm").format(date);
    }

    public static void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    public static StorageReference getUserPhotosStorageRef() {
        StorageReference allImageUrlRef = FirebaseStorage.getInstance().getReference()
                .child("images")
                .child(currentUserId());
        return allImageUrlRef;
    }
    public static DatabaseReference getUserProfilePicReference(String userId) {
        return FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(userId)
                .child("imageUrls")
                .child("0");
    }


    public static DatabaseReference getAllChatroomsReference() {
        return FirebaseDatabase.getInstance().getReference().child("chatrooms");
    }
    public static DatabaseReference getChatroomReference(String chatroomId) {
        return FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatroomId);
    }

    public static DatabaseReference getChatroomMessageReference(String chatroomId) {
        return getChatroomReference(chatroomId).child("messages");
    }

    // Сгенерировать ID для чата между двумя пользователями
    public static String getChatroomId(String userId1, String userId2) {
        if (userId1.hashCode() < userId2.hashCode()) {
            return userId1 + "_" + userId2;
        } else {
            return userId2 + "_" + userId1;
        }
    }


    public static DatabaseReference getOtherUserFromChatroom(List<String> userIds) {
        String currentUserId = FirebaseUtil.currentUserId();
        for (String userId : userIds) {
            if (!userId.equals(currentUserId)) {
                return FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            }
        }
        return null;
    }


    ///////////////////    Cloud    ///////////////////

    //public static CollectionReference allUserCollectionReference(){
    //    return FirebaseFirestore.getInstance().collection("users");
    //}

   // public static DocumentReference getChatroomReference(String chatroomId){
   //     return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
   // }

   // public static CollectionReference getChatroomMessageReference(String chatroomId){
   //     return getChatroomReference(chatroomId).collection("chats");
  //  }

   // public static String getChatroomId(String userId1,String userId2){
    //    if(userId1.hashCode()<userId2.hashCode()){
   //         return userId1+"_"+userId2;
    //    }else{
    //        return userId2+"_"+userId1;
    //    }
   // }

    //public static CollectionReference allChatroomCollectionReference(){
   //     return FirebaseFirestore.getInstance().collection("chatrooms");
   // }

    //public static DocumentReference getOtherUserFromChatroom(List<String> userIds){
     //   if(userIds.get(0).equals(FirebaseUtil.currentUserId())){
       //     return allUserCollectionReference().document(userIds.get(1));
     //   }else{
      //      return allUserCollectionReference().document(userIds.get(0));
      //  }





}
