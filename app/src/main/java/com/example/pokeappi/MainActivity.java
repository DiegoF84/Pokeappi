package com.example.pokeappi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;


import java.util.List;

import models.Pokemon;
import models.ListPokemonReponse;
import models.RetrofitSingleton;
import pokeapi.PokemonService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private PokemonListAdapter pokemonListAdapter;

    private int offset;

    private boolean load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        pokemonListAdapter = new PokemonListAdapter(this);
        recyclerView.setAdapter(pokemonListAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (load) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            load = false;
                            offset += 20;
                            getPokemons(offset);
                        }
                    }
                }
            }
        });


        retrofit = RetrofitSingleton.getRetrofit();
        load = true;
        offset = 0;
        getPokemons(offset);
    }

    private void getPokemons(int offset){

        PokemonService apiPokemon = retrofit.create(PokemonService.class);
        Call<ListPokemonReponse> call = apiPokemon.getPokemons(20,offset);

        call.enqueue(new Callback<ListPokemonReponse>() {
            @Override
            public void onResponse(Call<ListPokemonReponse> call, Response<ListPokemonReponse> response) {
                load = true;
                if(!response.isSuccessful()){
                    Log.e(TAG, " onResponse: "+response.errorBody());
                    return;
                }
                ListPokemonReponse poke = response.body();
                List<Pokemon> list = poke.getResults();

                pokemonListAdapter.addListPokemons(list);
            }

            @Override
            public void onFailure(Call<ListPokemonReponse> call, Throwable t) {
                load = true;
                Log.e(TAG, " onFailure: "+t.getMessage());
            }
        });

    }
}