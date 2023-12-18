package com.game.controller;

import com.game.controller.io.JsonMessageParser;
import com.game.view.gui.GameWindow;
import com.game.view.terminal.ConsoleText;
import com.game.view.terminal.MultipleChoiceConsoleView;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.ArrayList;
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
    private static final List<ConsoleText> audioOptions = new ArrayList<>();

    // volume control for console
    public static boolean volMenu(){
        JsonMessageParser.loadAudioOptions();
        if(audioOptions.isEmpty()) {
            for (String option : JsonMessageParser.getAudioOptions()) {
                audioOptions.add(new ConsoleText(option));
            }
        }

        MultipleChoiceConsoleView consoleView = new MultipleChoiceConsoleView(
                List.of(List.of(new ConsoleText("What would you like to do? Type 'exit' to exit the menu."))), audioOptions);
        consoleView.show();
        String userInput = consoleView.collectInput();
        switch (userInput){
            case "0": // M on
                loopMusic();
                return true;
            case "1": // M off
                stopSound(0);
                return true;
            case "2": // M up
                try {
                    musicVolUp();
                    return true;
                } catch (IllegalArgumentException e){
                    return false;
                }

            case "3": // M down
                musicVolDown();
                return true;
            case "4": //SFX on
                setSfxOn(true);
                return true;
            case "5": //SFX off
                setSfxOn(false);
                return true;
            case "6"://SFX up
                try {
                    sfxVolUp();
                    return true;
                } catch (IllegalArgumentException e){
                    return false;
                }
            case "7"://SFX down
                sfxVolDown();
                return true;
            case "8":
                return true;
            default:
                return false;
        }
    }

    // volume control on GUI
    public static boolean volControl(){
        JsonMessageParser.loadAudioOptions();
        if(audioOptions.isEmpty()) {
            for (String option : JsonMessageParser.getAudioOptions()) {
                audioOptions.add(new ConsoleText(option));
            }
        }

        String userInput = GameWindow.getCurrentVolumeOption();
        float newVolume = (GameWindow.getCurrentVolume() == 0) ? -80.0f : Math.max(-60.0f, Math.min(6.0f, (GameWindow.getCurrentVolume() / 100.0f) * 66.0f - 60.0f));
        float newSfxVolume = (GameWindow.getCurrentSfxVolume() == 0) ? -80.0f : Math.max(-60.0f, Math.min(6.0f, (GameWindow.getCurrentSfxVolume() / 100.0f) * 66.0f - 60.0f));
        switch (userInput){
            case "0": // M on
                loopMusic();
                return true;
            case "1": // M off
                stopSound(0);
                return true;
            case "2": // M up
                setMusicVol(newVolume);
                return true;
            case "3": // M down
                musicVolDown();
                return true;
            case "4": //SFX on
                setSfxOn(true);
                return true;
            case "5": //SFX off
                setSfxOn(false);
                return true;
            case "6": //set SFX
                setSfxVol(newSfxVolume);
                return true;
            case "7"://SFX up
                try {
                    sfxVolUp();
                    return true;
                } catch (IllegalArgumentException e){
                    return false;
                }
            case "8"://SFX down
                sfxVolDown();
                return true;
            case "9":
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

    public static void setMusicVol(float newVol) {
        FloatControl gainControl = (FloatControl) sound[0].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(newVol);
    }

    //SFX volume controls -

    //No argument version decreases by 10
    public static void sfxVolDown(){
        sfxVolDelta-=10.0f;
        FloatControl gainControl1 = (FloatControl) sound[2].getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gainControl2 = (FloatControl) sound[3].getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gainControl3 = (FloatControl) sound[4].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl1.setValue(sfxVolDelta);
        gainControl2.setValue(sfxVolDelta);
        gainControl3.setValue(sfxVolDelta);
    }

    public static void sfxVolDown(float downBy){
        sfxVolDelta-=downBy;
        FloatControl gainControl1 = (FloatControl) sound[2].getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gainControl2 = (FloatControl) sound[3].getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gainControl3 = (FloatControl) sound[4].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl1.setValue(sfxVolDelta);
        gainControl2.setValue(sfxVolDelta);
        gainControl3.setValue(sfxVolDelta);
    }

    //No argument version increases by 10
    public static void sfxVolUp(){
        sfxVolDelta+=10.0f;
        FloatControl gainControl1 = (FloatControl) sound[2].getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gainControl2 = (FloatControl) sound[3].getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gainControl3 = (FloatControl) sound[4].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl1.setValue(sfxVolDelta);
        gainControl2.setValue(sfxVolDelta);
        gainControl3.setValue(sfxVolDelta);
    }

    public static void sfxVolUp(float upBy){
        sfxVolDelta+=upBy;
        FloatControl gainControl1 = (FloatControl) sound[2].getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gainControl2 = (FloatControl) sound[3].getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gainControl3 = (FloatControl) sound[4].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl1.setValue(sfxVolDelta);
        gainControl2.setValue(sfxVolDelta);
        gainControl3.setValue(sfxVolDelta);
    }

    public static void setSfxVol(float newVol){
        FloatControl gainControl1 = (FloatControl) sound[2].getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gainControl2 = (FloatControl) sound[3].getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gainControl3 = (FloatControl) sound[4].getControl(FloatControl.Type.MASTER_GAIN);
        gainControl1.setValue(newVol);
        gainControl2.setValue(newVol);
        gainControl3.setValue(newVol);
        setSfxOn(true);
    }

    //General controls

    public static void loopMusic(){
        FloatControl gainControl = (FloatControl) sound[0].getControl(FloatControl.Type.MASTER_GAIN);
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