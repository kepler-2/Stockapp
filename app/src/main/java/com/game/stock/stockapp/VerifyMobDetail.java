package com.game.stock.stockapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Created by firemoon on 5/3/18.
 */

public class VerifyMobDetail extends AppCompatActivity implements View.OnClickListener{


private TextView txt_PAN,dateView;
private EditText edt_PAN,edt_name;

private Button btn_submitverification;
        LinearLayout linear_MOBILE;
        // DATE PICKER
        DatePickerDialog.OnDateSetListener date;
private Calendar myCalendar;
private ProgressDialog mProgressDialog;
private String id,name;
private SharedPreferences sharedPreferences_customLogin;
private String pannum,datedob;

@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.verifyaccount_mob);
        TextView txt_MOBILE=(TextView) findViewById(R.id.txt_MOB);
//        TextView txt_MOBILE=(TextView) findViewById(R.id.txt_MOB);
//        TextView txt_BANK=(TextView) findViewById(R.id.txt_Bank);
//        TextView txt_Pan=(TextView) findViewById(R.id.txt_PAN);
//        txt_BANK.setOnClickListener(this);
//        txt_Pan.setOnClickListener(this);
//


//        linear_MOBILE= (LinearLayout) findViewById(R.id.linear_mobile);
//        linear_MOBILE.setBackgroundColor(Color.parseColor("#302f2f"));
//        txt_MOBILE.setTextColor(Color.WHITE);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.bakbtnresize);
        toolbar.setTitle("My Profile");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Navigationdrawer.class));

        finish();
        }
        });
        initView();
        }

@SuppressLint("ResourceAsColor")
public void initView()
        {

        sharedPreferences_customLogin=getSharedPreferences("com.custom.Login", Context.MODE_PRIVATE);
        id = sharedPreferences_customLogin.getString("id",null);
        name = sharedPreferences_customLogin.getString("PANName",null);
        pannum = sharedPreferences_customLogin.getString("pannum",null);
        datedob = sharedPreferences_customLogin.getString("dob",null);



        edt_PAN = (EditText) findViewById(R.id.edt_PAN);
        edt_name = (EditText) findViewById(R.id.edt_name);
        txt_PAN = (TextView) findViewById(R.id.txt_PAN);



        LinearLayout linear_Pan = (LinearLayout) findViewById(R.id.linear_PAN);
        linear_Pan.setBackgroundColor(Color.parseColor("#302f2f"));
        txt_PAN.setTextColor(Color.WHITE);

        dateView = (TextView) findViewById(R.id.edt_DOB);
        dateView.setOnClickListener(this);

        btn_submitverification = (Button) findViewById(R.id.btn_submitverification);
        btn_submitverification.setOnClickListener(this);

        if(pannum.equalsIgnoreCase("")) {}else{
        edt_name.setText(name);
        edt_name.setEnabled(false);
        edt_PAN.setText(pannum);
        edt_PAN.setEnabled(false);
        dateView.setText(datedob);
        dateView.setEnabled(false);
        }
        }



@Override
public void onClick(View view) {

        switch(view.getId())
        {
        case R.id.edt_DOB:

        callDatePicker();
        break;

        case R.id.btn_submitverification:


        if(pannum.equalsIgnoreCase("")) {
        String name = edt_name.getText().toString().trim();
        String pannum = edt_PAN.getText().toString().trim();
        String text_DOB = dateView.getText().toString().trim();
        verificationResult(id, name, pannum, text_DOB);
        }else
        {

        Toast.makeText(getApplicationContext(),"You Cann't put these details many time",Toast.LENGTH_SHORT).show();
        }

                 break;

                case R.id.txt_Bank:
                        Intent intent1=new Intent(VerifyMobDetail.this,VerifyBankDetail.class);
                        startActivity(intent1);
                        break;

                case R.id.txt_PAN:
                        Intent intent2=new Intent(VerifyMobDetail.this,VerifyPANAccount.class);
                        startActivity(intent2);
                           break;


        }
        }

private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateView.setText(sdf.format(myCalendar.getTime()));
        }

public void callDatePicker()
        {
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

@Override
public void onDateSet(DatePicker view, int year, int monthOfYear,
        int dayOfMonth) {
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
        }
        };
        new DatePickerDialog(this, date, myCalendar
        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        }

public void verificationResult(final String ID , final String NAME , final String PANNUM ,final String DOB) {

        if (PANNUM.equalsIgnoreCase("")) {
        Toast.makeText(getApplicationContext(), "Please Insert PAN Card detail", Toast.LENGTH_SHORT).show();
        } else {
        if (DOB.equalsIgnoreCase("")) {
        Toast.makeText(getApplicationContext(), "Please Date Of Birth", Toast.LENGTH_SHORT).show();
        } else {

        mProgressDialog = ProgressDialog.show(this, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.verifyPAN, new Response.Listener<String>() {
@Override
public void onResponse(String ServerResponse) {

        try {
        JSONObject jsonObject = new JSONObject(ServerResponse);

        if (jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))) {

        Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);

        SharedPreferences.Editor editor=sharedPreferences_customLogin.edit();
        editor.putString("PANName",NAME);
        editor.putString("pannum",PANNUM);
        editor.putString("dob",DOB);
        editor.commit();

        startActivity(intent);
        mProgressDialog.dismiss();
        mProgressDialog.cancel();
        finish();

        } else {
        mProgressDialog.dismiss();
        mProgressDialog.cancel();
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

        mProgressDialog.dismiss();


        }
        }) {
@Override
protected Map<String, String> getParams() {

        Map<String, String> params = new HashMap<String, String>();

        params.put(Config.userid, ID);
        params.put("name", NAME);
        params.put("pnum", PANNUM);
        params.put("birthdate", DOB);


        return params;
        }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(VerifyMobDetail.this);
        requestQueue.add(stringRequest);

        }
        }
        }

        }
