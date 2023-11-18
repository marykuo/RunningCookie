package com.marykuo.runningcookie;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    public final static int MUSIC_N = 7;
    public final static int BACKGROUND = 0;
    public final static int PLAYER_JUMP = 1;
    public final static int PLAYER_JUMP2 = 2;
    public final static int PLAYER_FALLING = 3;
    public final static int EAT_CAKE = 4;
    public final static int EAT_HEART_POTION = 5;
    public final static int PLAYER_HIT = 6;

    public static synchronized void play(int fileName) {
        String[] address = new String[MUSIC_N];
        address[BACKGROUND] = "./image/audio/background.wav";
        address[PLAYER_JUMP] = "./image/audio/jump.wav";
        address[PLAYER_JUMP2] = "./image/audio/jump2.wav";
        address[PLAYER_FALLING] = "./image/audio/falling.wav";
        address[EAT_CAKE] = "./image/audio/eatcake.wav";
        address[EAT_HEART_POTION] = "./image/audio/up_heartvalue.wav";
        address[PLAYER_HIT] = "./image/audio/hit.wav";

        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    // AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(address[fileNam]));
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(address[fileName]));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.out.println("play sound error: " + e.getMessage() + " for " + fileName);
                }
            }
        }).start();
    }
}
