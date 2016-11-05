package thegroup.snakego.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import thegroup.snakego.interfaces.HttpResultsInterface;

public class ApiRequester {

    private String baseUrl = "http://snake-go.shmah.com/";

    // The url below can be used instead for testing our go api locally
    // Android gives access to your local machine's localhost through 10.0.2.2
    // just run `go run main.go` and you should be able to access your local api
    //private String baseUrl = "http://10.0.2.2:3000/";

    private RequestQueue requestQueue;

    private HttpResultsInterface callback;

    private Context ctx;

    public ApiRequester(Context ctx) {
        this.ctx = ctx;

        this.callback = (HttpResultsInterface) ctx;

        if (ctx != null) {
            this.requestQueue = Volley.newRequestQueue(ctx);
        }
    }

    public ApiRequester(Context ctx, HttpResultsInterface callback) {
        this.ctx = ctx;

        this.callback = callback;

        if (ctx != null) {
            this.requestQueue = Volley.newRequestQueue(ctx);
        }
    }

    private void requestArray(final int method, final String endpoint, JSONArray params) {
        String url = this.baseUrl + endpoint;

        JsonArrayRequest jsonRequest = new JsonArrayRequest(method, url, params,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (callback != null) {
                            callback.onSuccess(response, method, endpoint);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (callback != null) {
                            callback.onError(error, method, endpoint);
                        }
                    }
                });

        requestQueue.add(jsonRequest);
    }

    private void requestObject(final int method, final String endpoint, JSONObject params) {
        String url = this.baseUrl + endpoint;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(method, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (callback != null) {
                            callback.onSuccess(response, method, endpoint);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (callback != null) {
                            callback.onError(error, method, endpoint);
                        }
                    }
                });

        requestQueue.add(jsonRequest);
    }

    protected void getArray(final String endpoint) {
        this.requestArray(Request.Method.GET, endpoint, null);
    }

    protected void postArray(final String endpoint, final JSONObject params) {
        this.requestObject(Request.Method.POST, endpoint, params);
    }

    protected void getObject(final String endpoint) {
        this.requestArray(Request.Method.GET, endpoint, null);
    }

    protected void postObject(final String endpoint, final JSONObject params) {
        this.requestObject(Request.Method.POST, endpoint, params);
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }
}
