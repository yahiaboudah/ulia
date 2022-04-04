package com.bouda.ulia.activites.displayers;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class AudioPlayer {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private String filePath;
    private MediaPlayer player;
    private boolean isPlayerOn = false;

    public AudioPlayer(String filePath) {

        this.filePath = filePath;
        this.player = new MediaPlayer();
        try {
            this.player.setDataSource(filePath);
            this.player.prepare();
        }
        catch(IOException e){
            Log.i(LOG_TAG, String.valueOf(e));
        }
    }

    public void play(){
        this.player.start();
    }

    public void stop(){
        this.player.stop();
    }
}
