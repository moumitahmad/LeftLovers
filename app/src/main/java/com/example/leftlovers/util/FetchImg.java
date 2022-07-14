package com.example.leftlovers.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;


import java.io.IOException;
import java.io.InputStream;

public class FetchImg extends Thread {
    private final Handler imageHandler = new Handler();
    private final ImageView img;
    private final String url;
    private Bitmap bitmap;

    public FetchImg(String url, ImageView img) {
        this.url = url;
        this.img = img;
    }

    @Override
    public void run() {
        InputStream inputStream;
        try {
            inputStream = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            Log.e("fetching image-url Error: ", e.getMessage());
        }

        imageHandler.post(() -> {
            img.setImageBitmap(bitmap);
        });
    }
}
