package com.game.stock.stockapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.game.stock.stockapp.Config;
import com.game.stock.stockapp.Login;
import com.game.stock.stockapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.LeagueHistoryAdapter;
import Bean.LeaugeHistoryBean;


public class Leaguehistory extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView toolbar_title;
    private ProgressDialog mProgressDialog;
    private ArrayList<LeaugeHistoryBean> leaugeList = new ArrayList<>();
    private ListView leagueHistoryList;

    private SharedPreferences sharedPrefre;
    private String UserId;

   /* @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_leaguehistory, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("LEAGUE HISTORY");
    }*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_leaguehistory);

        sharedPrefre = getSharedPreferences("com.custom.Login", MODE_PRIVATE);
        UserId = sharedPrefre.getString("id", null);

        leagueHistoryList = (ListView) findViewById(R.id.listview_data);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("LEAGUE HISTORY");
        getLeaugueHistory();

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.img_back)
        {
            finish();
        }
    }


    private void getLeaugueHistory() {

        mProgressDialog = ProgressDialog.show(Leaguehistory.this, null,"Please wait...", true);
        mProgressDialog.setMessage("Logging you in...");
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.portfolioleaguehistory, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {

                // Hiding the progress dialog after all task complete.
                mProgressDialog.dismiss();
                mProgressDialog.cancel();

//                        Toast.makeText(Login.this, ServerResponse, Toast.LENGTH_LONG).show();

                try {
                    JSONObject jsonObject=new JSONObject(ServerResponse);

                    if(jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))){

//                        JSONObject categoryJSONObj=mainJSONObj.getJSONObject("category");
                        JSONArray jsonArrayRequest = jsonObject.getJSONArray("info");

                        for(int i=0;i<jsonArrayRequest.length();i++){
                            try {
                                LeaugeHistoryBean leaugeHistoryBean = new LeaugeHistoryBean();
                                JSONObject _object = jsonArrayRequest.getJSONObject(i);
                                leaugeHistoryBean.setLeagueName(_object.getString("LeagueName"));
                                leaugeHistoryBean.setEntryFees(_object.getString("LeagueAmount"));
                                leaugeHistoryBean.setWiningAmount(_object.getString("WinningAmount"));
//                                leaugeHistoryBean.setNoOfWinners(_object.getString("No Of Winners"));
//                                leaugeHistoryBean.setNoofTeams(_object.getString("No of Teams"));
//                                leaugeHistoryBean.setComments(_object.getString("Comments"));
                                leaugeHistoryBean.setLeagueHistorydate(_object.getString("Played Date"));
                                leaugeHistoryBean.setRank(_object.getString("Rank"));
                                leaugeList.add(leaugeHistoryBean);

                            }catch (JSONException jsonException)
                            {
                                jsonException.printStackTrace();
                            }}

                        LeagueHistoryAdapter leagueHistoryAdapter = new LeagueHistoryAdapter(Leaguehistory.this,leaugeList);
                        leagueHistoryList.setAdapter(leagueHistoryAdapter);


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
                        Toast.makeText(Leaguehistory.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put("UserID",UserId);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Leaguehistory.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

}
