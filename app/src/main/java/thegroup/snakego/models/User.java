package thegroup.snakego.models;

import com.google.android.gms.maps.model.LatLng;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import thegroup.snakego.interfaces.Listenable;

public class User implements Listenable {
    private static User instance;

    private LinkedList<LatLng> snake = new LinkedList<>();

    public static synchronized User get() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    private int score = 0;

    private int highScore = 0;

    private LatLng latLng;

    private boolean isMoving;

    public void addPoints(int points) {
        this.score += points;

        if (this.score > this.highScore) {
            this.highScore = this.score;
            this.notifyListeners(this, "new_highscore", this.highScore - points, this.highScore);
        }
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
        //if (isMoving) {
            this.setLatLng(latLng);
            snake.add(latLng);
            this.updateSnakeLength();
    //      Log.v("onLocationUpdated", "Phone was moving when updated!");
    //      } else {
    //      Log.v("onLocationUpdated", "Phone was not moving, location not updated!");
    //      }
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

    private void updateSnakeLength() {
        while (this.snake.size() > this.getMaxSnakeLength()) {
            this.snake.removeFirst();
        }
    }

    public void setIsMoving(boolean moving) {
        isMoving = moving;
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
