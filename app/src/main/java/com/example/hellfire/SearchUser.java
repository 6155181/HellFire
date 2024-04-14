package com.example.hellfire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellfire.Models.UserModel;
import com.example.hellfire.adapter.SearchUserRecyclerAdapter;
import com.example.hellfire.utils.FirebaseUtil;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


public class SearchUser extends AppCompatActivity {

    EditText searchInput;
    ImageButton searchButton;
    RecyclerView recyclerView;
    SearchUserRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        searchInput = findViewById(R.id.input);
        searchButton = findViewById(R.id.search_btn);
        recyclerView = findViewById(R.id.search_user_recycler_view);

        searchInput.requestFocus();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchInput.getText().toString();
                if (search == null || search.isEmpty() || search.length() < 2) {
                    searchInput.setError("Invalid Username");
                    return;
                }
                setupSearchRecyclerView(search);
                //Toast.makeText(SearchUser.this, "else", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setupSearchRecyclerView(String search) {
        DatabaseReference usersRef = FirebaseUtil.allUserDatabaseReference();
        Query query = usersRef.orderByChild("username")
                .startAt(search)
                .endAt(search + "\uf8ff");

        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(query, UserModel.class).build();

        adapter = new SearchUserRecyclerAdapter(options, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null)
            adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null)
            adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null)
            adapter.startListening();
    }

    public void toProfile(View view) {
        Intent intent = new Intent(SearchUser.this, Profile.class);
        startActivity(intent);
        finish();
    }
}