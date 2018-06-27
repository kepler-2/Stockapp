package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Bean.AddMoreLeagueModel;
import Bean.AddMoreLeagueModel;
import Bean.AllStockModel;

import static com.game.stock.stockapp.AddMoreLeauge.USER_ID;
import static com.game.stock.stockapp.AddMoreLeauge.type;

/**
 * Created by firemoon on 15/2/18.
 */

public class AddMoreLeagueAdapter extends BaseAdapter {

    private Activity activity;
    private Context mContext;
    private LayoutInflater inflater;
    private List<AddMoreLeagueModel> DataList;
    private TextView leaugeName,entryFees,totalWinning,winners,numberOfTeams,playerLeft,btn_join;
    private ProgressDialog mProgressDialog;
    private AllStockAdapter allStockAdapter;



    private ArrayList<AllStockModel> arrayList_allAllStockModels = new ArrayList<>();
    public static String AddMyTeam_MTID;


    public AddMoreLeagueAdapter(Activity activity, List<AddMoreLeagueModel> dataitem,Context context) {
        this.activity = activity;
        this.DataList = dataitem;
        this.mContext=context;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.add_myleague, null);

       /* if (imageLoader == null)
            imageLoader = Controller.getPermission().getImageLoader();*/
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);

//        getUiId(convertView);

        btn_join = (TextView) convertView.findViewById(R.id.btn_join);
        leaugeName = (TextView) convertView.findViewById(R.id.txt_LeagueName);
        entryFees = (TextView) convertView.findViewById(R.id.txt_EntryFees);
        totalWinning = (TextView) convertView.findViewById(R.id.txt_totalWinnings);
        winners = (TextView) convertView.findViewById(R.id.txt_Winners);
        numberOfTeams = (TextView) convertView.findViewById(R.id.txt_numberteams);
        playerLeft = (TextView) convertView.findViewById(R.id.txt_playerLeft);


        AddMoreLeagueModel addMoreLeagueModel = DataList.get(position);
//        leaugeName.setText(addMoreLeagueModel.getLeagueName());
        entryFees.setText(addMoreLeagueModel.getEntryFees());
        totalWinning.setText(addMoreLeagueModel.getWinningAmount());
        winners.setText(addMoreLeagueModel.getNumberOfWinnern());
        numberOfTeams.setText(addMoreLeagueModel.getNumberOfPlayers());
        playerLeft.setText(addMoreLeagueModel.getCurrentTeams());



        final String LEAGUE_ID = addMoreLeagueModel.getLeagueID();
        final String LEAGUE_NAME = addMoreLeagueModel.getLeagueName();
        final String ENTRY_FEES = addMoreLeagueModel.getEntryFees();
        final String WINNIG_AMOUNT = addMoreLeagueModel.getWinningAmount();
        final String PLAY_DATE = addMoreLeagueModel.getAMLDate();
        final String RANK = addMoreLeagueModel.getRank();
        final String SHARE_TYPE = type;


        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                btn_join.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.greybuttonjoin));
//                Color.parseColor("#bdbdbd");
//                btn_join.setTextColor(Color.parseColor("#000000"));

                getAddmyteams(LEAGUE_ID,LEAGUE_NAME,ENTRY_FEES,WINNIG_AMOUNT,PLAY_DATE,RANK,SHARE_TYPE);

                if(activity instanceof AddMoreLeauge)
                {
                    ((AddMoreLeauge)activity).getCreatLeagueButtonFunction();
                }
            }
        });

        return convertView;
    }



    public void getAddmyteams(final String leagueId, final String leagueName,final String entryFee, final String winning_amount,final String play_date,final String aml_rank, final String share_type)
    {

        mProgressDialog = ProgressDialog.show(activity, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.addmyteams, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject_LeaugeList= new JSONObject(response);

                    if(jsonObject_LeaugeList.getString("status").equalsIgnoreCase(String.valueOf(1))) {


                        AddMyTeam_MTID = jsonObject_LeaugeList.getString("MTID");
                        if(AddMyTeam_MTID.equalsIgnoreCase("0"))
                        {
                            Toast.makeText(activity,"There is no ID it gives some trouble please select another team"+AddMyTeam_MTID,Toast.LENGTH_SHORT).show();
                        }
//


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

                String userID = USER_ID;
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put(Config.LEAGUE_ID,leagueId );
                params.put(Config.LEAGUE_NAME,leagueName );
                params.put(Config.ENTRY_FEES,entryFee );
                params.put(Config.USER_ID,userID );
                params.put(Config.WINNIG_AMOUNT,winning_amount );
                params.put(Config.PLAY_DATE,play_date );
                params.put(Config.RANK,aml_rank );
                params.put(Config.SHARE_TYPE,share_type );



                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

}

