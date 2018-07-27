package com.example.swapnil.eggtimer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar timerSeekBar;
    Button goButton;
    boolean isactive = false;
    CountDownTimer countDownTimer;

    public void resetTimer() {
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        isactive = false;

    }

    @SuppressLint("ResourceAsColor")
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void goButtonClicked(View view) {
        goButton.setText("Stop!");
        timerSeekBar.setEnabled(false);

        if (isactive) {
            resetTimer();
        } else {
            isactive = true;
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {


                    update((int) (millisUntilFinished / 1000));


                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this, "TIME UP!!!!!!", Toast.LENGTH_LONG).show();
                    resetTimer();
                }
            }.start();

        }
    }

    public void update(int progress) {
        int min = progress / 60;
        int seconds = progress - (min * 60);
        String roundingDigits = Integer.toString(seconds);
        if (seconds <= 9) {
            roundingDigits = "0" + roundingDigits;
        }
        timerTextView.setText(Integer.toString(min) + ":" + roundingDigits);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goButton = findViewById(R.id.goButton);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(10);


        timerTextView = findViewById(R.id.timerTextView);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(progress);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


}
