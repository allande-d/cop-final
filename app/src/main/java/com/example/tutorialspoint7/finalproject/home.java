package com.example.tutorialspoint7.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tutorialspoint7.finalproject.RegisterPage;
import com.example.tutorialspoint7.finalproject.ui.login.LogIn;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class home extends AppCompatActivity {
    TextView mTextViewResult;
    ImageView imageView;
    EditText movie_title;
    String username;
    Integer count=0;
    EditText movie_year;
    String hold_title;
    String hold_year;
    Button getMovie;
    ImageButton addFavorite;
    DatabaseHelper myDb;
    String movieid;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageView = findViewById(R.id.imageView2);
        mTextViewResult = findViewById(R.id.textView5);
        movie_title = findViewById(R.id.name);
        Intent i =getIntent();
        username = i.getStringExtra("user_name");
        addFavorite = findViewById(R.id.imageButton);
        movie_year = findViewById(R.id.editText);
        getMovie = findViewById(R.id.button3);
        mQueue = Volley.newRequestQueue(this);
        //Get users email from the LogIn.java class
        myDb = new DatabaseHelper(this);
        //myDb.initializeCount(username);
        getMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hold_title = movie_title.getText().toString();
                hold_year = movie_year.getText().toString();
                count = myDb.getCount(username);
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
                        mTextViewResult.setText("Movie Title: " + movie_tit);
                        addFavorite.setVisibility(View.VISIBLE);
                        addFavorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(count <= 3){
                                    myDb.insertMovieId(movieid,username,count);
                                    count=count+1;
                                    myDb.incrementCount(count,username);
                                }
                                else{
                                    Context context = getApplicationContext();
                                    CharSequence text = "Can not add to favorites. Please delete one to add another movie as a favorite.";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                }
                            }
                        });
                        //Put MovieID into DB
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
