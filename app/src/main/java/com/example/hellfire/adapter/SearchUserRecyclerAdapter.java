package com.example.hellfire.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hellfire.ChatActivity;
import com.example.hellfire.Models.UserModel;
import com.example.hellfire.R;
import com.example.hellfire.utils.AndroidUtil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SearchUserRecyclerAdapter extends FirebaseRecyclerAdapter<UserModel, SearchUserRecyclerAdapter.UserModelViewHolder> {

    Context context;

    public SearchUserRecyclerAdapter(@NonNull FirebaseRecyclerOptions<UserModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserModelViewHolder holder, int position, @NonNull UserModel model) {
        holder.usernameText.setText(model.getUserName());
        holder.userAge.setText(model.getUserAge());
///////////////////////////////////////////////////////////////
        String userId = model.getUserId();   //  <----------------------------------------------------------

        DatabaseReference imageUrlRef = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(userId)
                .child("imageUrls")
                .child("0");
        imageUrlRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String imageUrl = snapshot.getValue(String.class);
                    Log.d("SearchUserRecyclerAdapter", "Image URL: " + imageUrl);
                    Glide.with(context).load(imageUrl).into(holder.profilePic);
                }
                else{
                    String imageUrl = "https://firebasestorage.googleapis.com/v0/b/hellfire-fc145.appspot.com/o/" +
                            "images%2Fdefault%2Fsmiley.png?alt=media&token=6005a7c7-d8a6-4164-9ec0-a882cd14b449";
                    Glide.with(context).load(imageUrl).into(holder.profilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SearchUserRecyclerAdapter", "Error reading image URL from database", error.toException());
            }
        });


        holder.itemView.setOnClickListener(v -> {
            //navigate to chat activity
            Intent intent = new Intent(context, ChatActivity.class);
            AndroidUtil.passUserModelAsIntent(intent,model);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public UserModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Toast.makeText(context, "username"+context, Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(context).inflate(R.layout.search_user_recycler_row, parent, false);
        return new UserModelViewHolder(view);
    }

    class UserModelViewHolder extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView userAge;   // -------------------------------//
        ImageView profilePic;

        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            userAge = itemView.findViewById(R.id.user_age);
            profilePic = itemView.findViewById(R.id.profile_pic_iv);
        }
    }
}
