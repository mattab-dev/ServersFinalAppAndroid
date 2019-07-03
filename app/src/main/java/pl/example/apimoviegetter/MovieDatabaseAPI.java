package pl.example.apimoviegetter;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDatabaseAPI {
    String HOME = "https://hidden-oasis-37690.herokuapp.com/";

    @GET("movie/{id}")
    Call<Movie>getMovie(@Path("id") String id);

    @GET("movie/keyword/{title}")
    Call<List<Movie>> getMovieByTitle(@Path("title") String title);

}
