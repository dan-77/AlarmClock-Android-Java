package com.example.daniel2.androidalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "ALARM...", Toast.LENGTH_LONG).show();

        //Get sound variable
        String state = intent.getExtras().getString("extra");

        Log.e("What is the key", state);

        if (state.equals("sound1")) {
            Toast.makeText(context, "YOTE...", Toast.LENGTH_LONG).show();
            MediaPlayer mp;
            mp = MediaPlayer.create(context, R.raw.alert);
            mp.start();
        }
        else if (state.equals("sound2")){
            MediaPlayer mp;
            mp = MediaPlayer.create(context, R.raw.beep);
            mp.start();
        }
        else if (state.equals("sound3")){
            MediaPlayer mp;
            mp = MediaPlayer.create(context, R.raw.classic);
            mp.start();
        }
        else if (state.equals("sound4")){
            MediaPlayer mp;
            mp = MediaPlayer.create(context, R.raw.old);
            mp.start();
        }
    }
}
