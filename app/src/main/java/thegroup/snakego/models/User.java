package thegroup.snakego.models;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import thegroup.snakego.interfaces.Listenable;

public class User implements Listenable {
    private static User instance;

    private LinkedList<LatLng> snake = new LinkedList<>();

    private boolean moving;
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

    public int addPoints(int points) {
        this.score += points;

        if (this.score > this.highScore) {
            this.highScore = this.score;

            this.notifyListeners(this, "new_highscore", this.highScore - points, this.highScore);
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

            Log.v("onLocationUpdated", "Location updated!");
        } else {
            Log.v("onLocationUpdated", "Location not updated!");
        }
    }

    public void accelerometerChanged(float paramX, float paramY, float paramZ, long diffTime) {
        //Reference Website: https://www.sitepoint.com/using-android-sensors-application/
        float currX = paramX;
        float currY = paramY;
        float currZ = paramZ;

        float speed = Math.abs(currX + currY + currZ - lastX - lastY - lastZ) / diffTime * 10000;
        String speedString = String.valueOf(speed);
        Log.v("accelerometerChanged", "speed = "+speedString);

        User.get().setMoving(speed > THRESHOLD);

        lastX = currX;
        lastY = currY;
        lastZ = currZ;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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

    public LatLng getPosition() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getMaxSnakeLength() {
        return ((score / 10) + 1);
    }

    public boolean getMoving() {
        return moving;
    }

    private void updateSnakeLength() {
        while (this.snake.size() > this.getMaxSnakeLength()) {
            this.snake.removeFirst();
        }
    }

    @Override
    public void notifyListeners(Object object, String property, Object oldValue, Object newValue) {
        for (PropertyChangeListener name : listeners) {
            name.propertyChange(new PropertyChangeEvent(object, property, oldValue, newValue));
        }
    }

    @Override
    public void addChangeListener(PropertyChangeListener newListener) {
        listeners.add(newListener);
    }


}
