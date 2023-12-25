package com.example.hellfire.Models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// [START rtdb_user_class]
@IgnoreExtraProperties
public class User implements Serializable {

    public String username;
    public String email;
    public String user_yearOfBirth;
    public String user_monthOfBirth;
    public String user_dateOfBirth;
    public String user_gender;
    public String i_want_see;
    public String here_for;
    public Boolean friends;
    public Boolean concert_buddies;
    String user_bio;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String user_yearOfBirth, String user_monthOfBirth, String user_dateOfBirth, String user_gender) {
        this.username = username;
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
    public User(String username, String email, String user_yearOfBirth, String user_monthOfBirth, String user_dateOfBirth,
                String user_gender, String i_want_see, String here_for, Boolean friends, Boolean concert_buddies, String user_bio) {
        this(username, email, user_yearOfBirth, user_monthOfBirth, user_dateOfBirth, user_gender);

        this.i_want_see = i_want_see;
        this.here_for = here_for;
        this.friends = friends;
        this.concert_buddies = concert_buddies;
        this.user_bio = user_bio;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
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
        }

        // Add other fields as needed

        return result;
    }
    // public User(String i_want_see, String here_for, Boolean friends, Boolean concert_buddies) {

    //     this.i_want_see = i_want_see;
    //     this.here_for = here_for;
    //     this.friends = friends;
    //     this.concert_buddies = concert_buddies;

    // }
}