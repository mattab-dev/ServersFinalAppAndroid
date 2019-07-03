package pl.example.apimoviegetter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    MovieDatabaseAPI movieAPI;
    EditText keyword;
    MovieList<Movie> moviesList;
    ListView results;
    Context context = this;
    MovieItemAdapter movieItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesList = new MovieList<>();
        Log.d("MovieList on create", "state:  " + moviesList);
        moviesList.movies = new ArrayList<>();
        keyword = findViewById(R.id.keywordProvider);
        results = findViewById(R.id.movieList);
        results.setAdapter(new MovieItemAdapter());

        createMoviesAPI();
    }

    private void createMoviesAPI() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieDatabaseAPI.HOME)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieAPI = retrofit.create(MovieDatabaseAPI.class);
    }

    public void searchForMovies(View button) {
        String title = keyword.getText().toString();
        movieAPI.getMovieByTitle(title).enqueue(moviesCallback);
        Log.d("MovieList search", "state:  " + moviesList);
    }


    Callback<List<Movie>> moviesCallback = new Callback<List<Movie>> () {
        @Override
        public void onResponse(Call<List<Movie>>  call, Response<List<Movie>>  response) {
            if (response.isSuccessful()) {
                // Pobranie danych z odpowiedzi serwera
                Log.d("Response", "Response: " + response.body());
                moviesList.movies = response.body();
                if(moviesList.movies.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No movie found. Please try again", Toast.LENGTH_LONG).show();
                }

                // Odświeżenie widoku listy i informacji o pobranych danych
                ((MovieItemAdapter)results.getAdapter()).notifyDataSetChanged();
            } else {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<List<Movie>>  call, Throwable t) {
            Log.d("MovieList on failure", "state:  " + moviesList);
            t.printStackTrace();
        }
    };



    private class MovieItemAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;

        public MovieItemAdapter() {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            Log.d("MovieList on count", "state:  " + moviesList);
            return moviesList.movies.size();
        }

        @Override
        public Object getItem(int position) {
            return moviesList.movies.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View rowView, ViewGroup parent) {
            if(rowView == null) {
                rowView = layoutInflater.inflate(R.layout.movie_list_item,null);
            }
            TextView movieTitle = rowView.findViewById(R.id.movTitle);
            TextView moviePlot = rowView.findViewById(R.id.movPlot);
            Movie currentMovie = moviesList.movies.get(position);
            movieTitle.setText(currentMovie.getTitle());
            moviePlot.setText(currentMovie.getPlot());

            return rowView;
        }
    }
}
