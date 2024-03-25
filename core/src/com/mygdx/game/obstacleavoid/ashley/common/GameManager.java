package com.mygdx.game.obstacleavoid.ashley.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.obstacleavoid.ashley.ObstacleAvoidGame;
import com.mygdx.game.obstacleavoid.ashley.config.DifficultyLevel;
import com.mygdx.game.obstacleavoid.ashley.config.GameConfig;


public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String HIGH_SCORE_KEY = "highscore";
    private static final String DIFFICULTY_KEY = "difficulty";

    private Preferences PREFS;
    private int highscore;
    private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;
    private int lives = GameConfig.LIVES_START;
    private int score;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(ObstacleAvoidGame.class.getSimpleName());
        highscore = PREFS.getInteger(HIGH_SCORE_KEY, 0);
        String difficultyName = PREFS.getString(DIFFICULTY_KEY, DifficultyLevel.MEDIUM.name());
        difficultyLevel = DifficultyLevel.valueOf(difficultyName);
    }

    public void updateHighScore() {
        if(score < highscore) {
            return;
        }

        highscore = score;
        PREFS.putInteger(HIGH_SCORE_KEY, highscore);
        PREFS.flush();
    }

    public void reset() {
        lives = GameConfig.LIVES_START;
        score = 0;
    }

    public void updateScore(int amount) {
        score += amount;
    }

    public void decrementLives() {
        lives--;
    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public String getHighScoreString() {
        return String.valueOf(highscore);
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void updateDifficulty(DifficultyLevel newDifficultyLevel) {
        if(difficultyLevel == newDifficultyLevel) {
            return;
        }

        difficultyLevel = newDifficultyLevel;
        PREFS.putString(DIFFICULTY_KEY, difficultyLevel.name());
        PREFS.flush();
    }
}
