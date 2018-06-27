package com.game.stock.stockapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.AddMoreLeagueAdapter;
import Adapter.AllStockAdapter;
import Adapter.MyTeamsDetailsAdapter;
import Adapter.SaveKingQueenAdapter;
import Adapter.WinningBreakUpAdapter;
import Bean.AddMoreLeagueModel;
import Bean.AllStockModel;
import Bean.EditLeaguesBean;
import Bean.MyTeamsDetailsBean;
import Bean.WinningBreakUpModel;

import static Adapter.AddMoreLeagueAdapter.AddMyTeam_MTID;

/**
 * Created by firemoon on 15/2/18.
 */

public class AddMoreLeauge extends AppCompatActivity implements View.OnClickListener {


    private AddMoreLeagueAdapter addMoreLeagueAdapter;
    private WinningBreakUpAdapter winningBreakUpAdapter;
    private ListView listview_winningBreakup,listView_addMoreLeague,listview_sensexNifty,listview_selectKingsqueen,listview_kingQueenSave;
    private ArrayList<AddMoreLeagueModel> addMoreLeagueModels = new ArrayList<>();
    private ArrayList<WinningBreakUpModel> arrayList_winningBreakUp = new ArrayList<>();
    private ArrayList<AllStockModel> arrayList_allAllStockModels = new ArrayList<>();
    private ArrayList<EditLeaguesBean> arrayList_editLeaguesBean = new ArrayList<>();
    private ArrayList<MyTeamsDetailsBean> arrayList_myteamsdetails = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private ImageView img_filter,img_selectLeague,img_createLeague,img_comfirmKingQueen;
    private ImageView img_back;
    private TextView toolbar_title,txt_cancel,txt_reset,txt_winning_cancel;
    private RelativeLayout relative_filter,relative_winningBreakup;
    private LinearLayout linear_afternextbuttonClick,linear_numofParticipants2to5,linear_numofParticipants10to500,linear_numofParticipants1300to,linear_Apply,linear_king_queensave;
    private LinearLayout scrollView_createLeagueafternextbtn,linear_ListView,linear_entry1to1000,linear_entry1001to3000,linear_entry3001to,scrollView_createLeague,
            linear_nifty50,linear_bankNifty;
    private String RANKFROM,RANKTO;
    private String AMOUNTFROM,AMOUNTTO;
    public static int mSelectedItem = -1;
    public static String USER_ID;
    private AllStockAdapter allStockAdapter;
    private MyTeamsDetailsAdapter myTeamsDetailsAdapter;
    private SaveKingQueenAdapter saveKingQueenAdapter;
    private SharedPreferences sharedPreferences_getAllLeague,sharedPreferences_customLogin;
    private TextView txt_addedPlayer,txt_mainimumPlayer,txt_nextbtn,txt_afternextbtnclick,txt_addedkingQueen,txt_kingsName,txt_saveKingQueen,txt_QueensName;
    public static int textPlayerTotalPlayers = 0 ;
    public static int textKINGQUEENTotal = 0;
    public static int if_txt_nextbn,if_txt_afternextbn=0;
    public static String type="1";
    public static String afterEdit_MTID="";
    private String count_Detail;
    private String intent_MTID;
//    private ScrollView scrollView_createLeague;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmoreleague);

        sharedPreferences_getAllLeague = getSharedPreferences("com.stock.addMoreLeague.getAllLeague", Context.MODE_PRIVATE);
        sharedPreferences_customLogin=getSharedPreferences("com.custom.Login", Context.MODE_PRIVATE);
        USER_ID = sharedPreferences_customLogin.getString("id",null);

//        Toast.makeText(getApplicationContext(),USER_ID,Toast.LENGTH_SHORT).show();
        initview();


        Intent intent = getIntent();
        String editValue = intent.getStringExtra("Edit");
        if(editValue!=null) {
            if (editValue.equalsIgnoreCase("1")) {

                intent_MTID = intent.getStringExtra("MTID");
                linear_ListView.setVisibility(View.GONE);
                img_filter.setVisibility(View.INVISIBLE);
                Drawable drawable_img_createLeague = getResources().getDrawable(R.drawable.selectleaguebluetick);
                drawable_img_createLeague.setBounds(0,0,60,60);
                img_createLeague.setImageDrawable(drawable_img_createLeague);
                toolbar_title.setText("CREATE PORTFOLIO");
                scrollView_createLeague.setVisibility(View.VISIBLE);
                scrollView_createLeagueafternextbtn.setVisibility(View.GONE);
                afterEdit_MTID = intent.getStringExtra("Edit");
                arrayList_allAllStockModels.clear();
                getCountDetail();

                if(txt_addedPlayer.getText().toString().equalsIgnoreCase("5"))
                {
                    String type_foredit = "2";
                    getNifty50(type_foredit);

                }
                else
                {
                    String type_foredit = "1";
                    getNifty50(type_foredit);
                }
//                getEditLeague(AddMyTeam_MTID);
//                getNifty50(type);
            }
        }
else {
            getallleague();
        }


        listView_addMoreLeague.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                relative_winningBreakup.setVisibility(View.VISIBLE);
                relative_filter.setVisibility(View.GONE);
                String leagueId = addMoreLeagueModels.get(position).getLeagueID();
                arrayList_winningBreakUp.clear();
//                addMoreLeagueModels.clear();
                getWinningBreakUP(leagueId);
            }
        });
