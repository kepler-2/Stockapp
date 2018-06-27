package com.game.stock.stockapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Forgot_password extends AppCompatActivity {

    Button btn_submit;
    EditText login_emailid;
    private ProgressDialog mProgressDialog;
    private SharedPreferences sharedPrefre;
    private String UserId;
    // private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        btn_submit=(Button) findViewById(R.id.btn_submit);
        login_emailid=(EditText) findViewById(R.id.login_emailid);

        sharedPrefre = getSharedPreferences("com.custom.Login", MODE_PRIVATE);
        UserId = sharedPrefre.getString("id", null);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.bakbtnresize);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText("Forgot Password");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        setSupportActionBar(toolbar);


//        getSupportActionBar().setDisplayShowTitleEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                final String email = login_emailid.getText().toString();
                if (!isValidEmail(email)) {
                    login_emailid.setError("Invalid Email");
                }
                else {


                    getNewPassword(email);

                }
            }
        });





        }

    private boolean isValidEmail(String email) {


        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



    public void getNewPassword(final String email)
    {
        mProgressDialog = ProgressDialog.show(Forgot_password.this, null,"Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.forgetPassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {

                // Hiding the progress dialog after all task complete.
                mProgressDialog.dismiss();
                mProgressDialog.cancel();

//                        Toast.makeText(Login.this, ServerResponse, Toast.LENGTH_LONG).show();

                try {
                    JSONObject jsonObject=new JSONObject(ServerResponse);

                    if(jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))){

                        Toast.makeText(Forgot_password.this, ""+jsonObject.getString("msg").toString(), Toast.LENGTH_SHORT).show();


                        Intent myIntent = new Intent(Forgot_password.this, Login.class);
                        startActivity(myIntent);


                        /*jsonObject=new JSONObject(ServerResponse).getJSONObject("info");

                        String userId= jsonObject.getString("Id").toString();*/

                        // Sending User Email to another activity using intent.
                        //intent.putExtra(Config.fullname, fullname1);

                    }else
                    {
                        Toast.makeText(getApplicationContext(),""+jsonObject.getString("msg").toString(),Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        mProgressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Forgot_password.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put(Config.userid,"1" );
                params.put(Config.emaillogin, email);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Forgot_password.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}
