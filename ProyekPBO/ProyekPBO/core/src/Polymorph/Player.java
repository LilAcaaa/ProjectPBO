package Polymorph;

import com.badlogic.gdx.graphics.Texture;


public class Player  extends GameObject {
    public Player(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void update(float deltaTime) {
        // Implementasi logika pembaruan pemain
    }
}
