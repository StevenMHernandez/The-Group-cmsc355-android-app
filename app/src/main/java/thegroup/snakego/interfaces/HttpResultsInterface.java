package thegroup.snakego.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface HttpResultsInterface {
    void onSuccess(JSONObject response, String method, String endpoint);

    void onSuccess(JSONArray response, String method, String endpoint);

    void onError(VolleyError error, String method, String endpoint);
}
