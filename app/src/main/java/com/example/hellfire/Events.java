package com.example.hellfire;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellfire.adapter.CardAdapter;
import com.example.hellfire.api.ApiManager;
import com.example.hellfire.api.ApiService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Events extends AppCompatActivity {

    private EditText keywordEditText;
    private TextView eventTextView;
    private ApiService apiService;
    private RecyclerView recyclerView; // Объявляем recyclerView как поле класса

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        recyclerView = findViewById(R.id.recyclerView); // Инициализируем recyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // Инициализация Retrofit и ApiService
        Retrofit retrofit = ApiManager.getClient();
        apiService = retrofit.create(ApiService.class);

        // Вызов метода для получения списка музыкальных событий
        getMusicEvents();

    }

    String apiKey = "ovSCFnu0HyAY2YuGwRki1A4X7y3GW4cP";

    private void getMusicEvents() {
        Call<JsonObject> call = apiService.getMusicEvents("music", apiKey);  // KZFzniwnSyZfZ7v7n1 - Miscellaneous;

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Response", response.toString());
                    JsonObject data = response.body();
                    if (data != null && data.has("_embedded")) {
                        JsonObject embedded = data.getAsJsonObject("_embedded");
                        //Log.d("embedded", embedded.toString());
                        if ((embedded.has("attractions")) ) {
                            JsonArray attractionsArray = embedded.getAsJsonArray("attractions");
                            //JsonArray eventsArray = embedded.getAsJsonArray("events");
                            List<CardItem> cardItems = new ArrayList<>(); // Используем List<CardItem>, так как CardItem является вложенным статическим классом
                            Set<String> uniqueEvents = new HashSet<>();
                            for (JsonElement attractionElement : attractionsArray) {
                                JsonObject attractionObject = attractionElement.getAsJsonObject();

                                String name = attractionObject.get("name").getAsString();
                                String attractionId = attractionObject.get("id").getAsString();

                                if (!uniqueEvents.contains(name)) {
                                    uniqueEvents.add(name);
                                    String imageUrl = attractionObject.getAsJsonArray("images").get(0).getAsJsonObject().get("url").getAsString();
                                    // Создание экземпляра CardItem и добавление его в список
                                    CardItem cardItem = new CardItem(name, R.drawable.placeholder_image, imageUrl);
                                    cardItems.add(cardItem);
                                }
                            }

                            updateRecyclerView(cardItems);
                        }

                    }
                } else {
                    Log.e("UpcomingShowsList", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("UpcomingShowsList", "Failed to fetch events", t);
            }
        });
    }

    private void getMusicEvents1() {
        Call<JsonObject> call = apiService.getMusicEvents1("music", apiKey);  // KZFzniwnSyZfZ7v7n1 - Miscellaneous;

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Response", response.toString());
                    JsonObject data = response.body();
                    if (data != null && data.has("_embedded")) {
                        JsonObject embedded = data.getAsJsonObject("_embedded");
                        Log.d("embedded", embedded.toString());
                        if ((embedded.has("events")) ) {
                            JsonArray eventsArray = embedded.getAsJsonArray("events");
                            //JsonArray eventsArray = embedded.getAsJsonArray("events");
                            List<CardItem> cardItems = new ArrayList<>(); // Используем List<CardItem>, так как CardItem является вложенным статическим классом
                            Set<String> uniqueEvents = new HashSet<>();
                            for (JsonElement eventElement : eventsArray) {
                                JsonObject eventObject = eventElement.getAsJsonObject();

                                String name = eventObject.get("name").getAsString();
                                String attractionId = eventObject.get("id").getAsString();

                                if (!uniqueEvents.contains(name)) {
                                    uniqueEvents.add(name);
                                    String imageUrl = eventObject.getAsJsonArray("images").get(0).getAsJsonObject().get("url").getAsString();
                                    // Создание экземпляра CardItem и добавление его в список
                                    CardItem cardItem = new CardItem(name, R.drawable.placeholder_image, imageUrl);
                                    cardItems.add(cardItem);
                                }
                            }

                            updateRecyclerView(cardItems);
                        }

                    }
                } else {
                    Log.e("UpcomingShowsList", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("UpcomingShowsList", "Failed to fetch events", t);
            }
        });
    }

    private void getAllMusicEvents() {
        Call<JsonObject> call = apiService.getAllMusicEvents( apiKey);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Response", response.toString());
                    JsonObject data = response.body();
                    if (data != null && data.has("_embedded")) {
                        JsonObject embedded = data.getAsJsonObject("_embedded");
                        //Log.d("embedded", embedded.toString());
                        if (embedded.has("events")) {    //     /events.json?segmentId=KZFzniwnSyZfZ7v7nJ&ap
                            JsonArray eventsArray = embedded.getAsJsonArray("events");
                            List<CardItem> cardItems = new ArrayList<>(); // Используем List<CardItem>, так как CardItem является вложенным статическим классом
                            Set<String> uniqueEvents = new HashSet<>();
                            for (JsonElement eventElement : eventsArray) {
                                JsonObject eventObject = eventElement.getAsJsonObject();
                                JsonObject segmentsIdObject = eventObject.getAsJsonObject(("segmentId"));
                                String segmentId = segmentsIdObject.get("id").getAsString();
                                if (segmentId.equals("KZFzniwnSyZfZ7v7nJ")) {
                                    String name = eventObject.get("name").getAsString();
                                    String eventId = eventObject.get("id").getAsString();
                                    if (!uniqueEvents.contains(name)) {
                                        uniqueEvents.add(name);
                                        String imageUrl = eventObject.getAsJsonArray("images").get(0).getAsJsonObject().get("url").getAsString();
                                        // Создание экземпляра CardItem и добавление его в список
                                        CardItem cardItem = new CardItem(name, R.drawable.placeholder_image, imageUrl);
                                        cardItems.add(cardItem);
                                    }
                                }
                            }
                            updateRecyclerView(cardItems);
                        }
                    }
                } else {
                    Log.e("UpcomingShowsList", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("UpcomingShowsList", "Failed to fetch events", t);
            }
        });
    }

    private void getEvents() {
        Call<JsonObject> call = apiService.getEvents(apiKey);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("Response", response.toString()); // Выводим содержимое response в журнал
                    JsonObject data = response.body();
                    if (data != null && data.has("_embedded")) {
                        JsonObject embedded = data.getAsJsonObject("_embedded");
                        Log.d("Data", embedded.toString()); // Выводим содержимое response в журнал
                        if (embedded.has("events")) {
                            JsonArray eventsArray = embedded.getAsJsonArray("events");
                            List<CardItem> cardItems = new ArrayList<>();
                            Set<String> uniqueEventNames = new HashSet<>(); // Set для хранения уникальных названий событий
                            for (JsonElement eventElement : eventsArray) {
                                JsonObject eventObject = eventElement.getAsJsonObject();
                                JsonArray classificationsArray = eventObject.getAsJsonArray("classifications");
                                for (JsonElement classificationElement : classificationsArray) {
                                    JsonObject classificationObject = classificationElement.getAsJsonObject();
                                    JsonObject segmentObject = classificationObject.getAsJsonObject("segment");
                                    String segmentName = segmentObject.get("name").getAsString();
                                    if (segmentName.equals("Music")) {

                                        String eventName = eventObject.get("name").getAsString();
                                        Log.d("Music", eventName.toString()); // Выводим содержимое response в журнал
                                        // Проверяем, нет ли уже такого события в списке
                                        if (!uniqueEventNames.contains(eventName)) {
                                            // Добавляем название события в множество для проверки уникальности
                                            uniqueEventNames.add(eventName);

                                            // Получаем URL изображения
                                            String imageUrl = eventObject.getAsJsonArray("images").get(0).getAsJsonObject().get("url").getAsString();

                                            // Создание экземпляра CardItem и добавление его в список
                                            CardItem cardItem = new CardItem(eventName, R.drawable.placeholder_image, imageUrl);
                                            cardItems.add(cardItem);
                                        }

                                    }
                                }
                            }
                            // Обновление RecyclerView с использованием новых данных
                            updateRecyclerView(cardItems);
                        }
                    }
                } else {
                    Log.e("UpcomingShowsList", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("UpcomingShowsList", "Failed to fetch events", t);
            }
        });
    }
    private void updateRecyclerView(List<CardItem> cardItems) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView); // Получение ссылки на ваш RecyclerView
        CardAdapter adapter = new CardAdapter(cardItems);
        recyclerView.setAdapter(adapter);
    }


    // Объявляем статический класс CardItem внутри класса Events
    public class CardItem {
        private String artistName;
        private int headerImage;
        private String imageUrl;

        public CardItem(String artistName, int headerImage, String imageUrl) {
            this.artistName = artistName;
            this.headerImage = headerImage;
            this.imageUrl = imageUrl;
        }

        public String getArtistName() {
            return artistName;
        }

        public int getHeaderImage() {
            return headerImage;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
}

