package com.example.vvdn.demoproject.appicontroller;

import android.graphics.Bitmap;

public interface ImageRequestHandler {
    void onErrorObtained(String str, int i);

    void onResponseObtained(Bitmap bitmap, int i);

}
