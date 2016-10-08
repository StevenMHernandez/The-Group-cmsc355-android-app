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

    public int addPoints(int points) {
        this.score += points;

        if (this.score > this.highScore) {
            this.highScore = this.score;
        }

        return this.score;
    }

    public int removePoints(int points) {
        this.score -= points;
        return this.score;
    }

    public void onLocationUpdated(LatLng latLng) {
        snake.add(latLng);

        while (this.snake.size() > this.getMaxSnakeLength()) {
            this.snake.removeFirst();
        }
    }

    public int getScore() {
        return score;
    }

    protected int getMaxSnakeLength() {
        return (score / 10) + 10;
    public LinkedList<LatLng> getSnake() {
        return snake;
    }


    private int getMaxSnakeLength() {
        return (score / 10) + 11;
    }
}
