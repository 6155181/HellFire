package com.example.hellfire.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellfire.DetailEvent;
import com.example.hellfire.MainActivity;
import com.example.hellfire.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<CardItem> cardItems;
    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference favoritesRef;


    public CardAdapter(List<CardItem> cardItems) {
        this.cardItems = cardItems;
    }
    public CardAdapter(Context context, List<CardItem> cardItems, DatabaseReference favoritesRef) {
        this.context = context;
        this.cardItems = cardItems;
        this.mAuth = FirebaseAuth.getInstance();
        this.favoritesRef = favoritesRef;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem currentItem = cardItems.get(position);
        Picasso.get().load(currentItem.getImageUrl()).into(holder.headerImage);
        holder.artistNameTextView.setText(currentItem.getArtistName());
        holder.localDate.setText(currentItem.getLocalDate());
        holder.city.setText(currentItem.getCityName());

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String eventId = currentItem.getId();
            favoritesRef.child(currentUser.getUid()).child(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        holder.iconAddFavorite.setColorFilter(ContextCompat.getColor(context, R.color.my_red));
                        currentItem.setFavorite(true);
                    } else {
                        holder.iconAddFavorite.setColorFilter(ContextCompat.getColor(context, R.color.my_gray));
                        currentItem.setFavorite(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors.
                }
            });
        } else {
            holder.iconAddFavorite.setColorFilter(ContextCompat.getColor(context, R.color.my_gray));
        }

        holder.itemView.setOnClickListener(v -> {
            FirebaseUser currentUser1 = mAuth.getCurrentUser();
            if (currentUser1 != null) {
                Intent intent = new Intent(context, DetailEvent.class);
                intent.putExtra("artistName", currentItem.getArtistName());
                intent.putExtra("imageUrl", currentItem.getImageUrl());
                intent.putExtra("localDate", currentItem.getLocalDate());
                intent.putExtra("city", currentItem.getCityName());
                intent.putExtra("genre", currentItem.getGenre());
                intent.putExtra("subGenre", currentItem.getSubGenre());
                intent.putExtra("id", currentItem.getId());
                intent.putExtra("minPrice", currentItem.getMinPrice());
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView headerImage;
        public TextView artistNameTextView;
        public TextView localDate;
        public TextView city;
        public ImageButton iconAddFavorite;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            headerImage = itemView.findViewById(R.id.header_image);
            artistNameTextView = itemView.findViewById(R.id.artist_name);
            localDate = itemView.findViewById(R.id.date);
            city = itemView.findViewById(R.id.city);
            iconAddFavorite = itemView.findViewById(R.id.favorite_icon);
        }
    }


}




