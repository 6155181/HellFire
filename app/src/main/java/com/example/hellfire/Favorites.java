package com.example.hellfire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hellfire.Models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private UserModel userModel;
    private DatabaseReference mDatabase;
    private ArrayList<String> selectedButtons = new ArrayList<>();
    //private  String hard;
    Button btn_hard, btn_rap, btn_house, btn_pop, btn_i_rock, btn_cl_m, btn_l_m, btn_blues, btn_folk,
            btn_disco, btn_amb, btn_pu_r,btn_trash, btn_cl_r, btn_techno, btn_soul, btn_jazz, btn_reggae,
            btn_el_mus, btn_hop, btn_got_r, btn_jungle, btn_chans, btn_trance, btn_alter, btn_j_f,
            btn_bl_r, btn_ethn_m, btn_country, btn_ind, btn_dub, btn_duds, btn_a_h, btn_garage, btn_e_funk,
            btn_trip_h, btn_grunge, btn_bluegr, btn_indust, btn_rap_m, btn_celtic, btn_experim, btn_new_w,
            btn_post_r, btn_neo_s, btn_glam_r;
    //Button btn_folk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        userModel = (UserModel) getIntent().getSerializableExtra("userModel");

        btn_hard = findViewById(R.id.btn_hard);
        btn_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_hard.setSelected(!btn_hard.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_hard.isSelected()){
                    //hard = buttonText;
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_rap = findViewById(R.id.btn_rap);
        btn_rap.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            btn_rap.setSelected(!btn_rap.isSelected());
            String buttonText = ((Button) view).getText().toString();
            if (btn_rap.isSelected()){
                selectedButtons.add(buttonText);
            }
            else{
                selectedButtons.remove(buttonText);
            }
        }
    });

        btn_house = findViewById(R.id.btn_house);
        btn_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_house.setSelected(!btn_house.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_house.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_pop = findViewById(R.id.btn_pop);
        btn_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pop.setSelected(!btn_pop.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_pop.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_i_rock = findViewById(R.id.btn_i_rock);
        btn_i_rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_i_rock.setSelected(!btn_i_rock.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_i_rock.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_cl_m = findViewById(R.id.btn_cl_music);
        btn_cl_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_cl_m.setSelected(!btn_cl_m.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_cl_m.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_l_m = findViewById(R.id.btn_latin);
        btn_l_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_l_m.setSelected(!btn_l_m.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_l_m.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_blues = findViewById(R.id.btn_blues);
        btn_blues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_blues.setSelected(!btn_blues.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_blues.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_folk = findViewById(R.id.btn_folk);
        btn_folk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_folk.setSelected(!btn_folk.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_folk.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_disco = findViewById(R.id.btn_disco);
        btn_disco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_disco.setSelected(!btn_disco.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_disco.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_amb = findViewById(R.id.btn_ambi);
        btn_amb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_amb.setSelected(!btn_amb.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_amb.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_pu_r = findViewById(R.id.btn_pu_rock);
        btn_pu_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pu_r.setSelected(!btn_pu_r.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_pu_r.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_trash = findViewById(R.id.btn_trash);
        btn_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_trash.setSelected(!btn_trash.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_trash.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_cl_r = findViewById(R.id.btn_cl_rock);
        btn_cl_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_cl_r.setSelected(!btn_cl_r.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_cl_r.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_techno = findViewById(R.id.btn_tech);
        btn_techno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_techno.setSelected(!btn_techno.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_techno.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_soul = findViewById(R.id.btn_soul);
        btn_soul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_soul.setSelected(!btn_soul.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_soul
                        .isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_jazz = findViewById(R.id.btn_jazz);
        btn_jazz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_jazz.setSelected(!btn_jazz.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_jazz
                        .isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_reggae = findViewById(R.id.btn_regg);
        btn_reggae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_reggae.setSelected(!btn_reggae.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_reggae.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_el_mus = findViewById(R.id.btn_electro);
        btn_el_mus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_el_mus.setSelected(!btn_el_mus.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_el_mus.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_hop = findViewById(R.id.btn_hip);
        btn_hop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_hop.setSelected(!btn_hop.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_hop.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_got_r = findViewById(R.id.btn_gothic);
        btn_got_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_got_r.setSelected(!btn_got_r.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_got_r.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_jungle = findViewById(R.id.btn_jungle);
        btn_jungle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_jungle.setSelected(!btn_jungle.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_jungle.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });
        btn_chans = findViewById(R.id.btn_chanson);
        btn_chans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_chans.setSelected(!btn_chans.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_chans.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_trance = findViewById(R.id.btn_trance);
        btn_trance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_trance.setSelected(!btn_trance.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_trance.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_alter = findViewById(R.id.btn_alter);
        btn_alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_alter.setSelected(!btn_trance.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_alter.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_j_f = findViewById(R.id.btn_j_funk);
        btn_j_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_j_f.setSelected(!btn_j_f.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_j_f.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_bl_r = findViewById(R.id.btn_bl_rock);
        btn_bl_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_bl_r.setSelected(!btn_bl_r.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_bl_r.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_ethn_m = findViewById(R.id.btn_ethnic);
        btn_ethn_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_ethn_m.setSelected(!btn_ethn_m.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_ethn_m.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_country = findViewById(R.id.btn_country);
        btn_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_country.setSelected(!btn_country.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_country.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_ind = findViewById(R.id.btn_indie);
        btn_ind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_ind.setSelected(!btn_ind.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_ind.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_dub = findViewById(R.id.btn_dub);
        btn_dub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_dub.setSelected(!btn_dub.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_dub.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_duds = findViewById(R.id.btn_dubstep);
        btn_duds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_duds.setSelected(!btn_duds.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_duds.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_a_h = findViewById(R.id.btn_a_house);
        btn_a_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_a_h.setSelected(!btn_a_h.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_a_h.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_garage = findViewById(R.id.btn_garage);
        btn_garage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_garage.setSelected(!btn_garage.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_garage.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_e_funk = findViewById(R.id.btn_el_funk);
        btn_e_funk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_e_funk.setSelected(!btn_e_funk.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_e_funk.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_trip_h = findViewById(R.id.btn_trip);
        btn_trip_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_trip_h.setSelected(!btn_trip_h.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_trip_h.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_grunge = findViewById(R.id.btn_grunge1);
        btn_grunge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_grunge.setSelected(!btn_grunge.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_grunge.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_bluegr = findViewById(R.id.btn_bluegrass);
        btn_bluegr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_bluegr.setSelected(!btn_bluegr.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_bluegr.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_indust = findViewById(R.id.btn_industr);
        btn_indust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_indust.setSelected(!btn_indust.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_indust.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_rap_m = findViewById(R.id.btn_rap_met);
        btn_rap_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_rap_m.setSelected(!btn_rap_m.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_rap_m.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_celtic = findViewById(R.id.btn_celtic);
        btn_celtic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_celtic.setSelected(!btn_celtic.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_celtic.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_experim = findViewById(R.id.btn_experim);
        btn_experim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_experim.setSelected(!btn_experim.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_experim.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_new_w = findViewById(R.id.btn_nwave);
        btn_new_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_new_w.setSelected(!btn_new_w.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_new_w.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_post_r = findViewById(R.id.btn_post);
        btn_post_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_post_r.setSelected(!btn_post_r.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_post_r.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_neo_s = findViewById(R.id.btn_neo);
        btn_neo_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_neo_s.setSelected(!btn_neo_s.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_neo_s.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });

        btn_glam_r = findViewById(R.id.btn_glam);
        btn_glam_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_glam_r.setSelected(!btn_glam_r.isSelected());
                String buttonText = ((Button) view).getText().toString();
                if (btn_glam_r.isSelected()){
                    selectedButtons.add(buttonText);
                }
                else{
                    selectedButtons.remove(buttonText);
                }
            }
        });
}

    public void shortBio(View view) {
        Intent intent = new Intent(Favorites.this, ShortBio.class);
        startActivity(intent);
        intent.putExtra("userModel", userModel);

        finish();
    }

    public void toAddPicture(View view) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String userId = mUser.getUid();

        mDatabase.child("users").child(userId).child("favorites").setValue(selectedButtons);

        Intent intent = new Intent(Favorites.this, AddPicture.class);
        startActivity(intent);
        //Toast.makeText(this, "YESS"+userModel.getUserBio(), Toast.LENGTH_SHORT).show();
        intent.putExtra("userModel", userModel);
        finish();
    }
}