package solutions.rollers.collegemessageboard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ssl.StrictHostnameVerifier;

/**
 * Created by nihan on 22-06-2017.
 */

public class CMB {
    static public String branch_code_to_text(String code){
        return "";
    }
    static public String branch_text_to_code(String text){
        return "";
    }
    static public void delete_message(int msg_id, final Context context){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Deleting News and Related Data");
        String url = "http://gietcmb.rollers.solutions/" + "delete.php";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("msg_id",msg_id);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressDialog.dismiss();
                Toast.makeText(context,"Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,AdminNewsFeed.class);
                context.startActivity(i);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressDialog.dismiss();
                Toast.makeText(context,"Delete Failed. Try Again Later..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
