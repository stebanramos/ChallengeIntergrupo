package com.stebanramos.challengeintergrupo.utilies;

import static com.stebanramos.challengeintergrupo.utilies.Urls.SEARCH_TOKEN_URL;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetToken extends AsyncTask<String, Integer, Boolean> {
    private final String TAG = "GetToken";

    public AsynResponse delegate = null;
    private Context context;
    private boolean result = false;

    public GetToken(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Log.i(TAG, "doInBackground() context " + context);

        try {
            Uri baseUri = Uri.parse(SEARCH_TOKEN_URL);
            Uri.Builder builder = baseUri.buildUpon();

            Log.i(TAG, "doInBackground() baseUri " + builder);

            VolleySingleton.getInstance(context).addToRequestQueue(

                    new JsonObjectRequest(Request.Method.GET, SEARCH_TOKEN_URL, null,

                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {

                                        Log.i(TAG, "doInBackground() onResponse " + response);

                                        if (response.isNull("auth_token")) {
                                            result = false;
                                            delegate.processFinish(false);
                                        } else {
                                            String auth_token = response.get("auth_token").toString();
                                            Log.i(TAG, "doInBackground() onResponse auth_token = " + auth_token);

                                            Preferences.set_str(context, "auth_token",auth_token);
                                            result = true;
                                            delegate.processFinish(true);
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        result = false;
                                    }
                                }
                            },
                            new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    result = false;
                                }
                            }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Log.i(TAG, "- getHeaders");
                            Map<String, String> params = new HashMap<>();
                            params.put("api-token", "DrXzRO0bvpQnlb7nq853oisu9BDuiepid3xbNCEgCCJChf480ymQ0tWcGAJWu2x_a3s");
                            params.put("user-email", "juanramosiam@gmail.com");
                            return params;
                        }
                    }
            );

        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }


}
