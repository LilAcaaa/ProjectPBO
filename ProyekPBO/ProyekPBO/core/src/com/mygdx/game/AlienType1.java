package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

public class AlienType1 extends Rectangle {
    private Texture alienTexture;

    public AlienType1() {
        alienTexture = new Texture("type1_alien.png");
        setWidth(64);
        setHeight(64);
        int hitPoints = 3;
    }

    public void setWidth(float width) {
        this.width = (int) width;
    }

    public void setHeight(float height) {
        this.height = (int) height;

    }
}
    
