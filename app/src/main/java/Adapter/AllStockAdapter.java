package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.game.stock.stockapp.AddMoreLeauge;
import com.game.stock.stockapp.Config;
import com.game.stock.stockapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Bean.AddMoreLeagueModel;
import Bean.AllStockModel;
import Bean.EditLeaguesBean;
import Bean.MyTeamsDetailsBean;

import static Adapter.AddMoreLeagueAdapter.AddMyTeam_MTID;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.game.stock.stockapp.AddMoreLeauge.USER_ID;
import static com.game.stock.stockapp.AddMoreLeauge.afterEdit_MTID;
import static com.game.stock.stockapp.AddMoreLeauge.if_txt_nextbn;
import static com.game.stock.stockapp.AddMoreLeauge.textPlayerTotalPlayers;

/**
 * Created by firemoon on 27/4/18.
 */

public class AllStockAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<AllStockModel> DataList;
    private List<Boolean> isBtnAdd;
    private TextView txt_playersName;
    private ImageView img_addplayer,img_removepalyer;
    private CardView card_view;
    private AllStockModel allStockModel;
    private ProgressDialog mProgressDialog;
    private String ADD_MYPLID;
    //private int accessPosition;


    public AllStockAdapter(Activity activity, List<AllStockModel> dataitem) {
        this.activity = activity;
        this.DataList = dataitem;
        isBtnAdd= Arrays.asList(new Boolean[dataitem.size()]);
        Collections.fill(isBtnAdd,true);
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public Object getItem(int position) {
        return DataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.all_stock, null);

    /*if(!afterEdit_MTID.toString().equalsIgnoreCase("")) {

                getEditLeague(AddMoreLeauge.afterEdit_MTID);

    }*/
        txt_playersName = (TextView) convertView.findViewById(R.id.txt_playersName);
        img_addplayer = (ImageView) convertView.findViewById(R.id.img_addplayer);
        img_removepalyer = (ImageView) convertView.findViewById(R.id.img_removepalyer);
        card_view = (CardView) convertView.findViewById(R.id.card_view);


       /* if(position == AddMoreLeauge.mSelectedItem)
        {

            card_view.setCardBackgroundColor(Color.WHITE);
        }*/

        allStockModel = DataList.get(position);

        final String clickItemName = allStockModel.getDispName();
        final String clickItemToken = allStockModel.getToken();
        final String clickItemsmid = allStockModel.getSMID();


        txt_playersName.setText(allStockModel.getDispName());


        if(if_txt_nextbn==1)
        {
            img_addplayer.setEnabled(false);
        }
        else
        {
            img_addplayer.setEnabled(true);

        }
        img_addplayer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                isBtnAdd.set(position,false);


                getAddmyteamsdetails(AddMyTeam_MTID,clickItemName,clickItemToken,clickItemsmid);
            }
        });

        img_removepalyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                isBtnAdd.set(position,true);


//                Toast.makeText(activity,clickItemsmid,Toast.LENGTH_SHORT).show();
                getSmidForCompareAndDelete(clickItemsmid);

            }
        });




        if(!isBtnAdd.get(position))
        {
            img_addplayer.setVisibility(View.GONE);
            img_removepalyer.setVisibility(View.VISIBLE);
        }
        else
        {
            img_addplayer.setVisibility(View.VISIBLE);
            img_removepalyer.setVisibility(View.GONE);
        }

        return convertView;
    }




    private void deletebymyteamsdetailsid(final String ADDMYPLID) {

        mProgressDialog = ProgressDialog.show(activity, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.deletemyteamsdetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                        if(activity instanceof AddMoreLeauge)
                        {
                            ((AddMoreLeauge)activity).updateNumberOfPlayerAdded();
                        }


                    }else
                    {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                        Toast.makeText(activity,jsonObject_LeaugeList.getString("msg").toString(),Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e) {
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();
                    Toast.makeText(activity,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                mProgressDialog.cancel();
                AlertDialog.Builder add = new AlertDialog.Builder(activity);
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

                params.put(Config.PLID, ADDMYPLID);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void getSmidForCompareAndDelete(final String SMIDPLAYER) {

         /*   mProgressDialog.dismiss();
            mProgressDialog.cancel();
            mProgressDialog = ProgressDialog.show(activity, null, "Please Wait...", true);
            mProgressDialog.setMessage("Please Wait...");*/


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
                                    if(_object.getString("SMID").equalsIgnoreCase(SMIDPLAYER))
                                    {
                                        ADD_MYPLID =  _object.getString("PLID").toString();
                                        deletebymyteamsdetailsid(ADD_MYPLID);
                                    }



                                }catch (JSONException jsonException)
                                {
                                    mProgressDialog.dismiss();
                                    mProgressDialog.cancel();
                                    jsonException.printStackTrace();
                                }
                            }

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
                        Toast.makeText(activity,e.toString(),Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();
                    AlertDialog.Builder add = new AlertDialog.Builder(activity);
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
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);

    }




    public void getAddmyteamsdetails(final String addMyTeam_MTID,final String clickItemName,final String clickItemToken,final String clickItemsmid)
    {
        mProgressDialog = ProgressDialog.show(activity, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.addmyteamsdetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {


                        ADD_MYPLID = jsonObject_LeaugeList.getString("PLID");
                        if(activity instanceof AddMoreLeauge)
                        {
                            ((AddMoreLeauge)activity).updateNumberOfPlayerAdded();
                        }
//                        Toast.makeText(activity,ADD_MYPLID,Toast.LENGTH_SHORT).show();


                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                    }else
                    {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                        Toast.makeText(activity,jsonObject_LeaugeList.getString("msg").toString(),Toast.LENGTH_SHORT).show();

                    }

                }catch (Exception e) {
                    mProgressDialog.dismiss();
                    mProgressDialog.cancel();
                    Toast.makeText(activity,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                mProgressDialog.cancel();
                AlertDialog.Builder add = new AlertDialog.Builder(activity);
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

                params.put(Config.MTD_MTID, addMyTeam_MTID);
                params.put(Config.MTD_SMID, clickItemsmid);
                params.put(Config.MTD_TOKEN, clickItemToken);
                params.put(Config.MTD_PLAYERNAME,clickItemName );
                params.put(Config.MTD_PLAYERTYPE, "");




                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
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


                            if(txt_playersName.getText().toString().equalsIgnoreCase(_object.getString("PlayerName").toString()))


                            {

                                Toast.makeText(activity,""+_object.getString("PLID").toString(),Toast.LENGTH_SHORT).show();

                            }

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

                AlertDialog.Builder add = new AlertDialog.Builder(activity);
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



    }


