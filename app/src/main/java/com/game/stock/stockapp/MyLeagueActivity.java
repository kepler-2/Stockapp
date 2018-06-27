package com.game.stock.stockapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Adapter.LeagueListAdapter;
import Bean.DataSet;


/**
 * Created by firemoon on 13/2/18.
 */

public class MyLeagueActivity extends AppCompatActivity implements View.OnClickListener{



/*   ----INSTA MOJO (JUS PAY) INTEGRATION----    */

    /*    -----   JUS PAY -------   */





    private ListView listView_LeagueList;
    private List<DataSet> leaugeList = new ArrayList<DataSet>();
    private LeagueListAdapter leagueListAdapter;
    private TextView _addMoreLeague;
    private SharedPreferences sharedPreferences_customLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myleauge);

        getUIid();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.bakbtnresize);
        toolbar.setTitle("My League");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sharedPreferences_customLogin=getSharedPreferences("com.custom.Login", Context.MODE_PRIVATE);

        String UserId = sharedPreferences_customLogin.getString("id",null);
        myLeagueDetail(UserId);

    }

    public void myLeagueDetail(final String userId)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.leaguelist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                            JSONArray jsonArrayRequest = jsonObject_LeaugeList.getJSONArray("info");

                    for(int i=0;i<jsonArrayRequest.length();i++){
                        try {
                            DataSet dataSet = new DataSet();
                            JSONObject _object = jsonArrayRequest.getJSONObject(i);
                    dataSet.setLeagueID(_object.getString("LeagueID"));
                    dataSet.setLeagueName(_object.getString("LeagueName"));
                    dataSet.setEntryFees(_object.getString("EntryFees"));
                    dataSet.setKingName(_object.getString("KingName"));
                    dataSet.setQueenName(_object.getString("QueenName"));
                    dataSet.setMTID(_object.getString("MTID"));

                            leaugeList.add(dataSet);

                        }catch (JSONException jsonException)
                        {
                            jsonException.printStackTrace();
                        }}
                        leagueListAdapter= new LeagueListAdapter(MyLeagueActivity.this,leaugeList);
                        listView_LeagueList.setAdapter(leagueListAdapter);
                        leagueListAdapter.notifyDataSetChanged();

                    }else
                    {
                        AlertDialog.Builder add = new AlertDialog.Builder(MyLeagueActivity.this);
                        add.setMessage("You have not created any league yet , please click on ADD MORE LEAGUE button to create your league").setCancelable(true);
                        AlertDialog alert = add.create();
                        alert.setTitle("NO LEAGUE CREATE");
                        alert.show();
                    }

                    }catch (Exception e) {}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AlertDialog.Builder add = new AlertDialog.Builder(MyLeagueActivity.this);
                add.setMessage(error.getMessage()).setCancelable(true);
                AlertDialog alert = add.create();
                alert.setTitle("Error!!!");
                alert.show();
            }
        }
        ){
            protected Map<String , String> getParams()
            {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("UserID",userId);
                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(MyLeagueActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    public void getUIid()
    {
        listView_LeagueList = (ListView) findViewById(R.id.listview_data);
        _addMoreLeague = (TextView) findViewById(R.id.txt_addMoreLeague);
        _addMoreLeague.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id)
        {

            case R.id.txt_addMoreLeague:
//                Toast.makeText(getApplicationContext(),"Working on this Module",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MyLeagueActivity.this,AddMoreLeauge.class));
        }
    }


    public void getInstaMojoPaymentIntegrate()
    {

    }

    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
