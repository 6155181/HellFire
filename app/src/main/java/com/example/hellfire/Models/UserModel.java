package com.example.hellfire.Models;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// [START rtdb_user_class]
@IgnoreExtraProperties
public class UserModel implements Serializable {

    private String username;
    private String userId;
    private Timestamp createdTimestamp;
    private String email;
    private String user_yearOfBirth;
    private String user_monthOfBirth;
    private String user_dateOfBirth;
    private String user_gender;
    private String i_want_see;
    private String here_for;
    private Boolean friends;
    private Boolean concert_buddies;
    private String user_bio;


    public UserModel() {
        // Default constructor required for calls to DataSnapshot.getValue(UserModel.class)
    }

    public UserModel(String username, Timestamp createdTimestamp, String userId, String email, String user_yearOfBirth, String user_monthOfBirth, String user_dateOfBirth, String user_gender) {
        this.username = username;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
        this.email = email;
        this.user_yearOfBirth = user_yearOfBirth;
        this.user_monthOfBirth = user_monthOfBirth;
        this.user_dateOfBirth = user_dateOfBirth;
        this.user_gender = user_gender;
        this.i_want_see = null;
        this.here_for = null;
        this.friends = null;
        this.concert_buddies = null;
        this.user_bio = null;
    }
    public UserModel(String username, Timestamp createdTimestamp, String userId, String email, String user_yearOfBirth, String user_monthOfBirth, String user_dateOfBirth,
                     String user_gender, String i_want_see, String here_for, Boolean friends, Boolean concert_buddies, String user_bio) {
        this(username, createdTimestamp, userId ,email, user_yearOfBirth, user_monthOfBirth, user_dateOfBirth, user_gender);

        this.i_want_see = i_want_see;
        this.here_for = here_for;
        this.friends = friends;
        this.concert_buddies = concert_buddies;
        this.user_bio = user_bio;
    }
    //public UserModel(String username, Timestamp createdTimestamp, String userId, String email, String user_yearOfBirth, String user_monthOfBirth, String user_dateOfBirth,
                    // String user_gender, String i_want_see, String here_for, Boolean friends, Boolean concert_buddies, String user_bio) {
        //this(username, createdTimestamp, userId ,email, user_yearOfBirth, user_monthOfBirth, user_dateOfBirth, user_gender, i_want_see, here_for, friends, concert_buddies);
        //this.user_bio = user_bio;
    //}


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("username", username);
        result.put("email", email);
        result.put("user_yearOfBirth", user_yearOfBirth);
        result.put("user_monthOfBirth", user_monthOfBirth);
        result.put("user_dateOfBirth", user_dateOfBirth);
        result.put("user_gender", user_gender);
        result.put("user_bio", user_bio);

        // Проверяем, не null ли поле перед добавлением в Map
        if (i_want_see != null) {
            result.put("i_want_see", i_want_see);
        }

        if (here_for != null) {
            result.put("here_for", here_for);
        }

        if (friends != null) {
            result.put("friends", friends);
        }

        if (concert_buddies != null) {
            result.put("concert_buddies", concert_buddies);
        }
        if (user_bio != null) {
            result.put("user_bio", user_bio);
            //setUser_bio(user_bio);
        }

        return result;
    }
    public String getUserAge() {
        // Combine year, month, and date of birth to form a complete date string
        String birthdateString = getUser_yearOfBirth() + "-" + getUser_monthOfBirth() + "-" + getUser_dateOfBirth();

        try {
            // Parse the birthdate string to a Date object
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthdate = dateFormat.parse(birthdateString);

            // Calculate the age
            int age = calculateAge(birthdate);

            // Convert age to String and return

            return String.valueOf(age);
        } catch (ParseException e) {
            e.printStackTrace();
            String age = "20";
            return age; // Handle the exception according to your requirements
        }
    }

    private int calculateAge(Date birthdate) {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthdate);

        Calendar currentCalendar = Calendar.getInstance();

        int age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        // Adjust age if the birthday hasn't occurred yet this year
        if (currentCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    public String getUserId() {
        return userId;
    }
    public String getI_want_see() {return i_want_see;};
    public void setI_want_see(String i_want_see) { this.i_want_see = i_want_see;}
    public String getHere_for() {return here_for;};
    public void setHere_for(String here_for) { this.here_for = here_for;}
    public void setFriends (Boolean friends) { this.friends = friends;}
    public void setConcert_buddies (Boolean friends) { this.concert_buddies = concert_buddies;}

    public void setUser_bio(String user_bio) { this.user_bio = user_bio;}
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getUser_yearOfBirth() { return  user_yearOfBirth;}
    public String getUser_dateOfBirth() { return user_dateOfBirth; }
    public String getUser_monthOfBirth() { return user_monthOfBirth; }
    public String getUser_gender() { return user_gender;}


    public String getUserBio() {
        if(user_bio == null)
            return "oppss";
        else
            return  user_bio;}
    public void setUserBio(String user_bio) {this.user_bio = user_bio;}
    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {this.createdTimestamp = createdTimestamp;}
    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    public void setUserAge(String userAge) {

    }
    // public UserModel(String i_want_see, String here_for, Boolean friends, Boolean concert_buddies) {

    //     this.i_want_see = i_want_see;
    //     this.here_for = here_for;
    //     this.friends = friends;
    //     this.concert_buddies = concert_buddies;

    // }
}