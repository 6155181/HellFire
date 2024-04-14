package com.example.hellfire;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hellfire.adapter.MyListAdapter;
import com.example.hellfire.api.ApiManager;
import com.example.hellfire.api.ApiService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpcomingShowsList extends AppCompatActivity {

    private EditText keywordEditText;
    private TextView eventTextView;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_upcoming_shows_list);

        // Initialize views
        keywordEditText = findViewById(R.id.keywordEditText);
        //eventTextView = findViewById(R.id.eventInfoTextView);

        Button searchButton = findViewById(R.id.searchButton);

        // Initialize Retrofit and ApiService
        Retrofit retrofit = ApiManager.getClient();
        apiService = retrofit.create(ApiService.class);



        // Set click listener for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = keywordEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(keyword)) {
                    // Call the API with the updated keyword
                    makeApiCall(keyword);
                } else {
                    Toast.makeText(UpcomingShowsList.this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Set your API key and keyword
    String apiKey = "ovSCFnu0HyAY2YuGwRki1A4X7y3GW4cP";

    // Make the API call
    private void makeApiCall(String keyword) {
        Call<JsonObject> call = apiService.getEvents(keyword, apiKey);
        ListView upcomingShowsListView = findViewById(R.id.upcomingShowsListView);

        ArrayList<String> maintitle = new ArrayList<>();
        ArrayList<String> subtitle = new ArrayList<>();
        ArrayList<String> imgUrls = new ArrayList<>(); // Change the ArrayList name to imgUrls
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Response", response.toString());
                    JsonObject data = response.body();
                    if (data != null && data.has("_embedded")) {
                        JsonObject embedded = data.getAsJsonObject("_embedded");
                        if (embedded.has("events")) {
                            JsonArray eventsArray = embedded.getAsJsonArray("events");
                            StringBuilder eventInfo = new StringBuilder();
                            for (JsonElement eventElement : eventsArray) {
                                JsonObject eventObject = eventElement.getAsJsonObject();
                                String eventName = eventObject.get("name").getAsString();
                                eventInfo.append(eventName).append("\n");
                                maintitle.add(eventName);

                                String startDate = eventObject.getAsJsonObject("dates").getAsJsonObject("start").get("localDate").getAsString();
                                eventInfo.append(startDate).append("\n");
                                subtitle.add(startDate);

                                String imageUrl = eventObject.getAsJsonArray("images").get(0).getAsJsonObject().get("url").getAsString();

                                imgUrls.add(imageUrl);

                            }
                            if (!maintitle.isEmpty()) {
                                MyListAdapter adapter = new MyListAdapter(UpcomingShowsList.this, maintitle, subtitle, imgUrls);
                                upcomingShowsListView.setAdapter(adapter);
                            } else {

                            }
                        }
                    }
                } else {
                    System.out.println("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
