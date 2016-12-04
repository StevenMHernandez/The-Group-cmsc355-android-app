package thegroup.snakego.models;

import com.google.android.gms.maps.model.LatLng;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import thegroup.snakego.interfaces.Listenable;

public class User implements Listenable {
    private static User instance;

    private LinkedList<LatLng> userLocationHistory = new LinkedList<>();

    private String name = "User1";

    private boolean moving;
    private float lastX;
    private float lastY;
    private float lastZ;
    private static final int THRESHOLD = 5;
    private int greenAppleCount;
    private int redAppleCount;

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
        redAppleCount++;
        this.notifyListeners(this, "score", this.score, this.score + points);

        this.score += points;

        if (this.score > this.highScore) {
            this.highScore = this.score;

            this.notifyListeners(this, "new_highscore", this.highScore - points, this.highScore);
        }

        return this.score;
    }

    public int removePoints(int points) {
        greenAppleCount++;

        int oldScore = this.score;

        this.score -= points;

        if (this.score < 0) {
            this.score = 0;
        }

        this.notifyListeners(this, "score", oldScore, this.score);

        this.updateSnakeLength();

        return this.score;
    }

    public void onLocationUpdated(LatLng latLng) {
//        if (moving || this.latLng == null) {
            this.setLatLng(latLng);
            userLocationHistory.add(latLng);
            this.updateSnakeLength();
//        }
    }

    public void accelerometerChanged(float currX, float currY, float currZ, long diffTime) {
        //Reference Website: https://www.sitepoint.com/using-android-sensors-application/
        float speed = Math.abs(currX + currY + currZ - lastX - lastY - lastZ) / diffTime * 10000;

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

    public int getGreenAppleCount() {
        return this.greenAppleCount;
    }

    public int getRedAppleCount() {
        return this.redAppleCount;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public LinkedList<LatLng> getUserLocationHistory() {
        return userLocationHistory;
    }

    public LatLng getPosition() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getMaxSnakeLength() {
        return ((score / 10) + 2);
    }

    public boolean getMoving() {
        return moving;
    }

    private void updateSnakeLength() {
        while (this.userLocationHistory.size() > this.getMaxSnakeLength()) {
            this.userLocationHistory.removeFirst();
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

    public void clearSnake() {
        userLocationHistory.clear();
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }
}
