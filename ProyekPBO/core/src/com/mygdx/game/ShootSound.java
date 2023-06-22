package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class ShootSound extends AbstractSound {
    public ShootSound() {
        sound = Gdx.audio.newSound(Gdx.files.internal("shootSound.mp3"));
    }

    @Override
    public void playSound() {
        sound.play();
    }
}
