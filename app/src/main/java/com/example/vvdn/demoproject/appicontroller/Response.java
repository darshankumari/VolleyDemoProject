package com.example.vvdn.demoproject.appicontroller;

public interface Response {
    void onErrorObtained(String str, int i);

    void onResponseObtained(String response, int i);

}
