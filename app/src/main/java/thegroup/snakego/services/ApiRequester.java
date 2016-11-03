package thegroup.snakego.services;

import android.content.Context;

import com.android.volley.Request;
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

    private void requestArray(final int method, Context ctx, final String endpoint,
                              JSONArray params) {
        String url = this.baseUrl + endpoint;

        final HttpResultsInterface callback = (HttpResultsInterface) ctx;

        JsonArrayRequest jsonRequest = new JsonArrayRequest(method, url, params,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSuccess(response, method, endpoint);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error, method, endpoint);
                    }
                });

        Volley.newRequestQueue(ctx).add(jsonRequest);
    }

    private void requestObject(final int method, Context ctx, final String endpoint,
                               JSONObject params) {
        String url = this.baseUrl + endpoint;

        final HttpResultsInterface callback = (HttpResultsInterface) ctx;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(method, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response, method, endpoint);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error, method, endpoint);
                    }
                });

        Volley.newRequestQueue(ctx).add(jsonRequest);
    }

    protected void getArray(Context ctx, final String endpoint) {
        this.requestArray(Request.Method.GET, ctx, endpoint, null);
    }

    protected void postArray(Context ctx, final String endpoint, final JSONObject params) {
        this.requestObject(Request.Method.POST, ctx, endpoint, params);
    }

    protected void getObject(Context ctx, final String endpoint) {
        this.requestArray(Request.Method.GET, ctx, endpoint, null);
    }

    protected void postObject(Context ctx, final String endpoint, final JSONObject params) {
        this.requestObject(Request.Method.POST, ctx, endpoint, params);
    }
}
