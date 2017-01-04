package com.example.ashwin.younews;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.util.Arrays;
import java.util.HashMap;


public class Login extends AppCompatActivity {

    private TextView something;
    private Spinner sp1;
    private RadioButton rb1;
    private RadioButton rb2;
    private TextView tv;
    private ListView feed1;
    private ListView list1;
    private String[] politicalSources = new String[]{"associated-press", "cnn", "independent",
            "reuters", "the-new-york-times", "the-huffington-post", "the-wall-street-journal", "the-washington-post"};
    private String[] entertainmentSources = new String[]{"buzzfeed", "daily-mail", "entertainment-weekly",
            "mashable", "the-lad-bible"};
    private String[] sportsSources = new String[] {"bbc-sport", "espn",
            "four-four-two", "fox-sports", "nfl-news", "sky-sports-news", "talksport", "the-sport-bible"};
    private String[] technologySources = new String[] {"ars-technica", "recode", "t3n", "techcrunch", "the-verge", "techradar"};
    private String topicForFeed = "Entertainment";
    private String[] allPublications = concatAll(politicalSources, entertainmentSources, sportsSources, technologySources);
    private Spinner spinner1;
    private TextView utv;
    private int counter = 0;
    private boolean catChosen;
    private ProgressBar spinner;
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

