package com.example.vvdn.demoproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.vvdn.demoproject.appicontroller.multipart.MultipartRequest;
import com.example.vvdn.volleydemoproject.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class UploadFileOnServer extends AppCompatActivity {
    private ImageView imageView;
    private static final String TAG = "UploadFileOnServer";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
//        String fileUri = "";
//        if (getApplicationContext().getPackageManager().hasSystemFeature(
//                PackageManager.FEATURE_CAMERA)) {
//            // Open default camera
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//
//            // start the image capture Intent
//            startActivityForResult(intent, 100);
//
//        } else {
//            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_SHORT).show();
//        }


        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    100);
        } else {
            startCamera();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                startCamera();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }

    }

    /**
     * start camera
     */
    private void startCamera() {
        Intent cameraIntent = new
                Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 100);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (data != null) {
                Uri selectedImage = data.getData();
                Bitmap bitmap = null;
                try {
                    Log.i(TAG, "onActivityResult: " + selectedImage);
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    Log.i(TAG, "onActivityResult: " + bitmap + "\n" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
                    imageView.setImageBitmap(bitmap);


                    // Cursor to get image uri to display

//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                    assert selectedImage != null;
//                    Cursor cursor = getContentResolver().query(selectedImage,
//                            filePathColumn, null, null, null);
//                    Objects.requireNonNull(cursor).moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String picturePath = cursor.getString(columnIndex);
//                    cursor.close();

//                    Bitmap b = BitmapFactory.decodeFile(picturePath);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();


                    String url = "http://192.168.1.100/api/postfile";
                    File f = new File(UploadFileOnServer.this.getCacheDir(), "filename.jpg");
                    f.createNewFile();
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(byteArray);
                    fos.flush();
                    fos.close();
                    MultipartRequest multipartRequest = new MultipartRequest(url, null, f, new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            try {
                                System.out.println("Networkonse " + new String(response.data, "UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(UploadFileOnServer.this, "Upload successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(UploadFileOnServer.this, "Upload failed!\r\n" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    int socketTimeout = 30000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    multipartRequest.setRetryPolicy(policy);
                    RequestQueue queue = Volley.newRequestQueue((Context) UploadFileOnServer.this);
                    queue.add(multipartRequest);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                Log.i(TAG, "onActivityResult error: ");
            System.out.println("UploadFileOnServer.onActivityResult error");


        }
    }
}
