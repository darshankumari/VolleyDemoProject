package com.example.vvdn.demoproject.appicontroller;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vvdn.demoproject.appicontroller.multipart.MultipartRequest;
import com.example.vvdn.demoproject.utility.AndroidAppUtils;
import com.example.vvdn.demoproject.volleyUtilty.AppSingleton;
import com.example.vvdn.volleydemoproject.R;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by darshna/2148 at 27/11/2018.
 */
public class ApiClient {
    private static String TAG = "ApiClient ";
    protected String message;
    private Response resp;
    private ImageRequestHandler imageRequestHandler;
    private int responseType = 0;
    private WeakReference weakReference;

    /*Response Listener for json string*/
    class ImageRespListener implements ImageLoader.ImageListener {
        ImageRespListener() {
        }

        @Override
        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
            ApiClient.this.imageRequestHandler.onResponseObtained(response.getBitmap(), ApiClient.this.responseType);
            AndroidAppUtils.showLog(TAG, "onResponse: " + response.getBitmap());
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            ApiClient.this.imageRequestHandler.onErrorObtained(error.getMessage(), ApiClient.this.responseType);

            AndroidAppUtils.showLog(TAG, "onErrorResponse: " + error.toString());
        }
    }

    /*Response Listener for json string*/
    class JsonMultipartRespListener implements Listener<NetworkResponse> {
        JsonMultipartRespListener() {
        }

        @Override
        public void onResponse(NetworkResponse response) {
            ApiClient.this.resp.onResponseObtained(new String(response.data), ApiClient.this.responseType);
            AndroidAppUtils.showLog(TAG, "onResponse: " + Arrays.toString(response.data));
        }

    }

    /*Response Listener for json string*/
    class JsonStringRespListener implements Listener<String> {
        JsonStringRespListener() {
        }

        @Override
        public void onResponse(String response) {
            ApiClient.this.resp.onResponseObtained(response, ApiClient.this.responseType);
            AndroidAppUtils.showLog(TAG, "onResponse: " + response);
        }
    }

    /*Response Listener for json object*/
    class JsonObjectRespListener implements Listener<JSONObject> {
        JsonObjectRespListener() {
        }

        public void onResponse(JSONObject response) {
            ApiClient.this.resp.onResponseObtained(response.toString(), ApiClient.this.responseType);
            AndroidAppUtils.showLog(TAG, "onResponse: " + response.toString());
        }

    }


    /*Response Listener for json array*/
    class JsonArrayRespListener implements Listener<JSONArray> {
        JsonArrayRespListener() {
        }

        public void onResponse(JSONArray response) {
            ApiClient.this.resp.onResponseObtained(response.toString(), ApiClient.this.responseType);
            AndroidAppUtils.showLog(TAG, "onResponse: " + response.toString());
        }

    }

    /*Error Listener for requested object*/
    class ErrorResp implements ErrorListener {
        ErrorResp() {
        }

        public void onErrorResponse(VolleyError error) {

            try {
                int statusCode = error.networkResponse.statusCode;
                NetworkResponse response = error.networkResponse;
                ApiClient.this.resp.onErrorObtained(new String(response.data), ApiClient.this.responseType);
                AndroidAppUtils.showLog("ErrorListener", "ErrorListener value=" + statusCode + " " + new String(response.data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * class constructor
     *
     * @param context      activity instance
     * @param res          JSONArray,JSONObject and string request response listener
     * @param imageRequest Image request response listener
     */
    public ApiClient(Context context, Response res, ImageRequestHandler imageRequest) {
        this.weakReference = new WeakReference(context);
        this.resp = res;
        this.imageRequestHandler = imageRequest;
    }



    /*Communication to Volley to get String response*/
    public void communicateVolleyStringRequest(String url, int responseType) {
        this.responseType = responseType;
        try {
            RequestQueue queue = Volley.newRequestQueue((Context) this.weakReference.get());
            StringRequest jsonArrayRequest = new StringRequest(url, createJsonStringRespListener(), createErrorListener());
            jsonArrayRequest.setShouldCache(false);
            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(jsonArrayRequest);
            AndroidAppUtils.showLog(TAG, "Communicate url: " + url);
        } catch (Exception e) {
            AndroidAppUtils.showLog(TAG, "Communicate: " + e);
        }
    }


    /*Communication to Volley to get JSONObject response*/
    public void communicateVolleyJsonObjectRequest(String url, Object value, int responseType) {
        this.responseType = responseType;
        try {
            RequestQueue queue = Volley.newRequestQueue((Context) this.weakReference.get());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, getJsonObject(value), createJsonObjectRespListener(), createErrorListener());
            jsonObjectRequest.setShouldCache(false);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(jsonObjectRequest);
            AndroidAppUtils.showLog(TAG, "Communicate url: " + url);
        } catch (Exception e) {
            AndroidAppUtils.showLog(TAG, "Communicate: " + e);
        }
    }


    /*Communication to Volley to get JSONArray response*/
    public void communicateVolleyJsonArrayRequest(String url, JSONArray value, int responseType) {
        this.responseType = responseType;
        try {
            RequestQueue queue = Volley.newRequestQueue((Context) this.weakReference.get());
            // Adding JsonObject request to request queue
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, createJsonArrayRespListener(), createErrorListener());
            jsonArrayRequest.setShouldCache(false);
            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            AppSingleton.getInstance((Context) this.weakReference.get()).addToRequestQueue(jsonArrayRequest, REQUEST_TAG);

            queue.add(jsonArrayRequest);
            AndroidAppUtils.showLog(TAG, "Communicate url: " + url);
        } catch (Exception e) {
            AndroidAppUtils.showLog(TAG, "Communicate: " + e);
        }
    }

    /*Communication to Volley to get JSONArray response*/
    public void communicateVolleyImageRequest(String url, int responseType) {
        ImageLoader imageLoader = AppSingleton.getInstance((Context) this.weakReference.get()).getImageLoader();
        this.responseType = responseType;
        try {
            imageLoader.get(url, createImageRespListener());
            AndroidAppUtils.showLog(TAG, "Communicate url: " + url);
        } catch (Exception e) {
            AndroidAppUtils.showLog(TAG, "Communicate: " + e);
        }
    }


    /**
     * json string response listener method
     *
     * @return
     */
    private Listener<String> createJsonStringRespListener() {
        return new JsonStringRespListener();
    }

    /**
     * json object response listener method
     *
     * @return
     */
    private Listener<JSONObject> createJsonObjectRespListener() {
        return new JsonObjectRespListener();
    }

    /**
     * json array response listener method
     *
     * @return
     */
    private Listener<JSONArray> createJsonArrayRespListener() {
        return new JsonArrayRespListener();
    }

    /**
     * json string response listener method
     *
     * @return
     */
    private ImageRespListener createImageRespListener() {
        return new ImageRespListener();
    }

    /**
     * request error listener method
     *
     * @return
     */
    private ErrorListener createErrorListener() {
        return new ErrorResp();
    }

    private JSONObject getJsonObject(Object obj) {
        Exception e;
        String json = new Gson().toJson(obj);
        JSONObject jsonObject = null;
        try {
            JSONObject jsonObject2 = new JSONObject(json);
            try {
                this.message = json.toString();
                jsonObject = jsonObject2;
            } catch (Exception e2) {
                e = e2;
                jsonObject = jsonObject2;
                AndroidAppUtils.showLog(TAG, "getJsonObject: " + json + "\n" + e);
                return jsonObject;
            }
        } catch (JSONException e3) {
            e = e3;
            AndroidAppUtils.showLog(TAG, "getJsonObject: " + json + "\n" + e);
            return jsonObject;
        }
        AndroidAppUtils.showLog(TAG, "getJsonObject: " + json);
        return jsonObject;
    }
}