    private boolean aBool = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_favorite) {
            setContentView(R.layout.feed);
            counter++;
            feed1 = (ListView) findViewById(R.id.feedtv1);
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
        if (catChosen) {
            setContentView(R.layout.feed);
            feed1 = (ListView) findViewById(R.id.feedtv1);
            new AsyncClass().execute();
            topicForFeed = spinner1.getSelectedItem().toString();
        } else {
            setContentView(R.layout.publications);
            list1 = (ListView) findViewById(R.id.lister);
            new AsyncClass().execute();
            topicForFeed = "publication";
        }
    }

    public void categoryChosen(View v) {
        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp1.setVisibility(View.VISIBLE);
        String[] categories = new String[]{"Politics", "Sports", "Entertainment", "Technology"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        sp1.setAdapter(adapter);
        rb1 = (RadioButton) v.findViewById(R.id.RadioButton1);
        rb2 = (RadioButton) v.findViewById(R.id.radioButton2);
        rb1.setChecked(true);
        catChosen = true;
        spinner1 = (Spinner) findViewById(R.id.spinner1);

    }

    public void topicChosen(View v) {
        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp1.setVisibility(View.INVISIBLE);
        rb1 = (RadioButton) v.findViewById(R.id.RadioButton1);
        rb2 = (RadioButton) v.findViewById(R.id.radioButton2);
        rb2.setChecked(true);
        catChosen = false;
    }

    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
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
                } else if (topicForFeed.equals("Technology")) {
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
                } else {
                    StringBuilder result = new StringBuilder();
                    url = new URL("https://newsapi.org/v1/sources");
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
                return newRes;
            } catch (Exception e) {
                ArrayList<String> error = new ArrayList<>();
                error.add(e.toString());
                return error;
            }
        }

        private HashMap<String, String> niceToReal = new HashMap<>();

        @Override
        protected void onPreExecute() {
            spinner = (ProgressBar)findViewById(R.id.progressBar1);
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<String> some) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            spinner = (ProgressBar)findViewById(R.id.progressBar1);
            spinner.setVisibility(View.GONE);
            if (topicForFeed.equals("publication")) {
                try {
                    AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView parent, View v, int position, long id) {
                            Log.d("creator", parent.getItemAtPosition(position).toString());
                            setContentView(R.layout.publicationfeed);
                            feed1 = (ListView) findViewById(R.id.feedtv1);
                            try {
                                StringBuilder result = new StringBuilder();
                                URL url = new URL("https://newsapi.org/v1/articles?source=" + niceToReal.get(parent.getItemAtPosition(position).toString()) + "&sortBy=top&apiKey=5af1a6765ca944788b515339c1b990ea");
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String line;
                                while ((line = rd.readLine()) != null) {
                                    result.append(line);
                                }
                                rd.close();
                                JSONArray arts = (new JSONObject(result.toString())).getJSONArray("articles");
                                spinner = (ProgressBar)findViewById(R.id.progressBar1);
                                spinner.setVisibility(View.GONE);
                                //feed1.append(parent.getItemAtPosition(position).toString() + "\n\n");
                                SpannableString[] adapt = new SpannableString[arts.length()];
                                final HashMap<Integer, String> numToURL = new HashMap<>();
                                for (int y = 0; y < arts.length(); y++) {
                                    SpannableString fSpan = new SpannableString("\n" + parent.getItemAtPosition(position).toString() + "\n\n");
                                    URL url1 = new URL(((JSONObject) arts.get(y)).get("urlToImage").toString());
                                    InputStream is = (InputStream) url1.getContent();
                                    Drawable d = Drawable.createFromStream(is, "src name");
                                    d.setBounds(0, 0, 1000, 600);
                                    SpannableString ss2 = new SpannableString("                " + "\n");
                                    ss2.setSpan(new ImageSpan(d), 10, 13, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                    //feed1.append(ss2);
                                    fSpan = new SpannableString(TextUtils.concat(fSpan, ss2));
                                    StringBuilder s3 = new StringBuilder();
                                    /*
                                    for (int p = 0; p < 65; p++) {
                                        s3.append("_");
                                    }*/
                                    String addText = ((JSONObject) arts.get(y)).get("title").toString() + "\n";
                                    SpannableString ss1 = new SpannableString(addText);
                                    ss1.setSpan(new RelativeSizeSpan(1.5f), 0, addText.length(), 0);
                                    ss1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, addText.length(), 0);
                                    String addURL = ((JSONObject) arts.get(y)).get("url").toString();
                                    ss1.setSpan(new URLSpan(addURL), 0, addText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    numToURL.put(y, addURL);
                                    //feed1.append(ss1);
                                    //feed1.append(s3 + "\n\n");
                                    feed1.setClickable(true);
                                    fSpan = new SpannableString(TextUtils.concat(fSpan, ss1, s3 + "\n"));
                                    adapt[y] = fSpan;
                                    //feed1.setMovementMethod(LinkMovementMethod.getInstance());
                                }
                                ArrayAdapter arrayForList = new ArrayAdapter(Login.this, android.R.layout.simple_list_item_1, adapt);
                                feed1.setAdapter(arrayForList);
                                AdapterView.OnItemClickListener meth2 = new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                                        Uri uri = Uri.parse(numToURL.get(i));
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        startActivity(intent);
                                    }
                                };
                                feed1.setOnItemClickListener(meth2);
                            } catch (Exception e) {
                                Log.d("creator", e.toString());
                            }
                        }
                    };
                    JSONArray pubs = (new JSONObject(some.get(0))).getJSONArray("sources");
                    String[] adapt = new String[pubs.length()];
                    for (int i = 0; i < pubs.length(); i++) {
                        JSONObject newOne = pubs.getJSONObject(i);
                        adapt[i] = (String) newOne.get("name");
                        niceToReal.put(adapt[i], (String) newOne.get("id"));
                    }
                    ArrayAdapter arrayForList = new ArrayAdapter(Login.this, android.R.layout.simple_list_item_1, adapt);
                    list1.setAdapter(arrayForList);
                    list1.setOnItemClickListener(mMessageClickedHandler);
                } catch (Exception e) {
                    //feed1.setText(e.toString());
                    Log.d("creator", "error1");
                }
            } else {
                try {
                    int length = some.size();
                    JSONObject[] objects = new JSONObject[length];
                    int count = 0;
                    for (String thing : some) {
                        objects[count] = new JSONObject(thing);
                        count++;
                    }
                    SpannableString[] adapt2 = new SpannableString[length];
                    final HashMap<Integer, String> numToURL = new HashMap<>();
                    for (int i = 0; i < length; i++) {
                        String source = objects[i].get("source").toString();
                        source.replace("-", " ");
                        //feed1.append(source + "\n");
                        SpannableString f2Span = new SpannableString("\n" + source + "\n");
                        JSONArray arr = objects[i].getJSONArray("articles");
                        int newCounter = counter % arr.length();
                        URL url = new URL(((JSONObject) arr.get(newCounter)).get("urlToImage").toString());
                        InputStream is = (InputStream) url.getContent();
                        Drawable d = Drawable.createFromStream(is, "src name");
                        d.setBounds(0, 0, 1000, 600);
                        SpannableString ss2 = new SpannableString("                " + "\n");
                        ss2.setSpan(new ImageSpan(d), 10, 13, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        //feed1.append(ss2);
                        //f2Span = (SpannableString) TextUtils.concat(f2Span, ss2);
                        //String f2Span1 = TextUtils.concat(f2Span, ss2).toString();
                        f2Span = new SpannableString(TextUtils.concat(f2Span, ss2));
                        StringBuilder s3 = new StringBuilder();
                        /*
                        for (int p = 0; p < 65; p++) {
                            s3.append("_");
                        }*/
                        String addText = ((JSONObject) arr.get(newCounter)).get("title").toString() + "\n";
                        SpannableString ss1 = new SpannableString(addText);
                        ss1.setSpan(new RelativeSizeSpan(1.5f), 0, addText.length(), 0);
                        ss1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, addText.length(), 0);
                        String addURL = ((JSONObject) arr.get(newCounter)).get("url").toString();
                        ss1.setSpan(new URLSpan(addURL), 0, addText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        numToURL.put(i, addURL);
                        //feed1.append(ss1);
                        //feed1.append(s3 + "\n\n");
                        //String finale = TextUtils.concat(f2Span, ss1, s3 + "\n\n").toString();
                        f2Span = new SpannableString(TextUtils.concat(f2Span, ss1, s3 + "\n"));
                        adapt2[i] = f2Span;
                        feed1.setClickable(true);
                        //feed1.setLinksClickable(true);
                        //feed1.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    ArrayAdapter arrayForList = new ArrayAdapter(Login.this, android.R.layout.simple_list_item_1, adapt2);
                    feed1.setAdapter(arrayForList);
                    AdapterView.OnItemClickListener meth1 = new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                            Uri uri = Uri.parse(numToURL.get(i));
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    };
                    feed1.setOnItemClickListener(meth1);
                } catch (Exception e) {
                    //feed1.setText(e.toString() + "\n\n");
                    Log.d("creator", e.toString());
                }
            }
        }
    }
}
