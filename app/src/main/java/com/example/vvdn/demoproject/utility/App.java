package com.example.vvdn.demoproject.utility;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.StrictMode;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.crashlytics.android.Crashlytics;
import com.example.vvdn.demoproject.volleyUtilty.LruBitmapCache;

import io.fabric.sdk.android.Fabric;

/**
 * Created by darshna/2148 at 27/11/2018
 */
public class App extends Application {

    public final String TAG = App.class
            .getSimpleName();

    private RequestQueue mRequestQueue = null;
    private ImageLoader mImageLoader;
    public static App mInstance;
    private PackageInfo packageInfo;
    private ActivityManager activityManager;
    private ConnectivityManager connManager;
    private int lastStop;


    @Override
    public void onCreate() {
        mInstance = this;
//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(true)
//                .build();
//        Fabric.with(fabric);
//        Crashlytics.getInstance().crash();
//        new LinuxSecureRandom(); // init proper random number generator
        // TODO review this
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder().detectAll().permitDiskReads().permitDiskWrites().penaltyLog().build());
        super.onCreate();
        AndroidAppUtils.showLog(TAG, "onCreate: application context");
        packageInfo = packageInfoFromContext(this);
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }


    /***
     * This method is used to get the packge name of the application
     * @param context
     * @return
     */
    private PackageInfo packageInfoFromContext(final Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static synchronized App getInstance() {
        return mInstance;
    }



    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

}
