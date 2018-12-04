package com.example.vvdn.demoproject.appicontroller;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.vvdn.demoproject.request.DemoReq;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by darshna/2148 at 27/11/2018
 */
public class ApiController implements Response, ImageRequestHandler {
    private String URL_STRING = "http://api.etherscan.io/api?module=account&action=txlist&address=0x4c58a7915ebe42c63c5a3c56c4fb0894a8a7c1fb&startblock=0&endblock=99999999&sort=asc&apikey=YourApiKeyToken";
    private String URL_OBJECT = "https://secureservice.megogrid.com//me_base/MeBase/mebase";
    private String URL_ARRAY_RESP = "http://crypto1.optiaux.com/cryptowallet/test.json";
    private String URL_ARRAY_IMAGE = "http://i.imgur.com/7spzG.png";

    private ApiClient client;
    private WeakReference<Context> mContext;
    private boolean isLive;
    private Response response;
    private ImageRequestHandler imageRequestHandler;
    private int responseType;
    public static final int demoReq = 0;
    public static final int demoReq1 = 1;
    public static final int demoReq2 = 2;
    public static final int demoReq3 = 3;

    /**
     * API constructor
     *
     * @param context      Activity instance
     * @param response     Interface to get back response of API
     * @param responseType requested API response type
     */
    private ApiController(Context context, Response response, ImageRequestHandler imageRequest, int responseType, boolean isProgressShow) {
        this.isLive = true;
        this.mContext = new WeakReference<>(context);
        this.response = response;
        this.imageRequestHandler = imageRequest;
        this.responseType = responseType;
        this.client = new ApiClient(this.mContext.get(), this, this);

    }

    /**
     * API constructor
     *
     * @param context      Activity instance
     * @param response     Interface to get back response of API
     * @param responseType requested API response type
     */
    public ApiController(Context context, Response response, ImageRequestHandler imageRequest, int responseType) {
        this(context, response, imageRequest, responseType, true);
    }

    /**
     * hit json object array
     *
     * @param object request object
     */
    //Using that method we can handle string as well json object request
    public void getDemoReqJsonObject(DemoReq object) {
        if (this.isLive) {
            this.client.communicateVolleyJsonObjectRequest(this.URL_OBJECT, object, this.responseType);
        } else {
            this.response.onResponseObtained(loadJSONFromAsset(this.mContext.get(), "updategetcart"), 3);
        }
    }

    /**
     * hit string request using array
     */
    public void getDemoReqStringDemo() {
        if (this.isLive) {
            this.client.communicateVolleyStringRequest(this.URL_STRING, this.responseType);
        } else {
            this.response.onResponseObtained(loadJSONFromAsset(this.mContext.get(), "updategetcart"), 3);
        }
    }

    /**
     * Hit json array request using array
     */
    public void getDemoReqJsonArray() {
        if (this.isLive) {
            this.client.communicateVolleyStringRequest(this.URL_ARRAY_RESP, this.responseType);
        } else {
            this.response.onResponseObtained(loadJSONFromAsset(this.mContext.get(), "updategetcart"), 3);
        }
    }

    /**
     * Hit image request using volley
     */
    public void getDemoReqImage() {
        if (this.isLive) {
            this.client.communicateVolleyImageRequest(this.URL_ARRAY_IMAGE, this.responseType);
        } else {
            this.response.onResponseObtained(loadJSONFromAsset(this.mContext.get(), "updategetcart"), 3);
        }
    }


    /**
     * @param response     api  success response
     * @param responseType response type
     */
    public void onResponseObtained(String response, int responseType) {
        this.response.onResponseObtained(response, responseType);
    }

    /**
     * @param errorMsg     api error response
     * @param responseType response type
     */
    public void onErrorObtained(String errorMsg, int responseType) {
        this.response.onErrorObtained(errorMsg, responseType);
    }

    @Override
    public void onResponseObtained(Bitmap bitmap, int responseType) {
        this.imageRequestHandler.onResponseObtained(bitmap, responseType);

    }

    /**
     * Method to load response from asset.
     *
     * @param context  class instance
     * @param pathName path to response
     * @return response in string
     */
    private String loadJSONFromAsset(Context context, String pathName) {
        IOException ex;
        String json = null;
        try {
            InputStream is = context.getAssets().open(pathName + ".json");
            byte[] buffer = new byte[is.available()];
            if (is.read(buffer) > 0) {
                String json2 = new String(buffer, "UTF-8");
                try {
                    is.close();
                    json = json2;
                } catch (IOException e) {
                    ex = e;
                    json = json2;
                    Logger.getLogger("").log(Level.INFO, "", ex);
                    return null;
                }
            }
            return json;
        } catch (IOException e2) {
            ex = e2;
            Logger.getLogger("").log(Level.INFO, "", ex);
            return null;
        }
    }
}
