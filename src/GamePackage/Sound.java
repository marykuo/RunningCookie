package GamePackage;


import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    public static synchronized void play(int fileName) 
    {
    	final int MUSIC_N=7;
    	final int BACK_GROUNG=0;
    	final int JUMP=1;
    	final int JUMP2=2;
    	final int FALLING=3;
    	final int EATCAKE=4;
    	final int UP_HEARTVALUE=5;
    	final int HIT=6;

    	String[] address;
    	address = new String[MUSIC_N];
    	address[BACK_GROUNG]="Resources/audio/background.wav";
    	address[JUMP]="Resources/audio/jump.wav";
    	address[JUMP2]="Resources/audio/jump2.wav";
    	address[FALLING]="Resources/audio/falling.wav";
    	address[EATCAKE]="Resources/audio/eatcake.wav";
    	address[UP_HEARTVALUE]="Resources/audio/up_heartvalue.wav";
    	address[HIT]="Resources/audio/hit.wav";
    	
        new Thread(new Runnable() { 
            public void run() {//AudioSystem.getAudioInputStream(new File(address[fileName]));
                try {
                    Clip clip = AudioSystem.getClip();
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
