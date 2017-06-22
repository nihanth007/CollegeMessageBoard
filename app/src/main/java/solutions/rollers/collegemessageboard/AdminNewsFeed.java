package solutions.rollers.collegemessageboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AdminNewsFeed extends AppCompatActivity {

    private AdView mAdView;
    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_news_feed);

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
    }

    public void AddNews(View view) {
        Intent i = new Intent();
        i.setClass(AdminNewsFeed.this,AddNews.class);
        startActivity(i);
    }
}
