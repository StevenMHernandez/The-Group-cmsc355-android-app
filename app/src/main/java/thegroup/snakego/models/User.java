package thegroup.snakego.models;

import com.google.android.gms.maps.model.LatLng;

import android.util.Log;

import java.util.LinkedList;

public class User {
    private static User instance;

    private LinkedList<LatLng> snake = new LinkedList<>();

    private float lastX;
    private float lastY;
    private float lastZ;
    private static final int THRESHOLD = 5;

    public static synchronized User get() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    private int score = 0;

    private int highScore = 0;

    private LatLng latLng;

    private boolean moving;

    public int addPoints(int points) {
        this.score += points;

        if (this.score > this.highScore) {
            this.highScore = this.score;
        }

        return this.score;
    }

    public int removePoints(int points) {
        this.score -= points;

        if (this.score < 0) {
            this.score = 0;
        }

        this.updateSnakeLength();

        return this.score;
    }

    public void onLocationUpdated(LatLng latLng) {
        if (moving) {
            this.setLatLng(latLng);
            snake.add(latLng);
            this.updateSnakeLength();
        }

    }

    public void accelerometerChanged(float paramX, float paramY, float paramZ, long diffTime) {
        //Reference Website: https://www.sitepoint.com/using-android-sensors-application/
        float currX = paramX;
        float currY = paramY;
        float currZ = paramZ;

        float speed = Math.abs(currX + currY + currZ - lastX - lastY - lastZ) / diffTime * 10000;

        User.get().setMoving(speed > THRESHOLD);

        lastX = currX;
        lastY = currY;
        lastZ = currZ;
    }


    public int getScore() {
        return this.score;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public LinkedList<LatLng> getSnake() {
        return snake;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getMaxSnakeLength() {
        return ((score / 10) + 1);
    }

    private void updateSnakeLength() {
        while (this.snake.size() > this.getMaxSnakeLength()) {
            this.snake.removeFirst();
        }
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

}
