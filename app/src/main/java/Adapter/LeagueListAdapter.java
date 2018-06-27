package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.game.stock.stockapp.AddMoreLeauge;
import com.game.stock.stockapp.Config;
import com.game.stock.stockapp.MyLeagueActivity;
import com.game.stock.stockapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Bean.DataSet;
import Util.Controller;

/**
 * Created by firemoon on 13/2/18.
 */

public class LeagueListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<DataSet> DataList;
    private TextView leaugeName,amount,kingName,queenName;
    private Button btn_edit,btn_Pay;
    private TextView mtid;
//    ImageLoader imageLoader = Controller.getPermission().getImageLoader();

    public LeagueListAdapter(Activity activity, List<DataSet> dataitem) {
        this.activity = activity;
        this.DataList = dataitem;
    }

    @Override
    public int getCount() {return DataList.size();}

    @Override
    public Object getItem(int position) {return DataList.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.my_leaguelist, null);

       /* if (imageLoader == null) imageLoader = Controller.getPermission().getImageLoader();*/


        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);


        btn_edit = (Button) convertView.findViewById(R.id.btn_edit);
        btn_Pay = (Button) convertView.findViewById(R.id.btn_Pay);

        leaugeName = (TextView) convertView.findViewById(R.id.txt_LeagueName);
        amount = (TextView) convertView.findViewById(R.id.txt_Amount);
        kingName = (TextView) convertView.findViewById(R.id.txt_KingName);
        queenName = (TextView) convertView.findViewById(R.id.txt_QueenName);

        final DataSet m = DataList.get(position);

        String plaType = m.getPlayerType();
        leaugeName.setText(m.getLeagueName());
        amount.setText(m.getEntryFees());
        kingName.setText(m.getKingName());
        queenName.setText(m.getQueenName());
        final String MTID =  m.getMTID();

     /*   if(plaType.equalsIgnoreCase("1"))
      {}else if(plaType.equalsIgnoreCase("2")) {queenName.setText(m.getPlayerName());}*/

     btn_edit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {


             Intent intent = new Intent(activity, AddMoreLeauge.class);
             intent.putExtra("Edit","1");
             intent.putExtra("MTID",MTID);
             activity.startActivity(intent);
             activity.finish();
//             getEditLeague(MTID);
         }
     });


        btn_Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(activity instanceof MyLeagueActivity)
                {
                    ((MyLeagueActivity)activity).getInstaMojoPaymentIntegrate();
                }

            } });


        return convertView;
    }
}
