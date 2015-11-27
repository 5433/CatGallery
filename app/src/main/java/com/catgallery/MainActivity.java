package com.catgallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private HashMap<String, String[]> catJSON = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String  tag_string_req = "string_req";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_GET_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try{
                    JSONObject jObj = new JSONObject(response);
                    JSONArray ja = jObj.getJSONArray("cats");
                    for (int i=0; i < ja.length(); i++)
                    {
                        try {
                            JSONObject jsonObject = ja.getJSONObject(i);
                            // Pulling items from the array
                            String data[] = new String[11];
                            data[0] = jsonObject.getString("breed");
                            data[1] = jsonObject.getString("legs");
                            data[2] = jsonObject.getString("prefered-food");
                            data[3] = jsonObject.getString("colour");
                            data[4] = jsonObject.getString("size");
                            data[5] = jsonObject.getString("whiskers");
                            //Pulling image object
                            JSONObject imageJObject = jsonObject.getJSONObject("image");
                            data[6] = imageJObject.getString("xxhdpi");
                            data[7] = imageJObject.getString("xhdpi");
                            data[8] = imageJObject.getString("hdpi");
                            data[9] = imageJObject.getString("mdpi");
                            data[10] = imageJObject.getString("ldpi");
                            catJSON.put(data[0],data);
                        } catch (JSONException e) {
                            // Oops
                        }
                    }
                    Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                    intent.putExtra("cat_json",catJSON);
                    startActivity(intent);
                    finish();
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "JSON Load Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }



}
