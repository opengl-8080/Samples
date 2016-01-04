package com.example.gl8080.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        ImageView imageView = (ImageView)this.findViewById(R.id.imageView);
        AsyncHttpRequest req = new AsyncHttpRequest(imageView);
        req.execute("http://hobby.dengeki.com/ss/hobby/uploads/2015/01/469477ec064b80b91ac8626257d3c11b.jpg");
    }
}
