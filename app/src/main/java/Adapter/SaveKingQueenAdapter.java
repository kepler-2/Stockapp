package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import Bean.MyTeamsDetailsBean;

import static com.game.stock.stockapp.AddMoreLeauge.if_txt_afternextbn;
import static com.game.stock.stockapp.AddMoreLeauge.textKINGQUEENTotal;

/**
 * Created by firemoon on 7/5/18.
 */

public class SaveKingQueenAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<MyTeamsDetailsBean> DataList;
    private TextView txt_playersName,txt_kingsName,txt_QueensName;
    private ImageView img_kwhite,img_qwhite,img_kblue,img_qblue,img_qcrown,img_kcrown,img_twox,img_fourx;
    private CardView card_view;
    private MyTeamsDetailsBean myTeamsDetailsBean;
    private ProgressDialog mProgressDialog;
 //   private List<Boolean> isBtnClickedKING;
  //  private List<Boolean> isBtnClickedQUEEN;
    //private int accessPosition;


    public SaveKingQueenAdapter(Activity activity, List<MyTeamsDetailsBean> dataitem) {
        this.activity = activity;
        this.DataList = dataitem;
     //   isBtnClickedKING= Arrays.asList(new Boolean[dataitem.size()]);
     //   isBtnClickedQUEEN= Arrays.asList(new Boolean[dataitem.size()]);
     //   Collections.fill(isBtnClickedKING,true);
      //  Collections.fill(isBtnClickedQUEEN,true);
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
            convertView = inflater.inflate(R.layout.saveking_queen_listitem, null);


        txt_playersName = (TextView) convertView.findViewById(R.id.txt_playersName);
        txt_kingsName = (TextView) convertView.findViewById(R.id.txt_kingsName);
        txt_QueensName = (TextView) convertView.findViewById(R.id.txt_QueensName);


        card_view = (CardView) convertView.findViewById(R.id.card_view);


       /* if(position == AddMoreLeauge.mSelectedItem)
        {

            card_view.setCardBackgroundColor(Color.WHITE);
        }*/
        myTeamsDetailsBean = DataList.get(position);

        txt_playersName.setText(myTeamsDetailsBean.getPlayerName());

        String getPlayerType = myTeamsDetailsBean.getPlayerType();

      /*  if(getPlayerType.equalsIgnoreCase("1"))
        {
//            String getPlayerType = myTeamsDetailsBean.getPlayerType();

            txt_kingsName.setText("KING");

        }

        else if(getPlayerType.equalsIgnoreCase("2"))
        {
            txt_kingsName.setText("Queens");
        }
*/







        return convertView;
    }


    public void getUpdateKingPlayer()
    {

        mProgressDialog = ProgressDialog.show(activity, null, "Changing Your Password in...", true);
        mProgressDialog.setMessage("Please Wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.updatebymyteamsdetailsid, new Response.Listener<String>() {
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

                        /*AlertDialog.Builder add = new AlertDialog.Builder(AddMoreLeauge.this);
                        add.setMessage("You have not created any league yet , please click on ADD MORE LEAGUE button to create your league").setCancelable(true);
                        AlertDialog alert = add.create();
                        alert.setTitle("NO LEAGUE CREATE");
                        alert.show();*/
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
                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}



