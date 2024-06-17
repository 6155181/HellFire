package com.example.hellfire.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hellfire.Models.UserModel;
public class AndroidUtil {

       //userModel = (UserModel) getIntent().getSerializableExtra("userModel");

    public static  void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public static void passUserModelAsIntent(Intent intent, UserModel model){
        intent.putExtra("username",model.getUserName());
        //intent.putExtra("phone",model.getPhone());
        intent.putExtra("userId",model.getUserId());
        intent.putExtra("userAge", model.getUserAge());
        //intent.putExtra("fcmToken",model.getFcmToken());

    }

    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setUserName(intent.getStringExtra("username"));
        //userModel.setPhone(intent.getStringExtra("phone"));
        userModel.setUserId(intent.getStringExtra("userId"));
        userModel.setUserAge(intent.getStringExtra("userAge"));
        //userModel.setFcmToken(intent.getStringExtra("fcmToken"));
        return userModel;
    }

    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView) {
        if (context == null) {
            Log.e("AndroidUtil", "Context is null");
        }
        if (imageView == null) {
            Log.e("AndroidUtil", "ImageView is null");
        }
        if (context != null && imageView != null) {
            Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
        }
    }


}
