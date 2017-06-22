package solutions.rollers.collegemessageboard;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class AddNews extends AppCompatActivity {

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        MobileAds.initialize(this, "ca-app-pub-5563376976296278~5111566948");
        //MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Spinner yearsSpinner = (Spinner) findViewById(R.id.year_spinner);

        String[] years = new String[] { "All", "2014", "2015", "2016", "2017" };

        ArrayAdapter<String> years_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, years);

        yearsSpinner.setAdapter(years_adapter);

        Spinner branchSpinner = (Spinner) findViewById(R.id.branch_spinner);

        String[] items = new String[] { "All Branches", "CSE", "ECE", "EEE" };

        ArrayAdapter<String> branch_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        branchSpinner.setAdapter(branch_adapter);

        branchSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
}
