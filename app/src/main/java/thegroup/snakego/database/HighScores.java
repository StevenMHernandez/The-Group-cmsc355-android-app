package thegroup.snakego.database;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import thegroup.snakego.interfaces.HttpResultsInterface;
import thegroup.snakego.services.ApiRequester;

public class HighScores extends ApiRequester {

    private String endpoint = "highscores";

    public HighScores(Context ctx) {
        super(ctx);
    }

    public HighScores(Context ctx, HttpResultsInterface callback) {
        super(ctx, callback);
    }

    public void load() {
        this.getArray(this.endpoint);
    }

    public void store(String username, int highscore) {
        try {
            JSONObject params = new JSONObject();
            params.put("Username", username);
            params.put("Score", highscore);

            this.postObject(this.endpoint, params);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
