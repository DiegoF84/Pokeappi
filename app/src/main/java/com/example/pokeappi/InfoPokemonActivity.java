package com.example.pokeappi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import models.PokemonInfo;
import models.RetrofitSingleton;
import pokeapi.PokemonService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoPokemonActivity extends AppCompatActivity {
    private static final String TAG = "POKEDEX";

    private Retrofit retrofit;

    private ImageView image;
    private TextView name;
    private TextView height;
    private TextView weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pokemon);

        image = (ImageView) findViewById(R.id.item_info_pokemon_icon);
        name = (TextView) findViewById(R.id.item_info_pokemon_name);
        height = (TextView) findViewById(R.id.item_info_pokemon_height);
        weight = (TextView) findViewById(R.id.item_info_pokemon_weight);

        retrofit = RetrofitSingleton.getRetrofit();

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            String number = extra.getString("number");
            getPokeInfo(Integer.parseInt(number),this);
        }
    }

    public void getPokeInfo(int id, Context context){
        PokemonService apiPokemon = retrofit.create(PokemonService.class);
        Call<PokemonInfo> call = apiPokemon.getPokemon(id);

        call.enqueue(new Callback<PokemonInfo>() {
            @Override
            public void onResponse(Call<PokemonInfo> call, Response<PokemonInfo> response) {
                if(!response.isSuccessful()){
                    Log.e(TAG, " onResponse: "+response.errorBody());
                    return;
                }

                PokemonInfo p = response.body();

                Glide.with(context)
                        .load(p.sprites.getFront_default())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image);
                name.setText(p.getName());
                height.setText(String.valueOf(p.getHeight()));
                weight.setText(String.valueOf(p.getWeight()));
            }

            @Override
            public void onFailure(Call<PokemonInfo> call, Throwable t) {
                Log.e(TAG, " onFailure: "+t.getMessage());
            }
        });
    }
}