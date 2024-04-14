package com.example.hellfire.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hellfire.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> maintitle;
    private final ArrayList<String> subtitle;
    private final ArrayList<String> imgUrls; // Store image URLs

    public MyListAdapter(Activity context, ArrayList<String> maintitle, ArrayList<String> subtitle, ArrayList<String> imgUrls) {
        super(context, R.layout.list_item, maintitle);
        this.context = context;
        this.maintitle = maintitle;
        this.subtitle = subtitle;
        this.imgUrls = imgUrls;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);

        TextView titleText = rowView.findViewById(R.id.textViewName);
        ImageView imageView = rowView.findViewById(R.id.imageView);
        TextView subtitleText = rowView.findViewById(R.id.textViewDate);

        titleText.setText(maintitle.get(position));
        if (position < imgUrls.size()) { // Ensure position is within imgUrls array bounds
            // Load image using Picasso
            Picasso.get().load(imgUrls.get(position)).into(imageView);
        }
        subtitleText.setText(subtitle.get(position));

        return rowView;
    }
}