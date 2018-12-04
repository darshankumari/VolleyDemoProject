package com.example.vvdn.demoproject;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.vvdn.demoproject.appicontroller.ApiController;
import com.example.vvdn.demoproject.appicontroller.ImageRequestHandler;
import com.example.vvdn.demoproject.request.DemoReq;
import com.example.vvdn.demoproject.utility.AndroidAppUtils;
import com.example.vvdn.volleydemoproject.R;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements com.example.vvdn.demoproject.appicontroller.Response, ImageRequestHandler {
    private static String TAG = "MainActivity";
    private TextView textView, textView1, textView2, textView3;
    private ImageView mImageView;
    private boolean reqBoolean = false, reqBoolean1 = false, reqBoolean2 = false, reqBoolean3 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.demoText);
        textView1 = (TextView) findViewById(R.id.demoText1);
        textView2 = (TextView) findViewById(R.id.demoText2);
        mImageView = (ImageView) findViewById(R.id.imageView);

        AndroidAppUtils.showProgressDialog(MainActivity.this, getResources().getString(R.string.please_wait_note), true);

        //hit all volley requests
        hitDemoReqJsonObject();
        hitDemoReqString();
        hitDemoReqJsonArray();
        hitDemoReqImage();

    }

    public void forceCrash(View view) {
        Crashlytics.setString("last_UI_action", "logged_in");
        try {
            textView3.setText("Null is here!");
        } catch (Exception e) {
//            Crashlytics.log(e.getMessage());
//            Crashlytics.logException(e);
            Crashlytics.log("Your log");
            Crashlytics.logException(new Throwable("This your not-fatal name"));
            // handle your exception here!
        }
//        throw new RuntimeException("This is a crash");

    }


    /**
     * hit volley json object request.
     */
    private void hitDemoReqJsonObject() {
        ApiController apiController = new ApiController(this, this, this, ApiController.demoReq);
        DemoReq demoReq = new DemoReq();
        apiController.getDemoReqJsonObject(demoReq);
    }

    /**
     * hit volley string request.
     */
    private void hitDemoReqString() {
        ApiController apiController = new ApiController(this, this, this, ApiController.demoReq1);
        apiController.getDemoReqStringDemo();
    }

    /**
     * hit volley json array request.
     */
    private void hitDemoReqJsonArray() {
        ApiController apiController = new ApiController(this, this, this, ApiController.demoReq2);
        apiController.getDemoReqJsonArray();
    }


    /**
     * hit volley image request.
     */
    private void hitDemoReqImage() {
        ApiController apiController = new ApiController(this, this, this, ApiController.demoReq3);
        apiController.getDemoReqImage();
    }


    @Override
    public void onErrorObtained(String strError, int reqNo) {
        AndroidAppUtils.showLog(TAG, "onErrorObtained: " + strError + "\n" + "response code=" + reqNo);
        if (reqNo == ApiController.demoReq) {
            reqBoolean = true;
        } else if (reqNo == ApiController.demoReq1) {
            reqBoolean1 = true;
        } else if (reqNo == ApiController.demoReq2) {
            reqBoolean2 = true;
        }
        AndroidAppUtils.showToast(MainActivity.this, strError);

        if (reqBoolean && reqBoolean1 && reqBoolean2 && reqBoolean3)
            AndroidAppUtils.hideProgressDialog();
    }

    @Override
    public void onResponseObtained(Bitmap bitmap, int reqNo) {
        if (reqNo == ApiController.demoReq3) {
            reqBoolean3 = true;
            if (bitmap != null)
                mImageView.setImageBitmap(bitmap);
        }
        if (reqBoolean && reqBoolean1 && reqBoolean2 && reqBoolean3)
            AndroidAppUtils.hideProgressDialog();
    }

    @Override
    public void onResponseObtained(String response, int reqNo) {
        AndroidAppUtils.showLog(TAG, "onResponseObtained: " + response + "\n\n" + reqNo);
        if (reqNo == ApiController.demoReq) {
            reqBoolean = true;
            textView.setText(response);
        } else if (reqNo == ApiController.demoReq1) {
            reqBoolean1 = true;
            textView1.setText(response);
        } else if (reqNo == ApiController.demoReq2) {
            reqBoolean2 = true;
            textView2.setText(response);
        }
        if (reqBoolean && reqBoolean1 && reqBoolean2 && reqBoolean3)
            AndroidAppUtils.hideProgressDialog();
    }

}
