package com.game.stock.stockapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.credentials.PasswordSpecification;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Change_password extends AppCompatActivity implements View.OnClickListener {


    private ProgressDialog mProgressDialog;
    private EditText oldpwd,confirmpwd,nepwd;
    private SharedPreferences sharedPrefre;
    private Button update;
    private String OldPass,confPass,newPass;
    private String UserId,U_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ImageView ImageView01 = ( ImageView) findViewById(R.id.imageView01);
        EditText login_emailid = ( EditText) findViewById(R.id.login_emailid);

        oldpwd = (EditText) findViewById(R.id.oldpwd);
        confirmpwd = ( EditText) findViewById(R.id.confirmpwd);
        nepwd = ( EditText) findViewById(R.id.nepwd);

        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(this);
        ImageView01.setOnClickListener(this);

        InitView();


//        getPasswordUpdate();
    }
    public void InitView()
    {
        sharedPrefre = getSharedPreferences("com.custom.Login", MODE_PRIVATE);
        UserId = sharedPrefre.getString("id", null);
        U_Password = sharedPrefre.getString("password", null);
//        Toast.makeText(getApplicationContext(),""+U_Password,Toast.LENGTH_SHORT).show();

    }

    public void getPasswordUpdate() {

        if(U_Password.equalsIgnoreCase(OldPass)){
        if (newPass.equalsIgnoreCase(confPass)) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.changePassword, new Response.Listener<String>() {
                @Override
                public void onResponse(String ServerResponse) {

                    mProgressDialog = ProgressDialog.show(Change_password.this, null, "Changing Your Password in...", true);
                    mProgressDialog.setMessage("Changing Your Password in...");
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();

                    try {
                        JSONObject jsonObject = new JSONObject(ServerResponse);

                        if (jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))) {
                            Toast.makeText(getApplicationContext(), "" + jsonObject.getString("msg").toString(), Toast.LENGTH_SHORT).show();

//                            jsonObject = new JSONObject(ServerResponse).getJSONObject("info");

                            SharedPreferences.Editor editor = sharedPrefre.edit();
                            editor.putString("password" , confPass);
                            startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));

                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "" + jsonObject.getString("msg").toString(), Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("userid", UserId);
                    params.put("oldpassword", OldPass);
                    params.put("newpassword", newPass);

                    return params;
                }
            };

            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(getApplicationContext(), "Password should be matched", Toast.LENGTH_SHORT).show();
        }
    }else{ Toast.makeText(getApplicationContext(), "Your Old Password is incorrect", Toast.LENGTH_SHORT).show();
            oldpwd.setText("");
        }

    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.imageView01:
                finish();
                break;
            case R.id.update:
                OldPass = oldpwd.getText().toString().trim();
                confPass = confirmpwd.getText().toString().trim();
                newPass = nepwd.getText().toString().trim();

                if(newPass.equalsIgnoreCase("") || confPass.equalsIgnoreCase("") || OldPass.equalsIgnoreCase("")) {

                    Toast.makeText(getApplicationContext(),"All Feilds are Mandatory",Toast.LENGTH_SHORT).show();

                }else {
                    if (U_Password != null) {
                        getPasswordUpdate();
                    }
                }
                break;
        }
    }
}
