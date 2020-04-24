package com.example.tutorialspoint7.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tutorialspoint7.finalproject.ui.login.LogIn;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class home extends AppCompatActivity {
    TextView mTextViewResult;
    ImageView imageView;
    EditText movie_title;
    EditText movie_year;
    String hold_title;
    String hold_year;
    Button getMovie;
    String movieid;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageView = findViewById(R.id.imageView2);
        mTextViewResult = findViewById(R.id.textView5);
        movie_title = findViewById(R.id.name);
        movie_year = findViewById(R.id.editText);
        getMovie = findViewById(R.id.button3);
        mQueue = Volley.newRequestQueue(this);


        getMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hold_title = movie_title.getText().toString();
                hold_year = movie_year.getText().toString();
                jsonParse();
            }
        });

    }
    //http://www.omdbapi.com/?t=galaxy&apikey=fe4f1194
    private void jsonParse() { String url = "https://www.omdbapi.com/?t=" + hold_title + "&y=" + hold_year + "&apikey=fe4f1194";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray weather = null;
                        String movie_tit = null;
                        String poster = null;

                        try {
                           // weather = response.getJSONArray("Ratings");
                           // for (int i = 0; i < weather.length(); i++)
                          //  {
                          //      hold = weather.getJSONObject(i).getString("Source");
                          // }
                            //clouds = response.getJSONObject("Ratings").getString("Source");
                            movie_tit = response.getString("Title");
                            poster = response.getString("Poster");
                            movieid = response.getString("imdbID");
                            //Picasso allows for hassle-free image loading in your application—often in one line of code!
                            Picasso.get().load(poster).into(imageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mTextViewResult.setText("Movie Titlwe: " + movie_tit);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
