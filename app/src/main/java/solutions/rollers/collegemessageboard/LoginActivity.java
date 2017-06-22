package solutions.rollers.collegemessageboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    TextView Error;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MobileAds.initialize(this, "ca-app-pub-5563376976296278~5111566948");
        //MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        username = (EditText) findViewById(R.id.Username);
        password = (EditText) findViewById(R.id.Password);
        Error = (TextView) findViewById(R.id.ErrorMessage);
    }

    public void Login(View view) {
        final String user = username.getText().toString();
        final String pass = password.getText().toString();

        if (!validate_login()) {
            return;
        }

        String url = getResources().getString(R.string.api_url) + "login.php";
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Verifying You Username and Password");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JsonObject jsonObject;
                        JsonParser parser = new JsonParser();
                        jsonObject = (JsonObject) parser.parse(response);
                        progressDialog.dismiss();
                        if (String.valueOf(jsonObject.get("isError")).equals("true")) {
                            String ErrorMessage = jsonObject.get("Error").getAsString();
                            Error.setText(ErrorMessage);
                        } else {
                            if (String.valueOf(jsonObject.get("isAuthenticated")).equals("false")) {
                                String ErrorMessage = jsonObject.get("Message").getAsString();
                                Error.setText(ErrorMessage);
                            } else {
                                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                int user_type = jsonObject.get("user_type").getAsInt();
                                if(user_type == 1){
                                    Intent i = new Intent();
                                    i.setClass(LoginActivity.this,AdminNewsFeed.class);
                                    startActivity(i);
                                }else if(user_type == 2){
                                    Intent i = new Intent();
                                    i.setClass(LoginActivity.this,NewsFeed.class);
                                    startActivity(i);
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                SharedPreferences settings = getSharedPreferences("settings", 0);
                params.put("username",user);
                params.put("password",pass);
                params.put("firebase_device_token", settings.getString("firebase_device_token", "TOKEN_IS_NULL"));
                Log.d("Nihanth", params.toString());
                return params;
            }
        };

        requestQueue.add(request);
    }

    private boolean validate_login() {
        if (username.getText().toString().equals("") && password.getText().toString().equals("")) {
            Error.setText("Please Enter Username and Password");
            return false;
        } else if (username.getText().toString().equals("")) {
            Error.setText("Please Enter Username");
            return false;
        } else if (password.getText().toString().equals("")) {
            Error.setText("Please Enter Password");
            return false;
        }
        return true;
    }
}