package solutions.rollers.collegemessageboard;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import solutions.rollers.collegemessageboard.Adapters.NewsAdapter;
import solutions.rollers.collegemessageboard.Models.NewsItem;

public class NewsFeed extends AppCompatActivity {

    private AdView mAdView;
    InterstitialAd mInterstitialAd;
    RecyclerView recyclerView;
    private List<NewsItem> newsItemList = new ArrayList<>();
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        MobileAds.initialize(this, "ca-app-pub-5563376976296278~5111566948");
        //MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdUnitId("ca-app-pub-5563376976296278/4692764547");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

        });


        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        newsAdapter = new NewsAdapter(newsItemList);
        recyclerView.setAdapter(newsAdapter);

        loadMessages();


    }

    private void loadMessages() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading the News");
        progressDialog.setCancelable(false);

        String url = "http://gietcmb.rollers.solutions/" + "load_messages.php";

        RequestParams params = new RequestParams();
        SharedPreferences settings = getSharedPreferences("settings", 0);
        params.put("branch",settings.getString("branch","NULL"));
        params.put("year",settings.getString("year","NULL"));
        params.put("user_type",settings.getInt("user_type",2));
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(response);
                JsonObject object = element.getAsJsonObject();
                int n = object.get("num_records").getAsInt();
                if(n>0){
                    JsonArray messages = object.get("messages").getAsJsonArray();
                    for(int i=0;i<n;i++){
                        String title,message,date_time;
                        int msg_id;
                        JsonObject temp = messages.get(i).getAsJsonObject();
                        title = temp.get("title").getAsString();
                        message= temp.get("message").getAsString();
                        date_time= temp.get("timestamp").getAsString();
                        msg_id= temp.get("msg_id").getAsInt();
                        NewsItem item = new NewsItem(title,message,date_time,msg_id);
                        newsItemList.add(item);
                    }
                    newsAdapter.notifyDataSetChanged();
                }
                //Toast.makeText(NewsFeed.this,response,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


}
