package com.example.hellfire;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hellfire.api.ApiManager;
import com.example.hellfire.api.ApiService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UpcomingShow extends AppCompatActivity {

    private EditText keywordEditText;
    private TextView eventInfoTextView; // Assuming you have a TextView to display event info



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_show);

        // Initialize views
        keywordEditText = findViewById(R.id.keywordEditText);

        Button searchButton = findViewById(R.id.searchButton);

        // Set click listener for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = keywordEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(keyword)) {
                    // Call the API with the updated keyword
                    makeApiCall(keyword);
                } else {
                    Toast.makeText(UpcomingShow.this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Set your API key and keyword
    String apiKey = "ovSCFnu0HyAY2YuGwRki1A4X7y3GW4cP";
    String keyword = "Madonna";

    //  String url = "https://app.ticketmaster.com/discovery/v2/events.json?keyword="+keyword+"&startDateTime=2024-04-10T14:00:00Z&endDateTime=2024-04-15T14:00:00Z&apikey="+apiKey;

    // Make the API call
    private void makeApiCall(String keyword) {
        // Initialize Retrofit and ApiService
        Retrofit retrofit = ApiManager.getClient();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<JsonObject> call = apiService.getEvents(keyword, apiKey);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
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
                            }

                            TextView eventTextView = findViewById(R.id.eventInfoTextView);

                            eventTextView.setText(null);
                            eventTextView.setText(eventInfo.toString());
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









    public void logOut (View view){
        //mAuth.signOut();
        Toast.makeText(this, "Good Buy", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpcomingShow.this, LogIn.class);
        startActivity(intent);
        finish();
    }
}