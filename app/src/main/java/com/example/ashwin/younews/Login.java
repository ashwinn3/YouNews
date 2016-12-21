package com.example.ashwin.younews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private TextView something;
    private Spinner sp1;
    private RadioButton rb1;
    private RadioButton rb2;
    private TextView tv;
    private TextView feed1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void LoginClicked(View v) {
        setContentView(R.layout.welcome);
    }

    public void callClick(View v) {
        something = (TextView) findViewById(R.id.callView);
        new AsyncClass().execute();
        //something.setText(someI.toString());
        //something.append("hi");
    }

    public void toFeed(View v) {
        setContentView(R.layout.feed);
        feed1 = (TextView) findViewById(R.id.feedtv1);
        new AsyncClass().execute();
    }

    public void categoryChosen(View v) {
        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp1.setVisibility(View.VISIBLE);
        String[] categories = new String[] {"Politics", "Sports", "Entertainment"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);
        sp1.setAdapter(adapter);
        tv = (TextView) findViewById(R.id.tv10);
        tv.setVisibility(View.INVISIBLE);
    }

    public void topicChosen(View v) {
        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp1.setVisibility(View.INVISIBLE);
        tv = (TextView) findViewById(R.id.tv10);
        tv.setVisibility(View.VISIBLE);
    }

    public class AsyncClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            something = (TextView) findViewById(R.id.callView);
            try {
                StringBuilder result = new StringBuilder();
                URL url = new URL("https://newsapi.org/v1/articles?source=techcrunch&sortBy=latest&apiKey=5af1a6765ca944788b515339c1b990ea");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();
                return result.toString();
            } catch (Exception e) {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String some) {
            try {
                JSONObject obj = new JSONObject(some);
                feed1.setText(obj.get("articles").toString());
            } catch (Exception e) {
                feed1.setText(e.toString());
            }
        }
    }

}
