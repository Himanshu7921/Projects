package com.example.luckynumber;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {
Button share_btn;
TextView showRanNum;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Finding Views by their respective id's
        showRanNum = findViewById(R.id.randomNumber);
        share_btn = findViewById(R.id.button);

        // Displaying the Generated Random Number
        int ran_num = generateRandomNumber();
        showRanNum.setText(String.valueOf(ran_num));

        // Getting the UserName from main Activity
        String userName = getIntent().getStringExtra("name");

        // Share my Random Number Button
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareData(userName,ran_num);
            }
        });
    }

    // Generates Random Number
    public int generateRandomNumber(){
        Random random = new Random();
        int MAX_VAL = 1000;
        return random.nextInt(MAX_VAL);
    }

    // Share the Data Among Other Platforms
    public void shareData(String userName, int randomNumber){
        // Implicit Intent
        Intent i = new Intent(Intent.ACTION_SEND);

        // Use MIME type for email clients
        i.setType("message/rfc822");

        // Additional Information Displayed!
        i.putExtra(Intent.EXTRA_SUBJECT, "Jackpot Alert! " + userName + "'s Lucky Number is In!");
        i.putExtra(Intent.EXTRA_TEXT, "Hey there! " + userName + " just hit the jackpot with the number " + randomNumber);

        startActivity(Intent.createChooser(i, "Choose a Platform"));
    }
}