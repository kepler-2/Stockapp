package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.game.stock.stockapp.AddMoreLeauge;
import com.game.stock.stockapp.Config;
import com.game.stock.stockapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Bean.AddMoreLeagueModel;
import Bean.AllStockModel;
import Bean.MyTeamsDetailsBean;

import static com.game.stock.stockapp.AddMoreLeauge.textKINGQUEENTotal;


/**
 * Created by firemoon on 4/5/18.
 */

public class MyTeamsDetailsAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<MyTeamsDetailsBean> DataList;
    private TextView txt_playersName;
    private ImageView img_kwhite,img_qwhite,img_kblue,img_qblue,img_qcrown,img_kcrown,img_twox,img_fourx;
    private CardView card_view;
    private MyTeamsDetailsBean myTeamsDetailsBean;
    private ProgressDialog mProgressDialog;
  //  private List<Boolean> isBtnClickedKING;
  //  private List<Boolean> isBtnClickedQUEEN;
    private String playerID,myteamsID,SMID,getTokenID,playerName,playerType,stockrate;

    private int kingPosition=-1,queenPostion=-1;
    public static String plid = "0";
    public static String Qplid = "0";
    public static String pltype = "0";

    //private int accessPosition;


    public MyTeamsDetailsAdapter(Activity activity, List<MyTeamsDetailsBean> dataitem) {
        this.activity = activity;
        this.DataList = dataitem;
    //    isBtnClickedKING= Arrays.asList(new Boolean[dataitem.size()]);
     //   isBtnClickedQUEEN= Arrays.asList(new Boolean[dataitem.size()]);
     //   Collections.fill(isBtnClickedKING,true);
     //   Collections.fill(isBtnClickedQUEEN,true);
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
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.my_teams_details_listitem, null);


        txt_playersName = (TextView) convertView.findViewById(R.id.txt_playersName);
        img_twox = (ImageView) convertView.findViewById(R.id.img_twox);
        img_fourx = (ImageView) convertView.findViewById(R.id.img_fourx);
        img_kcrown = (ImageView) convertView.findViewById(R.id.img_kcrown);
        img_qcrown = (ImageView) convertView.findViewById(R.id.img_qcrown);
        img_kwhite = (ImageView) convertView.findViewById(R.id.img_kwhite);
        img_qwhite = (ImageView) convertView.findViewById(R.id.img_qwhite);
        img_kblue = (ImageView) convertView.findViewById(R.id.img_kblue);
        img_qblue = (ImageView) convertView.findViewById(R.id.img_qblue);
        card_view = (CardView) convertView.findViewById(R.id.card_view);


       /* if(position == AddMoreLeauge.mSelectedItem)
        {

            card_view.setCardBackgroundColor(Color.WHITE);
        }*/
        myTeamsDetailsBean = DataList.get(position);

        txt_playersName.setText(myTeamsDetailsBean.getPlayerName());



        /*if(if_txt_afternextbn==1)
        {
            img_kwhite.setEnabled(false);
        }
        else if(if_txt_afternextbn == 2)
        {
            img_qwhite.setEnabled(false);
        }
        else if(if_txt_afternextbn == 0){
            img_kwhite.setEnabled(true);
            img_qwhite.setEnabled(false);


        }*/
        img_kwhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textKINGQUEENTotal==0)
                {
                    textKINGQUEENTotal++;
                }

                if(position==queenPostion){

                }else {
                    kingPosition=position;
//                    int sK = AddMoreLeauge.textKINGQUEENTotal;
//                    if(sK == 1 && sK == 2 ) {
//                        textKINGQUEENTotal = 1;
//                    }
                    MyTeamsDetailsBean myTeamsDetailsBean_loal=DataList.get(position);
                    playerID = myTeamsDetailsBean_loal.getPlayerID();
                    myteamsID = myTeamsDetailsBean_loal.getMyteamsID();
                    SMID = myTeamsDetailsBean_loal.getSMID();
                    getTokenID = myTeamsDetailsBean_loal.getTokenID();
                    playerName = myTeamsDetailsBean_loal.getPlayerName();
                    playerType = myTeamsDetailsBean_loal.getPlayerType();
                    stockrate = myTeamsDetailsBean_loal.getStockrate();


//                    if(pltype.equalsIgnoreCase("2")){}else{
                    if(plid.equalsIgnoreCase("0")){}else{
                    getUpdatePlayerByPlayerID(plid);}//}
                    String strplayerType = "1";
                   getUpdateKingPlayer(strplayerType);


                    if (activity instanceof AddMoreLeauge) {
                        ((AddMoreLeauge) activity).updateMYTeamDetails();

                    }

                }
