package com.example.tutorialspoint7.finalproject.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tutorialspoint7.finalproject.R;
import com.example.tutorialspoint7.finalproject.RegisterPage;

public class LogIn extends AppCompatActivity {
    Button login;
    Button signup;
    EditText username;
    EditText password;
    EditText name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.name);
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

    }
}
