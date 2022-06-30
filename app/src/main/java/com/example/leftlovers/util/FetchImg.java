package com.example.leftlovers.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.leftlovers.R;

import java.io.IOException;
import java.io.InputStream;

public class FetchImg extends Thread {
    private Handler imageHandler = new Handler();
    private ImageView img;
    private String url;
    private Bitmap bitmap;

    public FetchImg(String url, ImageView img) {
        this.url = url;
        this.img = img;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            Log.e("fetching image-url: ", e.getMessage());
        }

        imageHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i("fetching image-url: ", "LOADED");
                img.setImageBitmap(bitmap);
            }
        });
    }
}
