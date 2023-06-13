package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class Alien {
    protected Texture alienTexture;
    protected int hitPoints;

    // Other common properties and methods

    public void render(SpriteBatch batch, Rectangle alien) {
        batch.draw(alienTexture, alien.x, alien.y);
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    public void hit() {
        hitPoints--;
    }

    /*
    diff alienz
     */


    public class Type2Alien extends Alien {
        public Type2Alien() {
            alienTexture = new Texture("type2_alien.png");
            hitPoints = 5;
        }
    }

}
