package com.stebanramos.challengeintergrupo.viewModels;

import static com.stebanramos.challengeintergrupo.utilies.Urls.SEARCH_COUNTRIES_URL;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.stebanramos.challengeintergrupo.models.Country;
import com.stebanramos.challengeintergrupo.utilies.Preferences;
import com.stebanramos.challengeintergrupo.utilies.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewModel extends ViewModel {
    private final String TAG = "MainViewModel";

    private RequestQueue mQueue;
    private List<Country> countryList;
    private MutableLiveData<List<Country>> muCountryList;

    public LiveData<List<Country>> getData(Context context) {
        Log.i(TAG, "getData()");

        if (muCountryList == null){
            muCountryList = new MutableLiveData<>();
            loadData(context);
        }
        return muCountryList;
    }

    private void loadData(Context context) {
        Log.i(TAG, "loadData()");

        try {
            countryList = new ArrayList<>();

            VolleySingleton.getInstance(context).addToRequestQueue(

                    new JsonArrayRequest(Request.Method.GET, SEARCH_COUNTRIES_URL, null,

                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
                                        Log.i(TAG, "loadData() onResponse " + response);
                                        JSONObject jsonObject;
                                        for (int i = 0; i < response.length(); i++) {
                                            jsonObject = response.getJSONObject(i);

                                            String nombre = jsonObject.get("country_name").toString();
                                            Log.i(TAG, "loadData() nombre " + nombre);

                                            countryList.add(new Country(nombre, "", ""));
                                        }

                                        muCountryList.setValue(countryList);


                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    })
                    {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Log.i(TAG, "- getHeaders");
                            Map<String, String> params = new HashMap<>();
                            params.put("Authorization", "Bearer " + Preferences.get_str(context, "auth_token"));
                            return params;
                        }
                    }
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
