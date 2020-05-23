package rs.gomex.kolekcijapogledanihfilmova.network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rs.gomex.kolekcijapogledanihfilmova.model.Movie;
import rs.gomex.kolekcijapogledanihfilmova.model.Search;

public interface EndPoint {

    @GET("/")
    Call<Search> getSearchResult(@QueryMap Map<String, String> options);

    @GET("/")
    Call<Movie> getMovieDetail(@QueryMap Map<String, String> options);
}
