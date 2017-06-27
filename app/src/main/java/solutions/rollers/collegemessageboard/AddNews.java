package solutions.rollers.collegemessageboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;

public class AddNews extends AppCompatActivity {

    String y,b;
    CheckBox send_notification;
    EditText title,message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        MobileAds.initialize(this, "ca-app-pub-5563376976296278~5111566948");
        //MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        send_notification = (CheckBox) findViewById(R.id.send_notification);
        title = (EditText)findViewById(R.id.news_title);
        message = (EditText) findViewById(R.id.news_message);
        Spinner yearsSpinner = (Spinner) findViewById(R.id.year_spinner);

        Date d = new Date();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String y1=String.valueOf(year);
        String y2=String.valueOf(year-1);
        String y3=String.valueOf(year-2);
        String y4=String.valueOf(year-3);
        String y5=String.valueOf(year-4);
        String[] years = new String[] { "All Years", y5, y4, y3, y2, y1};

        ArrayAdapter<String> years_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, years);

        yearsSpinner.setAdapter(years_adapter);

        yearsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                y = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Spinner branchSpinner = (Spinner) findViewById(R.id.branch_spinner);

        String[] items = new String[] { "All Branches", "CSE", "ECE", "EEE", "Mechanical", "Automobile", "Civil", "Mining"};

        ArrayAdapter<String> branch_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        branchSpinner.setAdapter(branch_adapter);

        branchSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                b = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });








    }

    public void Add_News(View view) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Adding News and Related Data");

        String url = "http://gietcmb.rollers.solutions/" + "add_message.php";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("year",y);
        params.put("branch",b);
        params.put("title",title.getText().toString());
        params.put("message",message.getText().toString());
        boolean flag = send_notification.isChecked();
        params.put("send_notification",flag);

        Toast.makeText(this, params.toString(), Toast.LENGTH_SHORT).show();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressDialog.dismiss();
                Toast.makeText(AddNews.this,"News Sent Successfully", Toast.LENGTH_SHORT).show();
                //Toast.makeText(AddNews.this, new String(responseBody), Toast.LENGTH_LONG).show();
                Intent i = new Intent(AddNews.this,AdminNewsFeed.class);
                AddNews.this.startActivity(i);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressDialog.dismiss();
                Toast.makeText(AddNews.this,"Sending News Failed. Try Again Later..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
