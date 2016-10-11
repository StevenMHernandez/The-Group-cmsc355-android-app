package thegroup.snakego.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;

public class User {
    private static User instance;

    protected LinkedList<LatLng> snake = new LinkedList<>();

    public synchronized static User get() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    protected int score = 0;

    protected int highScore = 0;

    protected LatLng latLng;

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
        this.setLatLng(latLng);

        snake.add(latLng);

        this.updateSnakeLength();
    }

    public int getScore() {
        return score;
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
        return (score / 10) + 11;
    }

    public void updateSnakeLength() {
        while (this.snake.size() > this.getMaxSnakeLength()) {
            this.snake.removeFirst();
        }
    }
}
