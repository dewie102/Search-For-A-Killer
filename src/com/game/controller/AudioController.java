package com.game.controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class AudioController {

    private static Float sfxVolDelta = 0f;
    private static Float musicVolDelta = 0f;

    //musicPaths[1] is an alternative
    private static final String[] musicPaths =  {
            "audio/jazz-loop-7163.wav",
            "audio/sazzy-71792.wav",
            "audio/walking-3seconds.wav",
            "audio/item-pick-up-38258.wav",
            "audio/wooden-thud-mono-6244.wav"};
    private static final String[] sfxPaths = {

    };
    private static final String[] soundPaths = new String[musicPaths.length + sfxPaths.length];
    private static final AudioInputStream[] inputStream = new AudioInputStream[musicPaths.length];
    private static final File[] file = new File[musicPaths.length];
    private static final Clip[] sound = new Clip[musicPaths.length];

    //BACKGROUND music volume controls -

    //No argument version decreases by 10
    public static void musicVolDown(){
        musicVolDelta-=10.0f;
        FloatControl gainControl = (FloatControl) sound[0].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(musicVolDelta);
    }

    public static void musicVolDown(float downBy){
        musicVolDelta-=downBy;
        FloatControl gainControl = (FloatControl) sound[0].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(musicVolDelta);
    }

    //No argument version increases by 10
    public static void musicVolUp(){
        musicVolDelta+=10.0f;
        FloatControl gainControl = (FloatControl) sound[0].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(musicVolDelta);
    }

    public static void musicVolUp(float upBy){
        musicVolDelta+=upBy;
        FloatControl gainControl = (FloatControl) sound[0].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(musicVolDelta);
    }

    //SFX volume controls -
    //TODO: change sound[0] to sounds that are SFX
    public static void sfxVolDown(){
        sfxVolDelta-=10.0f;
        FloatControl gainControl = (FloatControl) sound[0].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(sfxVolDelta);
    }

    public static void sfxVolDown(float downBy){
        sfxVolDelta-=downBy;
        FloatControl gainControl = (FloatControl) sound[0].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(sfxVolDelta);
    }

    //No argument version increases by 10
    public static void sfxVolUp(){
        sfxVolDelta+=10.0f;
        FloatControl gainControl = (FloatControl) sound[0].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(sfxVolDelta);
    }

    public static void sfxVolUp(float upBy){
        sfxVolDelta+=upBy;
        FloatControl gainControl = (FloatControl) sound[0].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(sfxVolDelta);
    }

    //General controls

    public static void loopMusic(){
        sound[0].loop(Clip.LOOP_CONTINUOUSLY);
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

}