//        getCreatLeagueButtonFunction();
    }


    public void initview()
    {

        listview_selectKingsqueen = (ListView) findViewById(R.id.listview_selectKingsqueen);
        listview_kingQueenSave = (ListView) findViewById(R.id.listview_kingQueenSave);
        listview_sensexNifty = (ListView) findViewById(R.id.listview_sensexNifty);
        listView_addMoreLeague = (ListView) findViewById(R.id.listview_data);
        listview_winningBreakup = (ListView) findViewById(R.id.listview_winningBreakup);
        img_comfirmKingQueen = (ImageView) findViewById(R.id.img_comfirmKingQueen);
        img_createLeague = (ImageView) findViewById(R.id.img_createLeague);
        img_selectLeague = (ImageView) findViewById(R.id.img_selectLeague);
        img_filter = (ImageView) findViewById(R.id.img_filter);

        linear_nifty50 = (LinearLayout) findViewById(R.id.linear_nifty50);
        linear_bankNifty = (LinearLayout) findViewById(R.id.linear_bankNifty);
        scrollView_createLeague = (LinearLayout) findViewById(R.id.scrollView_createLeague);
        relative_winningBreakup = (RelativeLayout) findViewById(R.id.relative_winningBreakup);
        relative_filter = (RelativeLayout) findViewById(R.id.relative_filter);
        linear_king_queensave = (LinearLayout) findViewById(R.id.linear_king_queensave);
        linear_Apply = (LinearLayout) findViewById(R.id.linear_Apply);
        linear_ListView = (LinearLayout) findViewById(R.id.linear_ListView);
        scrollView_createLeagueafternextbtn = (LinearLayout) findViewById(R.id.scrollView_createLeagueafternextbtn);
        linear_afternextbuttonClick = (LinearLayout) findViewById(R.id.linear_afternextbuttonClick);
        linear_numofParticipants2to5 = (LinearLayout) findViewById(R.id.linear_numofParticipants2to5);
        linear_numofParticipants10to500 = (LinearLayout) findViewById(R.id.linear_numofParticipants10to500);
        linear_numofParticipants1300to = (LinearLayout) findViewById(R.id.linear_numofParticipants1300to);
        linear_entry1to1000 = (LinearLayout) findViewById(R.id.linear_entry1to1000);
        linear_entry1001to3000 = (LinearLayout) findViewById(R.id.linear_entry1001to3000);
        linear_entry3001to = (LinearLayout) findViewById(R.id.linear_entry3001to);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        linear_afternextbuttonClick.setOnClickListener(this);

        txt_saveKingQueen = (TextView) findViewById(R.id.txt_saveKingQueen);
        txt_QueensName = (TextView) findViewById(R.id.txt_QueensName);
        txt_kingsName = (TextView) findViewById(R.id.txt_kingsName);
        txt_afternextbtnclick = (TextView) findViewById(R.id.txt_afternextbtnclick);
        txt_nextbtn = (TextView) findViewById(R.id.txt_nextbtn);
        txt_addedPlayer = (TextView) findViewById(R.id.txt_addedPlayer);
        txt_addedkingQueen = (TextView) findViewById(R.id.txt_addedkingQueen);
        txt_mainimumPlayer = (TextView) findViewById(R.id.txt_mainimumPlayer);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        txt_winning_cancel = (TextView) findViewById(R.id.txt_winning_cancel);
        txt_cancel = (TextView) findViewById(R.id.txt_cancel);
        txt_reset = (TextView) findViewById(R.id.txt_reset);



        txt_addedPlayer.setText(String.valueOf(textPlayerTotalPlayers));
        txt_addedkingQueen.setText(String.valueOf(textKINGQUEENTotal));
        checkTotalNumberPlayer();
//        checkNumberKINGQUEEN();

        relative_winningBreakup.setOnClickListener(this);
        linear_nifty50.setOnClickListener(this);
        linear_bankNifty.setOnClickListener(this);
        txt_saveKingQueen.setOnClickListener(this);
        txt_afternextbtnclick.setOnClickListener(this);
        txt_nextbtn.setOnClickListener(this);
        img_filter.setOnClickListener(this);
//        img_selectLeague.setOnClickListener(this);
        txt_winning_cancel.setOnClickListener(this);
        txt_cancel.setOnClickListener(this);
        txt_reset.setOnClickListener(this);
        linear_Apply.setOnClickListener(this);
        linear_numofParticipants2to5.setOnClickListener(this);
        linear_numofParticipants10to500.setOnClickListener(this);
        linear_numofParticipants1300to.setOnClickListener(this);
        linear_entry1to1000.setOnClickListener(this);
        linear_entry1001to3000.setOnClickListener(this);
        linear_entry3001to.setOnClickListener(this);

        toolbar_title.setText("SELECT LEAGUE");

        txt_afternextbtnclick.setEnabled(false);
        txt_afternextbtnclick.setTextColor(getResources().getColor(R.color.grey_color));
    }


    public void checkTotalNumberPlayer()
    {

        if(type.equalsIgnoreCase("1")) {
            if (txt_addedPlayer.getText().toString().equalsIgnoreCase("19")) {
                txt_nextbtn.setEnabled(true);
                if_txt_nextbn = 1;
//            Toast.makeText(getApplicationContext(),"Enable",Toast.LENGTH_SHORT).show();
                txt_nextbtn.setTextColor(getResources().getColor(R.color.white));

            } else {
                txt_nextbtn.setEnabled(false);
                if_txt_nextbn = 0;
//            txt_nextbtn.setBackground((Color.parseColor("#CCCCCC")));
                txt_nextbtn.setTextColor(getResources().getColor(R.color.grey_color));
//            Toast.makeText(getApplicationContext(),"Disable",Toast.LENGTH_SHORT).show();

            }
        }else if(type.equalsIgnoreCase("2"))
        {
            if (txt_addedPlayer.getText().toString().equalsIgnoreCase("4")) {
                txt_nextbtn.setEnabled(true);
                if_txt_nextbn = 1;
//            Toast.makeText(getApplicationContext(),"Enable",Toast.LENGTH_SHORT).show();
                txt_nextbtn.setTextColor(getResources().getColor(R.color.white));

            } else {
                txt_nextbtn.setEnabled(false);
                if_txt_nextbn = 0;
//            txt_nextbtn.setBackground((Color.parseColor("#CCCCCC")));
                txt_nextbtn.setTextColor(getResources().getColor(R.color.grey_color));

            }
        }
    }



   public void getallleague()
    {
        mProgressDialog = ProgressDialog.show(AddMoreLeauge.this, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.getallleague, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                        JSONArray jsonArrayRequest = jsonObject_LeaugeList.getJSONArray("info");

                        for(int i=0;i<jsonArrayRequest.length();i++){
                            try {
                                AddMoreLeagueModel dataSet = new AddMoreLeagueModel();
                                JSONObject _object = jsonArrayRequest.getJSONObject(i);

                                String LEAGUE_ID =  _object.getString("LeagueID");
                                String LEAGUE_NAME =  _object.getString("LeagueName");
                                String ENTRY_FEE =  _object.getString("EntryFees");
                                String N0_OF_TEAMS =  _object.getString("No of Teams");
                                String N0_OF_WINNERS = _object.getString("No Of Winners");
                                String WINNING_AMOUNT = _object.getString("Winning Amount");
                                String CURRENT_TEAMS = _object.getString("Current Teams");
                                String JOIN_DATE = _object.getString("Date");
                                String TEAM_RANK = _object.getString("Rank");

                                dataSet.setLeagueID(LEAGUE_ID);
                                dataSet.setLeagueName(LEAGUE_NAME);
                                dataSet.setEntryFees(ENTRY_FEE);
                                dataSet.setNumberOfPlayers(N0_OF_TEAMS);
                                dataSet.setNumberOfWinnern(N0_OF_WINNERS);
                                dataSet.setWinningAmount(WINNING_AMOUNT);
                                dataSet.setCurrentTeams(CURRENT_TEAMS);
                                dataSet.setAMLDate(JOIN_DATE);
                                dataSet.setRank(TEAM_RANK);

                                SharedPreferences.Editor editor = sharedPreferences_getAllLeague.edit();
                                editor.putString("league_id",LEAGUE_ID);
                                editor.putString("league_name",LEAGUE_NAME);
                                editor.putString("entry_fee",ENTRY_FEE);
                                editor.putString("no_of_teams",N0_OF_TEAMS);
                                editor.putString("no_of_winners",N0_OF_WINNERS);
                                editor.putString("winning_amount",WINNING_AMOUNT);
                                editor.putString("current_teams",CURRENT_TEAMS);
                                editor.putString("join_date",JOIN_DATE);
                                editor.putString("team_rank",TEAM_RANK);
                                editor.apply();
                                editor.commit();

                                addMoreLeagueModels.add(dataSet);

                            }catch (JSONException jsonException)
                            {
                                mProgressDialog.dismiss();
                                mProgressDialog.cancel();
                                jsonException.printStackTrace();
                            }
                        }
                        addMoreLeagueAdapter = new AddMoreLeagueAdapter(AddMoreLeauge.this,addMoreLeagueModels,getApplicationContext());
                        listView_addMoreLeague.setAdapter(addMoreLeagueAdapter);



                        addMoreLeagueAdapter.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                    }else
                    {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();

                        /*AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
                        add.setMessage("You have not created any league yet , please click on ADD MORE LEAGUE button to create your league").setCancelable(true);
                        AlertDialog alert = add.create();
                        alert.setTitle("NO LEAGUE CREATE");
                        alert.show();*/
                    }

                }catch (Exception e) {
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();
                    Toast.makeText(AddMoreLeauge.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                mProgressDialog.cancel();
                AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
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
                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AddMoreLeauge.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }



    public void getWinningBreakUP(final String leagueId)
    {
        mProgressDialog = ProgressDialog.show(AddMoreLeauge.this, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.winningBreakup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                        JSONArray jsonArrayRequest = jsonObject_LeaugeList.getJSONArray("info");
                        for(int i=0;i<jsonArrayRequest.length();i++){
                            try {
                                WinningBreakUpModel dataSet = new WinningBreakUpModel();
                                JSONObject _object = jsonArrayRequest.getJSONObject(i);


                                String RANKTO =  _object.getString("Rank To");
                                String RANKFROM =  _object.getString("RankFrom");
                                String RANKFROMAMOUNT =  _object.getString("RankFromAmount");
                                String RANKT0AMOUNT =  _object.getString("RankToAmount");

                                dataSet.setRankFrom(RANKFROM);
                                dataSet.setRankTo(RANKTO);
                                dataSet.setRankFromAmount(RANKFROMAMOUNT);
                                dataSet.setRankToAmount(RANKT0AMOUNT);


                                arrayList_winningBreakUp.add(dataSet);

                            }catch (JSONException jsonException)
                            {
                                mProgressDialog.dismiss();
                                mProgressDialog.cancel();
                                jsonException.printStackTrace();
                            }
                        }
                        winningBreakUpAdapter = new WinningBreakUpAdapter(AddMoreLeauge.this,arrayList_winningBreakUp);
                        listview_winningBreakup.setAdapter(winningBreakUpAdapter);
                        winningBreakUpAdapter.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();

                    }else
                    {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                    }
                }catch (Exception e) {
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();
                    Toast.makeText(AddMoreLeauge.this,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                mProgressDialog.cancel();
                AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
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
                params.put("LeagueID","36");
                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AddMoreLeauge.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.img_filter)
        {
            relative_filter.setVisibility(View.VISIBLE);
            relative_winningBreakup.setVisibility(View.GONE);
        }

        else if(v.getId()==R.id.linear_nifty50)
        {
            updateNumberOfPlayerAdded();
            linear_nifty50.setBackground(getResources().getDrawable(R.color.gradient_start));
            linear_bankNifty.setBackground(getResources().getDrawable(R.color.colorAccent));

            arrayList_allAllStockModels.clear();
            allStockAdapter.notifyDataSetChanged();

            type = "1";
            getNifty50(type);
            txt_mainimumPlayer.setText("20");
        }

        else if(v.getId()==R.id.linear_bankNifty)
        {

            updateNumberOfPlayerAdded();
            linear_nifty50.setBackground(getResources().getDrawable(R.color.colorAccent));
            linear_bankNifty.setBackground(getResources().getDrawable(R.color.gradient_start));
            arrayList_allAllStockModels.clear();
            allStockAdapter.notifyDataSetChanged();

            type = "2";
            getNifty50(type);
            txt_mainimumPlayer.setText("05");
        }
        else if(v.getId()==R.id.img_back)
        {
            finish();
        }

        else if(v.getId()==R.id.txt_saveKingQueen)
        {
            startActivity(new Intent(AddMoreLeauge.this,MyLeagueActivity.class));
            finish();
        }

        else if(v.getId()==R.id.txt_nextbtn)
        {

            textPlayerTotalPlayers=0;
            getMyTeamsbyMTID();
            scrollView_createLeagueafternextbtn.setVisibility(View.VISIBLE);
            scrollView_createLeague.setVisibility(View.GONE);
            linear_ListView.setVisibility(View.GONE);

        }

        else if(v.getId()==R.id.txt_afternextbtnclick)
        {

            arrayList_myteamsdetails.clear();

            textPlayerTotalPlayers=0;
            textKINGQUEENTotal=0;

            getSaveKingAndQueenCreateLeague();

            Drawable drawable_img_createLeague = getResources().getDrawable(R.drawable.selectleaguebluetick);
            drawable_img_createLeague.setBounds(0,0,60,60);
            img_comfirmKingQueen.setImageDrawable(drawable_img_createLeague);
            toolbar_title.setText("CONFIRM");
            scrollView_createLeagueafternextbtn.setVisibility(View.GONE);
            scrollView_createLeague.setVisibility(View.GONE);
            linear_ListView.setVisibility(View.GONE);
            linear_king_queensave.setVisibility(View.VISIBLE);

        }

        else if(v.getId()==R.id.img_selectLeague)
        {

            Drawable drawable_img_createLeague = getResources().getDrawable(R.drawable.selectleaguegrey);
            drawable_img_createLeague.setBounds(0,0,60,60);
            img_createLeague.setImageDrawable(drawable_img_createLeague);
            scrollView_createLeagueafternextbtn.setVisibility(View.GONE);
            linear_ListView.setVisibility(View.VISIBLE);
            img_filter.setVisibility(View.VISIBLE);
            toolbar_title.setText("SELECT LEAGUE");
            scrollView_createLeague.setVisibility(View.GONE);
            linear_king_queensave.setVisibility(View.GONE);

            textPlayerTotalPlayers=0;

        }

        else if(v.getId()==R.id.txt_winning_cancel)
        {
            relative_filter.setVisibility(View.GONE);
            relative_winningBreakup.setVisibility(View.GONE);
        }

        else if(v.getId()==R.id.txt_cancel)
        {
            relative_filter.setVisibility(View.GONE);
            relative_winningBreakup.setVisibility(View.GONE);
        }
        else if(v.getId()==R.id.txt_reset)
        {
            addMoreLeagueModels.clear();
            getallleague();
            relative_filter.setVisibility(View.GONE);
        }
        else if(v.getId()==R.id.linear_numofParticipants2to5)
        {

            Drawable drawable_filterColor = getResources().getDrawable( R.drawable.filterbuttoncolor );
            Drawable rectangular_button = getResources().getDrawable( R.drawable.rectanglebutn );
            drawable_filterColor.setBounds( 0, 0, 60, 60 );
            linear_numofParticipants2to5.setBackground(drawable_filterColor);
            linear_numofParticipants10to500.setBackground(rectangular_button);
            linear_numofParticipants1300to.setBackground(rectangular_button);
            linear_entry1to1000.setBackground(rectangular_button);
            linear_entry1001to3000.setBackground(rectangular_button);
            linear_entry3001to.setBackground(rectangular_button);

            RANKFROM = "2";
            RANKTO = "5";
        }
        else if(v.getId()==R.id.linear_numofParticipants10to500)
        {
            Drawable drawable_filterColor = getResources().getDrawable( R.drawable.filterbuttoncolor );
            Drawable rectangular_button = getResources().getDrawable( R.drawable.rectanglebutn );
            drawable_filterColor.setBounds( 0, 0, 60, 60 );
            linear_numofParticipants2to5.setBackground(rectangular_button);
            linear_numofParticipants10to500.setBackground(drawable_filterColor);
            linear_numofParticipants1300to.setBackground(rectangular_button);
            linear_entry1to1000.setBackground(rectangular_button);
            linear_entry1001to3000.setBackground(rectangular_button);
            linear_entry3001to.setBackground(rectangular_button);


            RANKFROM = "10";
            RANKTO = "500";
        }
        else if(v.getId()==R.id.linear_numofParticipants1300to)
        {
            Drawable drawable_filterColor = getResources().getDrawable( R.drawable.filterbuttoncolor );
            Drawable rectangular_button = getResources().getDrawable( R.drawable.rectanglebutn );
            drawable_filterColor.setBounds( 0, 0, 60, 60 );
            linear_numofParticipants2to5.setBackground(rectangular_button);
            linear_numofParticipants10to500.setBackground(rectangular_button);
            linear_numofParticipants1300to.setBackground(drawable_filterColor);
            linear_entry1to1000.setBackground(rectangular_button);
            linear_entry1001to3000.setBackground(rectangular_button);
            linear_entry3001to.setBackground(rectangular_button);


            RANKFROM = "1300";
            RANKTO = "200000";
        }


        else if(v.getId()==R.id.linear_entry1to1000)
        {
            Drawable drawable_filterColor = getResources().getDrawable( R.drawable.filterbuttoncolor );
            Drawable rectangular_button = getResources().getDrawable( R.drawable.rectanglebutn );
            drawable_filterColor.setBounds( 0, 0, 60, 60 );
            linear_numofParticipants2to5.setBackground(rectangular_button);
            linear_numofParticipants10to500.setBackground(rectangular_button);
            linear_numofParticipants1300to.setBackground(rectangular_button);
            linear_entry1to1000.setBackground(drawable_filterColor);
            linear_entry1001to3000.setBackground(rectangular_button);
            linear_entry3001to.setBackground(rectangular_button);

            AMOUNTFROM = "1";
            AMOUNTTO = "1000";
            RANKFROM = "";
            RANKTO = "";
        }
        else if(v.getId()==R.id.linear_entry1001to3000)
        {
            Drawable drawable_filterColor = getResources().getDrawable( R.drawable.filterbuttoncolor );
            Drawable rectangular_button = getResources().getDrawable( R.drawable.rectanglebutn );
            drawable_filterColor.setBounds( 0, 0, 60, 60 );
            linear_numofParticipants2to5.setBackground(rectangular_button);
            linear_numofParticipants10to500.setBackground(rectangular_button);
            linear_numofParticipants1300to.setBackground(rectangular_button);
            linear_entry1to1000.setBackground(rectangular_button);
            linear_entry1001to3000.setBackground(drawable_filterColor);
            linear_entry3001to.setBackground(rectangular_button);

            AMOUNTFROM = "1001";
            AMOUNTTO = "3000";
            RANKFROM = "";
            RANKTO = "";
        }
        else if(v.getId()==R.id.linear_entry3001to)
        {
            Drawable drawable_filterColor = getResources().getDrawable( R.drawable.filterbuttoncolor );
            Drawable rectangular_button = getResources().getDrawable( R.drawable.rectanglebutn );
            drawable_filterColor.setBounds( 0, 0, 60, 60 );
            linear_numofParticipants2to5.setBackground(rectangular_button);
            linear_numofParticipants10to500.setBackground(rectangular_button);
            linear_numofParticipants1300to.setBackground(rectangular_button);
            linear_entry1to1000.setBackground(rectangular_button);
            linear_entry1001to3000.setBackground(rectangular_button);
            linear_entry3001to.setBackground(drawable_filterColor);

            AMOUNTFROM = "3001";
            AMOUNTTO = "10000";
            RANKFROM = "";
            RANKTO = "";
        }
        else if(v.getId()==R.id.linear_Apply)
        {
            if(!RANKFROM.equalsIgnoreCase("")) {
                addMoreLeagueModels.clear();

                getNumberOfParticipants();
            }
            else
            {
                addMoreLeagueModels.clear();
                getEntryFees();
            }
            relative_filter.setVisibility(View.GONE);
        }
    }


    public void getNumberOfParticipants()
    {
        mProgressDialog = ProgressDialog.show(AddMoreLeauge.this, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.getbetweenteamrange, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                        JSONArray jsonArrayRequest = jsonObject_LeaugeList.getJSONArray("info");

                        for(int i=0;i<jsonArrayRequest.length();i++){
                            try {
                                AddMoreLeagueModel dataSet = new AddMoreLeagueModel();
                                JSONObject _object = jsonArrayRequest.getJSONObject(i);

                                dataSet.setLeagueID(_object.getString("LeagueID"));
                                dataSet.setLeagueName(_object.getString("LeagueName"));
                                dataSet.setEntryFees(_object.getString("EntryFees"));
                                dataSet.setRank(_object.getString("Rank"));
                                dataSet.setAMLDate(_object.getString("Date"));
                                dataSet.setCurrentTeams(_object.getString("Current Teams"));
                                dataSet.setNumberOfPlayers(_object.getString("No of Teams"));
                                dataSet.setNumberOfWinnern(_object.getString("No Of Winners"));
                                dataSet.setWinningAmount(_object.getString("Winning Amount"));
//                                dataSet.setCurrentTeams(_object.getString("Current Teams"));

                                addMoreLeagueModels.add(dataSet);

                            }catch (JSONException jsonException)
                            {
                                jsonException.printStackTrace();
                            }
                        }
                        addMoreLeagueAdapter = new AddMoreLeagueAdapter(AddMoreLeauge.this,addMoreLeagueModels,getApplicationContext());
                        listView_addMoreLeague.setAdapter(addMoreLeagueAdapter);

                        addMoreLeagueAdapter.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                    }else
                    {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();

                        Toast.makeText(getApplicationContext(),jsonObject_LeaugeList.getString("msg").toString(),Toast.LENGTH_SHORT).show();
                        /*AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
                        add.setMessage("You have not created any league yet , please click on ADD MORE LEAGUE button to create your league").setCancelable(true);
                        AlertDialog alert = add.create();
                        alert.setTitle("NO LEAGUE CREATE");
                        alert.show();*/
                    }

                }catch (Exception e) {
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();
                    Toast.makeText(AddMoreLeauge.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                mProgressDialog.cancel();
                AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
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


                params.put(Config.RANK_FROM,RANKFROM );
                params.put(Config.RANK_TO, RANKTO);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AddMoreLeauge.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }


    public void getEntryFees()
    {
        mProgressDialog = ProgressDialog.show(AddMoreLeauge.this, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.getbetweenamountrange, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                        JSONArray jsonArrayRequest = jsonObject_LeaugeList.getJSONArray("info");

                        for(int i=0;i<jsonArrayRequest.length();i++){
                            try {
                                AddMoreLeagueModel dataSet = new AddMoreLeagueModel();
                                JSONObject _object = jsonArrayRequest.getJSONObject(i);

                                dataSet.setLeagueID(_object.getString("LeagueID"));
                                dataSet.setLeagueName(_object.getString("LeagueName"));
                                dataSet.setEntryFees(_object.getString("EntryFees"));
                                dataSet.setRank(_object.getString("Rank"));
                                dataSet.setAMLDate(_object.getString("Date"));
                                dataSet.setCurrentTeams(_object.getString("Current Teams"));
                                dataSet.setNumberOfPlayers(_object.getString("No of Teams"));
                                dataSet.setNumberOfWinnern(_object.getString("No Of Winners"));
                                dataSet.setWinningAmount(_object.getString("Winning Amount"));
//                                dataSet.setCurrentTeams(_object.getString("Current Teams"));

                                addMoreLeagueModels.add(dataSet);

                            }catch (JSONException jsonException)
                            {
                                jsonException.printStackTrace();
                            }
                        }
                        addMoreLeagueAdapter = new AddMoreLeagueAdapter(AddMoreLeauge.this,addMoreLeagueModels,getApplicationContext());
                        listView_addMoreLeague.setAdapter(addMoreLeagueAdapter);

                        addMoreLeagueAdapter.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                    }else
                    {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                        Toast.makeText(getApplicationContext(),jsonObject_LeaugeList.getString("msg").toString(),Toast.LENGTH_SHORT).show();

                        /*AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
                        add.setMessage("You have not created any league yet , please click on ADD MORE LEAGUE button to create your league").setCancelable(true);
                        AlertDialog alert = add.create();
                        alert.setTitle("NO LEAGUE CREATE");
                        alert.show();*/
                    }

                }catch (Exception e) {
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();
                    Toast.makeText(AddMoreLeauge.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                mProgressDialog.cancel();
                AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
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


                params.put(Config.AMOUNT_FROM,AMOUNTFROM );
                params.put(Config.AMOUNT_TO, AMOUNTTO);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AddMoreLeauge.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }



    /*--------------
    ----------------
    --------------------
    // METHOD CALLING FOR CREATE LEAGUE PORTFOLIO
    -----------------
    ===================
    -------------------*/


    public void getCreatLeagueButtonFunction()
    {

        linear_ListView.setVisibility(View.GONE);
        img_filter.setVisibility(View.INVISIBLE);
        Drawable drawable_img_createLeague = getResources().getDrawable(R.drawable.selectleaguebluetick);
        drawable_img_createLeague.setBounds(0,0,60,60);
        img_createLeague.setImageDrawable(drawable_img_createLeague);
        toolbar_title.setText("CREATE PORTFOLIO");
        scrollView_createLeague.setVisibility(View.VISIBLE);
        scrollView_createLeagueafternextbtn.setVisibility(View.GONE);

        getNifty50(type);

    }

    public void getNifty50(final String type)
    {
        textKINGQUEENTotal=0;
        textPlayerTotalPlayers=0;
        mProgressDialog.dismiss();
        mProgressDialog.cancel();
        mProgressDialog = ProgressDialog.show(AddMoreLeauge.this, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.getallstocksbytype, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                        JSONArray jsonArrayRequest = jsonObject_LeaugeList.getJSONArray("info");

                        for(int i=0;i<jsonArrayRequest.length();i++){
                            AllStockModel dataSet = new AllStockModel();
                            try {

                                JSONObject _object = jsonArrayRequest.getJSONObject(i);

                                dataSet.setDispName(_object.getString("DispName"));
                                dataSet.setToken(_object.getString("Token"));
                                dataSet.setSMID(_object.getString("SMID"));


                                arrayList_allAllStockModels.add(dataSet);

                            }catch (JSONException jsonException)
                            {
                                mProgressDialog.dismiss();
                                mProgressDialog.cancel();
                                jsonException.printStackTrace();
                            }
                        }



                        allStockAdapter = new AllStockAdapter(AddMoreLeauge.this,arrayList_allAllStockModels);
                        listview_sensexNifty.setAdapter(allStockAdapter);

                        listview_sensexNifty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                mSelectedItem = position;
                                allStockAdapter.notifyDataSetChanged();
                            }
                        });
                        allStockAdapter.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                    }else
                    {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                        Toast.makeText(getApplicationContext(),jsonObject_LeaugeList.getString("msg").toString(),Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e) {
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();
                    Toast.makeText(AddMoreLeauge.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                mProgressDialog.cancel();
                AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
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


                params.put(Config.TYPE,type );
//                params.put(Config.AMOUNT_TO, AMOUNTTO);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AddMoreLeauge.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    public void getEditLeague(final String MTID)
    {

        Toast.makeText(getApplicationContext(),""+MTID,Toast.LENGTH_SHORT).show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.editLeague2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                        JSONArray jsonArrayRequest = jsonObject_LeaugeList.getJSONArray("info");

                        for(int i=0 ; i<jsonArrayRequest.length();i++)
                        {

                            EditLeaguesBean editLeaguesBean = new EditLeaguesBean();
                            try {

                                JSONObject _object = jsonArrayRequest.getJSONObject(i);

                                editLeaguesBean.setBY_TYPE(_object.getString("ByType"));
                                editLeaguesBean.setPlayerType(_object.getString("PlayerType"));
                                editLeaguesBean.setPlayerName(_object.getString("PlayerName"));
                                editLeaguesBean.setPLID(_object.getString("PLID"));


//                                if(_object.getString("PlayerName").equalsIgnoreCase())
                                arrayList_editLeaguesBean.add(editLeaguesBean);
                            }catch (JSONException jsonException)
                            {
                                mProgressDialog.dismiss();
                                mProgressDialog.cancel();
                                jsonException.printStackTrace();
                            }
                        }

                    }


                }catch (Exception e) {}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
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
                params.put("MTID",MTID);
                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
 public void getMyTeamsbyMTID()
    {
        mProgressDialog.dismiss();
        mProgressDialog.cancel();
        mProgressDialog = ProgressDialog.show(AddMoreLeauge.this, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.addmyteamsdetailslistNextbtn, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                        JSONArray jsonArrayRequest = jsonObject_LeaugeList.getJSONArray("info");

                        for(int i=0;i<jsonArrayRequest.length();i++){
                            MyTeamsDetailsBean dataSet = new MyTeamsDetailsBean();
                            try {

                                JSONObject _object = jsonArrayRequest.getJSONObject(i);

                                dataSet.setPlayerID(_object.getString("PLID"));
                                dataSet.setMyteamsID(_object.getString("myteamsID"));
                                dataSet.setSMID(_object.getString("SMID"));
                                dataSet.setTokenID(_object.getString("TokenID"));
                                dataSet.setPlayerName(_object.getString("PlayerName"));
                                dataSet.setPlayerType(_object.getString("PlayerType"));
                                dataSet.setStockrate(_object.getString("Stockrate"));


                                arrayList_myteamsdetails.add(dataSet);
                            }catch (JSONException jsonException)
                            {
                                mProgressDialog.dismiss();
                                mProgressDialog.cancel();
                                jsonException.printStackTrace();
                            }
                        }
                        myTeamsDetailsAdapter = new MyTeamsDetailsAdapter(AddMoreLeauge.this,arrayList_myteamsdetails);
                        listview_selectKingsqueen.setAdapter(myTeamsDetailsAdapter);

                        myTeamsDetailsAdapter.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                    }else
                    {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                        Toast.makeText(getApplicationContext(),jsonObject_LeaugeList.getString("msg").toString(),Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e) {
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();
                    Toast.makeText(AddMoreLeauge.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                mProgressDialog.cancel();
                AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
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

                params.put(Config.MTID,AddMyTeam_MTID );
//                params.put(Config.AMOUNT_TO, AMOUNTTO);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AddMoreLeauge.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }


    public void getSaveKingAndQueenCreateLeague()
    {
        mProgressDialog.dismiss();
        mProgressDialog.cancel();
        mProgressDialog = ProgressDialog.show(AddMoreLeauge.this, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.addmyteamsdetailslistNextbtn, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                        JSONArray jsonArrayRequest = jsonObject_LeaugeList.getJSONArray("info");

                        for(int i=0;i<jsonArrayRequest.length();i++){
                            MyTeamsDetailsBean dataSet = new MyTeamsDetailsBean();
                            try {

                                JSONObject _object = jsonArrayRequest.getJSONObject(i);

                                dataSet.setMyteamsID(_object.getString("myteamsID"));
                                dataSet.setSMID(_object.getString("SMID"));
                                dataSet.setTokenID(_object.getString("TokenID"));
                                dataSet.setPlayerName(_object.getString("PlayerName"));
                                dataSet.setPlayerType(_object.getString("PlayerType"));
                                dataSet.setStockrate(_object.getString("Stockrate"));
                                if(_object.getString("PlayerType").equalsIgnoreCase("1"))
                                {
                                    txt_kingsName.setText(_object.getString("PlayerName").toString().trim());
                                }
                                else if(_object.getString("PlayerType").equalsIgnoreCase("2"))
                                {
                                    txt_QueensName.setText(_object.getString("PlayerName").toString().trim());
                                }


                                arrayList_myteamsdetails.add(dataSet);
                            }catch (JSONException jsonException)
                            {
                                mProgressDialog.dismiss();
                                mProgressDialog.cancel();
                                jsonException.printStackTrace();
                            }
                        }
                        saveKingQueenAdapter = new SaveKingQueenAdapter(AddMoreLeauge.this,arrayList_myteamsdetails);
                        listview_kingQueenSave.setAdapter(saveKingQueenAdapter);

                        saveKingQueenAdapter.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                    }else
                    {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                        Toast.makeText(getApplicationContext(),jsonObject_LeaugeList.getString("msg").toString(),Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e) {
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();
                    Toast.makeText(AddMoreLeauge.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                mProgressDialog.cancel();
                AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
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

                params.put(Config.MTID,AddMyTeam_MTID );
//                params.put(Config.AMOUNT_TO, AMOUNTTO);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AddMoreLeauge.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }




    public void updateNumberOfPlayerAdded()
    {

        getCountDetail();
        checkTotalNumberPlayer();
//        Toast.makeText(getApplicationContext(),txt_addedPlayer.getText().toString(),Toast.LENGTH_SHORT).show();
        /*if(!txt_addedPlayer.getText().toString().equalsIgnoreCase("0")) {
            if (type.equalsIgnoreCase("1")) {
                linear_bankNifty.setEnabled(false);
//                linear_nifty50.setEnabled(false);
            } else if (type.equalsIgnoreCase("2")) {
                linear_nifty50.setEnabled(false);
//                linear_bankNifty.setEnabled(false);
            }
        }
       *//* else if(!txt_addedPlayer.getText().toString().equalsIgnoreCase("0")) {
            if (type.equalsIgnoreCase("1")) {
                linear_bankNifty.setEnabled(false);
                linear_nifty50.setEnabled(false);
            } else if (type.equalsIgnoreCase("2")) {
                linear_nifty50.setEnabled(false);
                linear_bankNifty.setEnabled(false);
            }
        }*//*
        else
        {
            linear_nifty50.setEnabled(true);
            linear_bankNifty.setEnabled(true);
        }*/
        txt_addedPlayer.setText(String.valueOf(textPlayerTotalPlayers));

        allStockAdapter.notifyDataSetChanged();
    }

    public void updateMYTeamDetails()
    {


        txt_addedkingQueen.setText(String.valueOf(textKINGQUEENTotal));
        if(txt_addedkingQueen.getText().toString().equalsIgnoreCase("2"))
        {
            txt_afternextbtnclick.setEnabled(true);

            txt_afternextbtnclick.setTextColor(getResources().getColor(R.color.white));
        }
        else
        {
            txt_afternextbtnclick.setEnabled(false);
            txt_afternextbtnclick.setTextColor(getResources().getColor(R.color.grey_color));

        }
        myTeamsDetailsAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        textKINGQUEENTotal=0;
        textPlayerTotalPlayers=0;
    }


    public void getCountDetail()
    {


        mProgressDialog = ProgressDialog.show(AddMoreLeauge.this, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.get_allcount, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                        JSONArray jsonArrayRequest = jsonObject_LeaugeList.getJSONArray("info");

                        for(int i=0;i<jsonArrayRequest.length();i++){
                            MyTeamsDetailsBean dataSet = new MyTeamsDetailsBean();
                            try {

                                JSONObject _object = jsonArrayRequest.getJSONObject(i);

                                count_Detail = _object.getString("COUNT");
                                txt_addedPlayer.setText(count_Detail);

                                if(txt_addedPlayer.getText().toString().equalsIgnoreCase("0"))
                                {
                                    linear_nifty50.setEnabled(true);
                                    linear_bankNifty.setEnabled(true);
                                }
                                else
                                {
                                    linear_nifty50.setEnabled(false);
                                    linear_bankNifty.setEnabled(false);
                                }


                            }catch (JSONException jsonException)
                            {
                                mProgressDialog.dismiss();
                                mProgressDialog.cancel();
                                jsonException.printStackTrace();
                            }
                        }
                        String TotalCount = jsonObject_LeaugeList.getString("Limit");

                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                    }else
                    {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                        Toast.makeText(getApplicationContext(),jsonObject_LeaugeList.getString("msg").toString(),Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e) {
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();
                    Toast.makeText(AddMoreLeauge.this,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                mProgressDialog.cancel();
                AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
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

                params.put(Config.MTID,AddMyTeam_MTID );
//                params.put(Config.AMOUNT_TO, AMOUNTTO);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(AddMoreLeauge.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    public void executeEditData()
    {
            mProgressDialog.dismiss();
            mProgressDialog.cancel();
            mProgressDialog = ProgressDialog.show(AddMoreLeauge.this, null, "Please Wait...", true);
            mProgressDialog.setMessage("Please Wait...");


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.EDIT_LEAGUE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try
                    {
                        JSONObject jsonObject_LeaugeList= new JSONObject(response);

                        if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                            JSONArray jsonArrayRequest = jsonObject_LeaugeList.getJSONArray("info");

                            for(int i=0;i<jsonArrayRequest.length();i++){
                                MyTeamsDetailsBean dataSet = new MyTeamsDetailsBean();
                                try {

                                    JSONObject _object = jsonArrayRequest.getJSONObject(i);

                                    dataSet.setMyteamsID(_object.getString("myteamsID"));
                                    dataSet.setSMID(_object.getString("SMID"));
                                    dataSet.setTokenID(_object.getString("TokenID"));
                                    dataSet.setPlayerName(_object.getString("PlayerName"));
                                    dataSet.setPlayerType(_object.getString("PlayerType"));
                                    dataSet.setStockrate(_object.getString("Stockrate"));
                                    if(_object.getString("PlayerType").equalsIgnoreCase("1"))
                                    {
                                        txt_kingsName.setText(_object.getString("PlayerName").toString().trim());
                                    }
                                    else if(_object.getString("PlayerType").equalsIgnoreCase("2"))
                                    {
                                        txt_QueensName.setText(_object.getString("PlayerName").toString().trim());
                                    }


                                    arrayList_myteamsdetails.add(dataSet);
                                }catch (JSONException jsonException)
                                {
                                    mProgressDialog.dismiss();
                                    mProgressDialog.cancel();
                                    jsonException.printStackTrace();
                                }
                            }
                            saveKingQueenAdapter = new SaveKingQueenAdapter(AddMoreLeauge.this,arrayList_myteamsdetails);
                            listview_kingQueenSave.setAdapter(saveKingQueenAdapter);

                            saveKingQueenAdapter.notifyDataSetChanged();
                            mProgressDialog.dismiss();
                            mProgressDialog.cancel();
                        }else
                        {
                            mProgressDialog.dismiss();
                            mProgressDialog.cancel();
                            Toast.makeText(getApplicationContext(),jsonObject_LeaugeList.getString("msg").toString(),Toast.LENGTH_SHORT).show();

                        }

                    }catch (Exception e) {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                        Toast.makeText(AddMoreLeauge.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();
                    AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
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

                    params.put(Config.MTID,intent_MTID );
//                params.put(Config.AMOUNT_TO, AMOUNTTO);

                    return params;
                }
            };

            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(AddMoreLeauge.this);
            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);

    }
}
