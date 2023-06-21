package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class HighScoreManager {
    private static final String HIGH_SCORE_FILE = "highscore.dat";
    private int highScore;

    public HighScoreManager() {
        loadHighScore();
    }

    private void loadHighScore() {
        try {
            FileHandle file = Gdx.files.local(HIGH_SCORE_FILE);
            if (file.exists()) {
                String data = file.readString();
                highScore = Integer.parseInt(data.trim());
            } else {
                highScore = 0;
            }
        } catch (Exception e) {
            highScore = 0;
            e.printStackTrace();
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public boolean isNewHighScore(int score) {
        return score > highScore;
    }

    public void saveHighScore(int score) {
        if (isNewHighScore(score)) {
            highScore = score;
            try {
                FileHandle file = Gdx.files.local(HIGH_SCORE_FILE);
                file.writeString(Integer.toString(highScore), false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

