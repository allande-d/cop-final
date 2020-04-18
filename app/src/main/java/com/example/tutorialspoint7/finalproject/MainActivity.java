package com.example.tutorialspoint7.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    EditText username;
    EditText password;
    Button ChangeScreen;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChangeScreen = findViewById(R.id.button2);
        ChangeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_login);

            }
        });
    }
}
