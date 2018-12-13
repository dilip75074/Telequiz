package com.example.telequiz.services.utilities;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

public class SoundManager extends Activity {
    MediaPlayer mp ;
    Context context;

    public SoundManager(Context context, int id) {
        this.context = context;
        mp = MediaPlayer.create(context, id);
    }

    @Override
    public void onStop() {
        super.onStop();
        mp.stop();
        mp.release();
    }
    public void playSound() {
        mp.start();
    }

    public void vibrateDevice(long duration)
    {
        Vibrator vb = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(duration);
    }
}
