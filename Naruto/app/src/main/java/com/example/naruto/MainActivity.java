package com.example.naruto;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
TextView soundTracks;
Button naruto, sasuke, madara, obito, pain, sasuke_intro;
MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the text views
        soundTracks = findViewById(R.id.soundtrack);

        // Initializing the Buttons
        naruto = findViewById(R.id.naruto);
        sasuke = findViewById(R.id.sasuke);
        pain = findViewById(R.id.pain);
        obito = findViewById(R.id.obito);
        madara = findViewById(R.id.madara);
        sasuke_intro = findViewById(R.id.sasuke_introduction);

        naruto.setOnClickListener(this);
        sasuke.setOnClickListener(this);
        madara.setOnClickListener(this);
        pain.setOnClickListener(this);
        obito.setOnClickListener(this);
        sasuke_intro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int buttonClickedId = view.getId();
        if(buttonClickedId == R.id.madara){
            playSound(R.raw.madara_uchiha);
        }else if(buttonClickedId == R.id.sasuke){
            playSound(R.raw.namdemo_sasuke);
        }else if(buttonClickedId == R.id.obito){
            playSound(R.raw.tobi_soundtrack);
        }else if(buttonClickedId == R.id.pain){
            playSound(R.raw.ware_ware_wa_pain_kami_da);
        }else if (buttonClickedId == R.id.naruto){
            playSound(R.raw.voicy_naruto_rasengan);
        } else if (buttonClickedId == R.id.sasuke_introduction){
            playSound(R.raw.sasuke_introduction);
        }
    }

    public void playSound(int id){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this,id);
        mediaPlayer.setVolume(1.0f, 1.0f); // set to max volume
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(mp ->{
            mp.release();
            mediaPlayer = null;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!= null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}