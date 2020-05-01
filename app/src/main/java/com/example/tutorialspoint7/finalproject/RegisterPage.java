package com.example.tutorialspoint7.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tutorialspoint7.finalproject.ui.login.LogIn;

public class RegisterPage extends AppCompatActivity {
    DatabaseHelper myDb;
    Button register;
    EditText username;
    EditText password;
    EditText name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Connect variables to items in activity
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        //Create a new DatabaseHelper instance
        myDb = new DatabaseHelper(this);
        AddData();
    }
    public  void AddData() {
        //Function to add information into DB
        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //InsertData function returns true if information is added correctly
                        boolean isInserted = myDb.insertData(name.getText().toString(), username.getText().toString(), password.getText().toString() );
                        if(isInserted == true){
                            Toast.makeText(RegisterPage.this,"Account Created",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterPage.this, LogIn.class);
                            startActivity(intent);}
                        else
                            Toast.makeText(RegisterPage.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}

