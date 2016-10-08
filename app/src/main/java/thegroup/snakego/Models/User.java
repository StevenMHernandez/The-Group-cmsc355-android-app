package thegroup.snakego.Models;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;

public class User {
    private static User instance;

    private LinkedList<LatLng> snake = new LinkedList<>();


    public synchronized static User get() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    private int score = 0;

    private int highScore = 0;

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

    public LinkedList<LatLng> getSnake() {
        return snake;
    }


    private int getMaxSnakeLength() {
        return (score / 10) + 11;
    }
}
