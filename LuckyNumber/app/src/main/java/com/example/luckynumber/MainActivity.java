package com.example.luckynumber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView welcomeText;
    EditText userName;
    Button btn;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find View by id's
        welcomeText = findViewById(R.id.textView);
        userName = findViewById(R.id.name);
        btn = findViewById(R.id.btn);



        // Button to next Interface
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get User Name
                name =  userName.getText().toString();
                if(!name.isEmpty()){
                    displayLuckyNumber();
                }
                else{
                    Toast.makeText(MainActivity.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void displayLuckyNumber(){
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("name" , name);
        startActivity(intent);
    }

}