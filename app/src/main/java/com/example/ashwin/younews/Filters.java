package com.example.ashwin.younews;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ashwin on 12/19/16.
 */

public class Filters extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }

    private TextView something;

    public void callClick(View v) {
        something = (TextView) findViewById(R.id.callView);
        try {
            URL url = new URL("https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=5af1a6765ca944788b515339c1b990ea");
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            something.setText(is.toString());
        } catch (Exception e) {
            something.setText(e.toString());
        }
    }
}
