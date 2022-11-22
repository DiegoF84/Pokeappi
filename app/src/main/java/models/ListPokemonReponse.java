package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListPokemonReponse {
    @SerializedName("results")
    @Expose
    private List<Pokemon> results = null;

    public List<Pokemon> getResults() {
        return results;
    }

    public void setResults(List<Pokemon> results) {
        this.results = results;
    }
}
