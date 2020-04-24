package com.example.tutorialspoint7.finalproject.ui.login;

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
    Button login;
    Button signup;
    EditText username;
    EditText password;
    DatabaseHelper myDb;
    String check_pass;
    String user_pass = "";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        password = findViewById(R.id.password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, RegisterPage.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                user_pass = myDb.getAllData(username.getText().toString());
                check_pass = password.getText().toString();
                if(user_pass.equals(check_pass))
                {
                    showMessage("Attention","Password is correct");
                    Intent intent = new Intent(LogIn.this, home.class);
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
