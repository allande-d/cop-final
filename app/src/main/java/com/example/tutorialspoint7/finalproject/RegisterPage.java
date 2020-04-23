package com.example.tutorialspoint7.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterPage extends AppCompatActivity {
    DatabaseHelper myDb;
    Button login;
    Button signup;
    Button register;
    EditText username;
    EditText password;
    EditText name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        register = findViewById(R.id.register);
        myDb = new DatabaseHelper(this);


    }
    public  void AddData() {
        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(name.getText().toString(), username.getText().toString(), password.getText().toString() );
                        if(isInserted == true)
                            Toast.makeText(RegisterPage.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(RegisterPage.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}

