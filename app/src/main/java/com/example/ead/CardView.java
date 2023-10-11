package com.example.ead;

import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CardView extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        // Initialize RecyclerView and its adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceItemDecoration(2));
        movieAdapter = new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String apiKey = "dc986974"; // Replace with your actual OMDB API key
                    String movieTitle = "Avengers"; // Specify the movie title you want to search for

                    // Make an API request to get movie data in JSON format
                    String apiUrl = "https://www.omdbapi.com/?s=" + movieTitle + "&apikey=" + apiKey;
                    URL url = new URL(apiUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        // Parse the JSON response and extract movie data
                        JSONObject response = new JSONObject(result.toString());
                        JSONArray searchResults = response.getJSONArray("Search");

                        final List<Movie> movies = new ArrayList<>();

                        for (int i = 0; i < searchResults.length(); i++) {
                            JSONObject movieData = searchResults.getJSONObject(i);
                            String title = movieData.getString("Title");
                            String imageUrl = movieData.getString("Poster");
                            movies.add(new Movie(title, imageUrl));
                        }

                        // Update the UI components on the main thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                movieAdapter.setMovies(movies);
                            }
                        });
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
