package com.example.tutorialspoint7.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class favorites extends AppCompatActivity {

    DatabaseHelper myDb;
    String username;
    Integer movie_count;
    String movie_id;
    TextView mTextViewResult1;
    TextView mTextViewResult2;
    TextView mTextViewResult3;
    TextView mTextViewResult4;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageButton delete1;
    ImageButton delete2;
    ImageButton delete3;
    ImageButton delete4;
    private RequestQueue mQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create DB instance
        myDb = new DatabaseHelper(this);
        //Connect variables to value in activity
        setContentView(R.layout.activity_favorites);
        mTextViewResult1 = findViewById(R.id.textView8);
        mTextViewResult2 = findViewById(R.id.textView9);
        mTextViewResult3 = findViewById(R.id.textView10);
        mTextViewResult4 = findViewById(R.id.textView11);
        imageView1 = findViewById(R.id.imageView2);
        imageView2 = findViewById(R.id.imageView4);
        imageView3 = findViewById(R.id.imageView5);
        imageView4 = findViewById(R.id.imageView3);
        delete1 = findViewById(R.id.imageButton2);
        delete2 = findViewById(R.id.imageButton3);
        delete3 = findViewById(R.id.imageButton5);
        delete4 = findViewById(R.id.imageButton4);
        mQueue = Volley.newRequestQueue(this);

        //Get username from the home.java class
        Intent i = getIntent();
        username = i.getStringExtra("username");
        //Test if the user can add one more movie
        movie_count=myDb.getCount(username);
        for(int j = 0; j <= movie_count; j++) {
            if(j == 1){
                movie_id = myDb.getID(username,4);
                jsonParse(movie_id, 1);
            }
            if(j == 2){
                movie_id = myDb.getID(username,5);
                jsonParse(movie_id, 2);
            }
            if(j == 3){
                movie_id = myDb.getID(username,6);
                jsonParse(movie_id, 3);
            }
            if(j == 4){
                movie_id = myDb.getID(username,7);
                jsonParse(movie_id, 4);
            }
        }
    }

    private void jsonParse(String movieid, final Integer view_number) {
        String url = "https://www.omdbapi.com/?i=" + movieid + "&apikey=fe4f1194";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String movie_tit = null;
                        String poster = null;

                        try {
                            movie_tit = response.getString("Title");
                            poster = response.getString("Poster");
                            if(view_number == 1) {
                                //Picasso allows for hassle-free image loading
                                Picasso.get().load(poster).into(imageView1);
                                //Set imageView visibility to VISIBLE because it is hidden by default
                                imageView1.setVisibility(View.VISIBLE);
                            }
                            if(view_number == 2) {
                                Picasso.get().load(poster).into(imageView2);
                                imageView2.setVisibility(View.VISIBLE);
                            }
                            if(view_number == 3) {
                                Picasso.get().load(poster).into(imageView3);
                                imageView3.setVisibility(View.VISIBLE);
                            }
                            if(view_number == 4) {
                                Picasso.get().load(poster).into(imageView4);
                                imageView4.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Controller for the movie being shown in the favorites page
                        if(view_number == 1) {
                            mTextViewResult1.setText("" + movie_tit);
                            //OnclickListener for a movie. Once click visibility is set to hidden
                            delete1.setVisibility(View.VISIBLE);
                            delete1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //TextView is set to null once movie is deleted
                                    mTextViewResult1.setText("");
                                    delete1.setVisibility(View.INVISIBLE);
                                    imageView1.setVisibility(View.INVISIBLE);
                                    //deleteMovie function is called and a movie is erased
                                    myDb.deleteMovie(username,1);
                                }
                            });
                        }
                        //Controller for the movie being shown in the favorites page
                        if(view_number == 2) {
                            mTextViewResult2.setText("" + movie_tit);
                            delete2.setVisibility(View.VISIBLE);
                            delete2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mTextViewResult2.setText("");
                                    delete2.setVisibility(View.INVISIBLE);
                                    imageView2.setVisibility(View.INVISIBLE);
                                    myDb.deleteMovie(username,2);
                                }
                            });
                        }
                        //Controller for the movie being shown in the favorites page
                        if(view_number == 3) {
                            mTextViewResult3.setText("" + movie_tit);
                            delete3.setVisibility(View.VISIBLE);
                            delete3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mTextViewResult3.setText("");
                                    delete3.setVisibility(View.INVISIBLE);
                                    imageView3.setVisibility(View.INVISIBLE);
                                    myDb.deleteMovie(username,3);
                                }
                            });
                        }
                        //Controller for the movie being shown in the favorites page
                        if(view_number == 4) {
                            mTextViewResult4.setText("" + movie_tit);
                            delete4.setVisibility(View.VISIBLE);
                            delete4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mTextViewResult4.setText("");
                                    delete4.setVisibility(View.INVISIBLE);
                                    imageView4.setVisibility(View.INVISIBLE);
                                    myDb.deleteMovie(username,4);
                                }
                            });
                        }

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
