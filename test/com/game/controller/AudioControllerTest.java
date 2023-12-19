package com.game.controller;

import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.FloatControl;

import static org.junit.Assert.assertEquals;

public class AudioControllerTest {
    private FloatControl musicGainControl;
    private FloatControl sfxGainControl;

    private static final float TEST_VOLUME = 6.0f;

    @Before
    public void setUp() throws Exception {
        // load music and get the FloatControl
        AudioController.loadMusic();
        musicGainControl = (FloatControl) AudioController.sound[0].getControl(FloatControl.Type.MASTER_GAIN);

        // load SFX and get the FloatControl
        AudioController.setSfxOn(true);
        sfxGainControl = (FloatControl) AudioController.sound[2].getControl(FloatControl.Type.MASTER_GAIN);
    }

    @Test
    public void shouldSetMusicVolumeToSpecifiedValue() {
        // set the music volume
        AudioController.setMusicVol(TEST_VOLUME);

        // check if the volume is set correctly for music
        assertEquals(TEST_VOLUME, musicGainControl.getValue(), .001);
    }

    @Test
    public void shouldSetSfxVolumeToSpecifiedValue() {
        // set the SFX volume
        AudioController.setSfxVol(TEST_VOLUME);

        // check if the volume is set correctly for SFX
        assertEquals(TEST_VOLUME, sfxGainControl.getValue(), .001);
    }
}
