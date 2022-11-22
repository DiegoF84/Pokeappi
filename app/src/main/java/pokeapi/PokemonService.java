package pokeapi;

import models.PokemonInfo;
import models.ListPokemonReponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonService {
    @GET("pokemon")
    Call<ListPokemonReponse> getPokemons(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<PokemonInfo> getPokemon(@Path("id") int id);
}
