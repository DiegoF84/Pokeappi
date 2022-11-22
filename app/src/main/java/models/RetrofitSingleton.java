package models;


import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    public static retrofit2.Retrofit retrofit = null;

    public static retrofit2.Retrofit getRetrofit() {
        if(retrofit == null){
            retrofit = new retrofit2.Retrofit
                    .Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
