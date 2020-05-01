package com.example.tutorialspoint7.finalproject.ui.login;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorialspoint7.finalproject.DatabaseHelper;
import com.example.tutorialspoint7.finalproject.R;
import com.example.tutorialspoint7.finalproject.RegisterPage;
import com.example.tutorialspoint7.finalproject.home;

public class LogIn extends AppCompatActivity {
    //Initializing variables
    Button login;
    Button signup;
    EditText username;
    EditText password;
    DatabaseHelper myDb;
    public String user_email;
    String check_pass;
    String user_pass = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create a new DatabaseHelper instance
        myDb = new DatabaseHelper(this);
        setContentView(R.layout.activity_login);
        //Connecting variables to activity
        username = findViewById(R.id.username);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        password = findViewById(R.id.password);
        //Send user to the signup activity when the SIGNUP button is pressed
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, RegisterPage.class);
                startActivity(intent);
            }
        });
        //Check users credentials and send them to the
        //home page if their login information match whats on the DB
        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                user_pass = myDb.getAllData(username.getText().toString());
                check_pass = password.getText().toString();
                user_email = username.getText().toString();
                //Check users inputted username with what is on DB for that username
                if(user_pass.equals(check_pass))
                {
                    showMessage("Attention","Password is correct");
                    Intent intent = new Intent(LogIn.this, home.class);
                    //Intent to pass user's email to home class
                    intent.putExtra("user_name",user_email);
                    startActivity(intent);
                }
                else
                {
                    showMessage("Attention","Password is incorrect");
                }
            }
        });
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
