package com.example.gl8080.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class AsyncHttpRequest extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public AsyncHttpRequest(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);

            InputStream in = url.openStream();

            try {
                return BitmapFactory.decodeStream(url.openStream());
            } finally {
                in.close();
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        this.imageView.setImageBitmap(bitmap);
    }
}
