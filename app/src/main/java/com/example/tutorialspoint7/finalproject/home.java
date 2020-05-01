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
import com.example.tutorialspoint7.finalproject.ui.login.LogIn;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class home extends AppCompatActivity {
    //Initializing my variables
    TextView mTextViewResult;
    ImageView imageView;
    EditText movie_title;
    String username;
    Integer count=0;
    EditText movie_year;
    String hold_title;
    String hold_year;
    Button getMovie;
    Button button3;
    ImageButton addFavorite;
    DatabaseHelper myDb;
    String movieid;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Pointing my variables to the correct item in the activity
        imageView = findViewById(R.id.imageView2);
        mTextViewResult = findViewById(R.id.textView5);
        movie_title = findViewById(R.id.name);
        button3 = findViewById(R.id.button4);
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
                //User input movie title
                hold_title = movie_title.getText().toString();
                //User input movie year
                hold_year = movie_year.getText().toString();
                //Check DB for movie count
                count = myDb.getCount(username);
                jsonParse();
            }
        });
        //Once button 3 is press user is sent to their favorites page
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, favorites.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
    }
    //http://www.omdbapi.com/?t=galaxy&apikey=fe4f1194
    private void jsonParse() {
        String url = "https://www.omdbapi.com/?t=" + hold_title + "&y=" + hold_year + "&apikey=fe4f1194";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String movie_tit = null;
                        String poster = null;

                        try {
                            //Get input from the JSON returned by the URL
                            movie_tit = response.getString("Title");
                            poster = response.getString("Poster");
                            movieid = response.getString("imdbID");
                            //Picasso allows for hassle-free image loading
                            Picasso.get().load(poster).into(imageView);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Display the movie title for the user obtained from the API/JSON
                        mTextViewResult.setText("Movie Title: " + movie_tit);
                        addFavorite.setVisibility(View.VISIBLE);
                        addFavorite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Obtain count before a user adds a MOVIEID into the DB
                                count = myDb.getCount(username);
                                if(count <= 3){
                                    //Calls the insertMovieID function to place the current movies ID into a free MOVIEID column
                                    myDb.insertMovieId(movieid,username,count);
                                    count=count+1;
                                    //Increment count column on DB
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
