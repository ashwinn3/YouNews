package com.example.ashwin.younews;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
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
    private String[] politicalSources = new String[]{"associated-press", "cnn", "independent",
            "reuters", "the-new-york-times", "the-huffington-post", "the-wall-street-journal", "the-washington-post"};
    private String[] entertainmentSources = new String[]{"buzzfeed", "daily-mail", "entertainment-weekly",
            "mashable", "the-lad-bible"};
    private String[] sportsSources = new String[] {"bbc-sport", "espn",
            "four-four-two", "fox-sports", "nfl-news", "sky-sports-news", "talksport", "the-sport-bible"};
    private String[] technologySources = new String[] {"ars-technica", "recode", "t3n", "techcrunch", "the-verge", "techradar"};
    private String topicForFeed = "Entertainment";
    private Spinner spinner1;
    private TextView utv;
    private int counter = 0;
    //"engadget", "hacker-news"
    //, "espn-cric-info", "football-italia",

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feed_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            setContentView(R.layout.feed);
            counter++;
            feed1 = (TextView) findViewById(R.id.feedtv1);
            new AsyncClass().execute();
        } else {
            setContentView(R.layout.welcome);
        }
        return true;
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
        String[] categories = new String[]{"Politics", "Sports", "Entertainment", "Technology"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
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

    public class AsyncClass extends AsyncTask<ArrayList<String>, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... strings) {
            something = (TextView) findViewById(R.id.callView);
            try {
                ArrayList<String> newRes = new ArrayList<>();
                URL url;
                if (topicForFeed.equals("Politics")) {
                    for (int i = 0; i < politicalSources.length; i++) {
                        StringBuilder result = new StringBuilder();
                        url = new URL("https://newsapi.org/v1/articles?source=" + politicalSources[i] + "&sortBy=top&apiKey=5af1a6765ca944788b515339c1b990ea");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line;
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        rd.close();
                        newRes.add(result.toString());
                    }
                } else if (topicForFeed.equals("Entertainment")) {
                    for (int i = 0; i < entertainmentSources.length; i++) {
                        StringBuilder result = new StringBuilder();
                        url = new URL("https://newsapi.org/v1/articles?source=" + entertainmentSources[i] + "&sortBy=top&apiKey=5af1a6765ca944788b515339c1b990ea");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line;
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        rd.close();
                        newRes.add(result.toString());
                    }
                } else if (topicForFeed.equals("Sports")){
                    for (int i = 0; i < sportsSources.length; i++) {
                        StringBuilder result = new StringBuilder();
                        url = new URL("https://newsapi.org/v1/articles?source=" + sportsSources[i] + "&sortBy=top&apiKey=5af1a6765ca944788b515339c1b990ea");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line;
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        rd.close();
                        newRes.add(result.toString());
                    }
                } else {
                    for (int i = 0; i < technologySources.length; i++) {
                        StringBuilder result = new StringBuilder();
                        url = new URL("https://newsapi.org/v1/articles?source=" + technologySources[i] + "&sortBy=top&apiKey=5af1a6765ca944788b515339c1b990ea");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line;
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        rd.close();
                        newRes.add(result.toString());
                    }
                }
                return newRes;
            } catch (Exception e) {
                ArrayList<String> error = new ArrayList<>();
                error.add(e.toString());
                return error;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> some) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                int length = some.size();
                JSONObject[] objects = new JSONObject[length];
                int count = 0;
                for (String thing: some) {
                    objects[count] = new JSONObject(thing);
                    count++;
                }
                for (int i = 0; i < length; i++) {
                    String source = objects[i].get("source").toString();
                    source.replace("-", " ");
                    feed1.append(source + "\n");
                    JSONArray arr = objects[i].getJSONArray("articles");
                    int newCounter = counter % arr.length();
                    URL url = new URL(((JSONObject) arr.get(newCounter)).get("urlToImage").toString());
                    InputStream is = (InputStream) url.getContent();
                    Drawable d = Drawable.createFromStream(is, "src name");
                    d.setBounds(0, 0, 1000, 600);
                    SpannableString ss2 = new SpannableString("                " + "\n");
                    ss2.setSpan(new ImageSpan(d), 10, 13, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    feed1.append(ss2);
                    StringBuilder s3 = new StringBuilder();
                    for (int p = 0; p < 65; p++) {
                        s3.append("_");
                    }
                    String addText = ((JSONObject) arr.get(newCounter)).get("title").toString() +"\n";
                    SpannableString ss1 = new SpannableString(addText);
                    ss1.setSpan(new RelativeSizeSpan(1.5f), 0, addText.length(), 0);
                    ss1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, addText.length(), 0);
                    String addURL = ((JSONObject) arr.get(newCounter)).get("url").toString();
                    ss1.setSpan(new URLSpan(addURL), 0, addText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    feed1.append(ss1);
                    feed1.append(s3 + "\n\n");
                    feed1.setClickable(true);
                    feed1.setLinksClickable(true);
                    feed1.setMovementMethod(LinkMovementMethod.getInstance());
                }

            } catch (Exception e) {
                feed1.setText(e.toString() + "\n\n");
            }
        }
    }
}
