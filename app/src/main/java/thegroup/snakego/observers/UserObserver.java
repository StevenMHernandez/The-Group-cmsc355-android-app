package thegroup.snakego.observers;

import android.content.Context;

import thegroup.snakego.database.HighScores;
import thegroup.snakego.models.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UserObserver implements PropertyChangeListener {
    private final Context context;

    public UserObserver(Context context) {
        this.context = context;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("new_highscore")) {
            String username = User.get().getName();
            if (username != null) {
                HighScores highScores = new HighScores(this.context, null);
                highScores.store(username, (int) event.getNewValue());
            }
        }
    }
}
