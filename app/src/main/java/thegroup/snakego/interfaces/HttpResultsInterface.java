package thegroup.snakego.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface HttpResultsInterface {
    void onSuccess(JSONObject response, int method, String endpoint);

    void onSuccess(JSONArray response, int method, String endpoint);

    void onError(VolleyError error, int method, String endpoint);
}