//
            }

        });

        img_qwhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(textKINGQUEENTotal==1)
                {
                    textKINGQUEENTotal++;
                }



                if(position==kingPosition){

                }else {
                    queenPostion=position;
//                    int sK = AddMoreLeauge.textKINGQUEENTotal;
//                    if(sK == 1 && sK == 2) {
//                        textKINGQUEENTotal = 2;
//                    }

                    MyTeamsDetailsBean myTeamsDetailsBean_loal=DataList.get(position);
                    playerID = myTeamsDetailsBean_loal.getPlayerID();
                    myteamsID = myTeamsDetailsBean_loal.getMyteamsID();
                    SMID = myTeamsDetailsBean_loal.getSMID();
                    getTokenID = myTeamsDetailsBean_loal.getTokenID();
                    playerName = myTeamsDetailsBean_loal.getPlayerName();
                    playerType = myTeamsDetailsBean_loal.getPlayerType();
                    stockrate = myTeamsDetailsBean_loal.getStockrate();


                    if(Qplid.equalsIgnoreCase("0"))
                    {

                    }else{

                        getUpdatePlayerByPlayerID(Qplid);
                    }
                    String strplayerType = "2";
                    getUpdateQueenPlayer(strplayerType);



                    if (activity instanceof AddMoreLeauge) {
                        ((AddMoreLeauge) activity).updateMYTeamDetails();
                    }

                }
            }
        });

        if(position==kingPosition)
        {

           /* if(pltype.equalsIgnoreCase("0"))
            {
                img_kwhite.setVisibility(View.VISIBLE);

        }else{*/

            img_kwhite.setVisibility(View.GONE);
            img_kblue.setVisibility(View.VISIBLE);
            img_fourx.setVisibility(View.VISIBLE);
            img_kcrown.setVisibility(View.VISIBLE);//}

        }
        else
        {
            img_kwhite.setVisibility(View.VISIBLE);
            img_kblue.setVisibility(View.GONE);
            img_fourx.setVisibility(View.GONE);
            img_kcrown.setVisibility(View.GONE);
//            Toast.makeText(activity,"No King Position......."+kingPosition,Toast.LENGTH_SHORT).show();


        }



        if(position==queenPostion)
        {

            if(pltype.equalsIgnoreCase("0"))
            {
                img_kwhite.setVisibility(View.VISIBLE);

            }else{
            img_qwhite.setVisibility(View.GONE);
            img_qblue.setVisibility(View.VISIBLE);
            img_twox.setVisibility(View.VISIBLE);
            img_qcrown.setVisibility(View.VISIBLE);}


        }
        else
        {
            img_qwhite.setVisibility(View.VISIBLE);
            img_qblue.setVisibility(View.GONE);
            img_twox.setVisibility(View.GONE);
            img_qcrown.setVisibility(View.GONE);
//            Toast.makeText(activity,"No Queen Position"+queenPostion,Toast.LENGTH_SHORT).show();

//            String strplayerType = "0";
//            getUpdateKingPlayer(strplayerType);
        }

        return convertView;
    }


    public void getUpdateKingPlayer(final String playerType)
    {

            mProgressDialog = ProgressDialog.show(activity, null, "Please Wait...", true);
            mProgressDialog.setMessage("Please Wait...");


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.updatebymyteamsdetailsid, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try
                    {
                        JSONObject jsonObject_LeaugeList= new JSONObject(response);

                        if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {




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

                    pltype=playerType;
                    plid=playerID;
                    // Creating Map String Params.
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("plid",playerID);
                    params.put("mtid",myteamsID);
                    params.put("smid",SMID);
                    params.put("token",getTokenID);
                    params.put("playername",playerName);
                    params.put("playertype",playerType);
                    params.put("stockrate",stockrate);
                    return params;
                }
            };

            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }public void getUpdateQueenPlayer(final String playerType)
    {

            mProgressDialog = ProgressDialog.show(activity, null, "Please Wait...", true);
            mProgressDialog.setMessage("Please Wait...");


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.updatebymyteamsdetailsid, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try
                    {
                        JSONObject jsonObject_LeaugeList= new JSONObject(response);

                        if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {




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

                    pltype=playerType;
                    Qplid=playerID;
                    // Creating Map String Params.
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("plid",playerID);
                    params.put("mtid",myteamsID);
                    params.put("smid",SMID);
                    params.put("token",getTokenID);
                    params.put("playername",playerName);
                    params.put("playertype",playerType);
                    params.put("stockrate",stockrate);
                    return params;
                }
            };

            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }


        public void getUpdatePlayerByPlayerID(final String plid)
    {

         /*   mProgressDialog = ProgressDialog.show(activity, null, "Please Wait...", true);
            mProgressDialog.setMessage("Please Wait...");*/


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.updatePlayerByPlayerID, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try
                    {
                        JSONObject jsonObject_LeaugeList= new JSONObject(response);

                        if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {



//                            Toast.makeText(activity,jsonObject_LeaugeList.getString("msg").toString(),Toast.LENGTH_SHORT).show();

//                            mProgressDialog.dismiss();
//                            mProgressDialog.cancel();
                        }else
                        {
//                            mProgressDialog.dismiss();
//                            mProgressDialog.cancel();

                        }

                    }catch (Exception e) {
//                        mProgressDialog.dismiss();
//                        mProgressDialog.cancel();
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
                    params.put("plid",plid);

                    return params;
                }
            };

            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
    }



