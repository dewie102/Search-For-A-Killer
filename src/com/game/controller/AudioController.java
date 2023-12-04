package com.game.controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class AudioController {

    //musicPaths[1] is an alternative
    private static final String[] musicPaths =  {
            "audio/jazz-loop-7163.wav",
            "audio/sazzy-71792.wav",
            "audio/walking-3seconds.wav",
            "audio/item-pick-up-38258.wav",
            "audio/wooden-thud-mono-6244.wav"};
    private static AudioInputStream[] inputStream = new AudioInputStream[musicPaths.length];
    private static File[] file = new File[musicPaths.length];
    private static Clip[] sound = new Clip[musicPaths.length];

    public static void loopMusic(){
        sound[0].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void volumeDown(){

    }

    public static void volumeUp(){

    }

    public static void playLoop() {

    }

    public static void stopSound(int soundIndex) {
        sound[soundIndex].stop();
        sound[soundIndex].setFramePosition(0);
    }

    public static void playSFX(int soundIndex){
        stopSound(soundIndex);
        sound[soundIndex].start();
    }

    public static void loadMusic() {
        for (int i = 0; i <musicPaths.length; i++) {
            try {
                file[i] = new File(musicPaths[i]);
                inputStream[i] = AudioSystem.getAudioInputStream(file[i]);
                sound[i] = AudioSystem.getClip();
                sound[i].open(inputStream[i]);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }



    }








    //Players can turn music on and off
    //Players can control music volume

    //Players can hear FX
    //Players can turn SFX on/off
    //Players can control SFX volume

}