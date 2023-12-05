package com.game.controller;

import com.game.view.ConsoleText;
import com.game.view.MultipleChoiceConsoleView;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.File;
import java.util.List;

public class AudioController {

    private static Float sfxVolDelta = 0f;
    private static Float musicVolDelta = 0f;
    private static boolean sfxOn = true;

    //musicPaths[1] is an alternative
    private static final String[] soundPaths =  {
            "audio/jazz-loop-7163.wav",
            "audio/sazzy-71792.wav",
            "audio/walking-3seconds.wav",
            "audio/item-pick-up-38258.wav",
            "audio/wooden-thud-mono-6244.wav"};
    private static final AudioInputStream[] inputStream = new AudioInputStream[soundPaths.length];
    private static final File[] file = new File[soundPaths.length];
    private static final Clip[] sound = new Clip[soundPaths.length];

    public static boolean volMenu(){
        MultipleChoiceConsoleView consoleView = new MultipleChoiceConsoleView(
                List.of(List.of(new ConsoleText("What would you like to do? Type 'exit' to exit the menu."))),
                List.of(
                        new ConsoleText("Music ON"),
                        new ConsoleText("Music OFF"),
                        new ConsoleText("Music UP"),
                        new ConsoleText("Music DOWN"),
                        new ConsoleText("Sound Effects ON"),
                        new ConsoleText("Sound Effects OFF"),
                        new ConsoleText("Sound Effects UP"),
                        new ConsoleText("Sound Effects DOWN"),
                        new ConsoleText("Exit audio menu")

                ));
        String userInput = consoleView.show();
        switch (userInput){
            case "0": // M on
                System.out.println("You entered 0");
                return false;
            case "1": // M off
                stopSound(0);
                return false;
            case "2": // M up
                musicVolUp();
                return true;
            case "3": // M down
                musicVolDown();
                return true;
            case "4": //SFX on
                setSfxOn(true);
                return false;
            case "5": //SFX off
                setSfxOn(false);
                return false;
            case "6"://SFX up
                sfxVolUp();
                return false;
            case "7"://SFX down
                sfxVolDown();
                return false;
            case "8":
                return true;
            default:
                return false;
        }

    }


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
        if(isSfxOn()) {
            stopSound(soundIndex);
            sound[soundIndex].start();
        }
    }

    public static void loadMusic() {
        for (int i = 0; i < soundPaths.length; i++) {
            try {
                file[i] = new File(soundPaths[i]);
                inputStream[i] = AudioSystem.getAudioInputStream(file[i]);
                sound[i] = AudioSystem.getClip();
                sound[i].open(inputStream[i]);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    public static Float getSfxVolDelta() {
        return sfxVolDelta;
    }

    public static void setSfxVolDelta(Float sfxVolDelta) {
        AudioController.sfxVolDelta = sfxVolDelta;
    }

    public static Float getMusicVolDelta() {
        return musicVolDelta;
    }

    public static void setMusicVolDelta(Float musicVolDelta) {
        AudioController.musicVolDelta = musicVolDelta;
    }

    public static boolean isSfxOn() {
        return sfxOn;
    }

    public static void setSfxOn(boolean sfxOn) {
        AudioController.sfxOn = sfxOn;
    }
}