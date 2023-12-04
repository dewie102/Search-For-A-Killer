package com.game.controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class AudioController {

    private static final String[] musicPaths =  {
            "audio/jazz-loop-7163.wav",
            "audio/sazzy-71792.wav"};
    private static AudioInputStream[] inputStream = new AudioInputStream[musicPaths.length];
    private static File[] file = new File[musicPaths.length];
    private static Clip[] music = new Clip[musicPaths.length];

    public static void loopMusic(){
        music[0].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void volumeDown(){

    }

    public static void volumeUp(){

    }

    public static void playMusic() {

    }

    public static void stopMusic() {

    }


    public static void loadMusic() {
        for (int i = 0; i <musicPaths.length; i++) {
            try {
                file[i] = new File(musicPaths[i]);
                inputStream[i] = AudioSystem.getAudioInputStream(file[i]);
                music [i] = AudioSystem.getClip();
                music [i].open(inputStream[i]);
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