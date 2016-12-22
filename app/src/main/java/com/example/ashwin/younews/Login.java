package com.example.ashwin.younews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {

    private TextView something;
    private Spinner sp1;
    private RadioButton rb1;
    private RadioButton rb2;
    private TextView tv;
    private TextView feed1;
    private String[] politicalSources = new String[]{"associated-press", "cnn", "independent",
            "reuters", "the-new-york-times", "the-huffington-post", "the-wall-street-journal"};
    private String topicForFeed = "Entertainment";
    private Spinner spinner1;
    private TextView utv;

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
    }

    public void toFeed(View v) {
        setContentView(R.layout.feed);
        feed1 = (TextView) findViewById(R.id.feedtv1);
        new AsyncClass().execute();
        rb1.setChecked(true);
        if (rb1.isChecked()) {
            topicForFeed = spinner1.getSelectedItem().toString();
        } else if (rb2.isChecked()) {
            topicForFeed = utv.getText().toString();
        }
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
        rb1 = (RadioButton) v.findViewById(R.id.RadioButton1);
        rb2 = (RadioButton) v.findViewById(R.id.radioButton2);
        spinner1 = (Spinner) findViewById(R.id.spinner1);

    }

    public void topicChosen(View v) {
        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp1.setVisibility(View.INVISIBLE);
        tv = (TextView) findViewById(R.id.tv10);
        tv.setVisibility(View.VISIBLE);
        rb1 = (RadioButton) v.findViewById(R.id.RadioButton1);
        rb2 = (RadioButton) v.findViewById(R.id.radioButton2);
        utv = (TextView) findViewById(R.id.tv10);

    }

    public class AsyncClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            something = (TextView) findViewById(R.id.callView);
            try {
                StringBuilder result = new StringBuilder();
                URL url;
                if (topicForFeed.equals("Politics")) {
                    url = new URL("https://newsapi.org/v1/articles?source=" + politicalSources[0] + "&sortBy=latest&apiKey=5af1a6765ca944788b515339c1b990ea");
                } else if (topicForFeed.equals("Entertainment")) {
                    url = new URL("https://newsapi.org/v1/articles?source=" + politicalSources[0] + "&sortBy=latest&apiKey=5af1a6765ca944788b515339c1b990ea");
                } else {
                    url = new URL("https://newsapi.org/v1/articles?source=techcrunch&sortBy=latest&apiKey=5af1a6765ca944788b515339c1b990ea");
                }
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
                JSONArray objArr = obj.getJSONArray("articles");
                //feed1.setText(obj.get("source").toString());
                //JSONObject otherObj = (JSONObject) objArr.get(0);
                String text = "<body><font size=20>"+ obj.get("source").toString() + "</font></body>";
                Spanned spanned1 = Html.fromHtml(text);
                feed1.setText(spanned1);
                feed1.append( "\n" + ((JSONObject) objArr.get(0)).get("title").toString());
            } catch (Exception e) {
                feed1.setText(e.toString());
            }
        }
    }

}
