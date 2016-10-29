package thegroup.snakego.database;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import thegroup.snakego.services.ApiRequester;

public class HighScores extends ApiRequester {
    private Context ctx;

    private String endpoint = "example.json";

    public HighScores(Context ctx) {
        this.ctx = ctx;
    }

    public void load() {
        this.getArray(this.ctx, this.endpoint);
    }

    public void store(String username, int highscore) {
        try {
            JSONObject params = new JSONObject();
            params.put("user", username);
            params.put("score", Integer.toString(highscore));

            this.postObject(this.ctx, this.endpoint, params);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
