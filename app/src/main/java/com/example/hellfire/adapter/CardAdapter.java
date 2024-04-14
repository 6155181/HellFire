package com.example.hellfire.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellfire.Events;
import com.example.hellfire.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<Events.CardItem> cardItems;

    public CardAdapter(List<Events.CardItem> cardItems) {
        this.cardItems = cardItems;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Events.CardItem currentItem = cardItems.get(position);
        // Загрузка изображения с помощью Picasso
        Picasso.get().load(currentItem.getImageUrl()).into(holder.header_image);
        holder.artist_nameTextView.setText(currentItem.getArtistName());
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView header_image;
        public TextView artist_nameTextView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            header_image = itemView.findViewById(R.id.header_image);
            artist_nameTextView = itemView.findViewById(R.id.artist_name);
        }
    }
}

