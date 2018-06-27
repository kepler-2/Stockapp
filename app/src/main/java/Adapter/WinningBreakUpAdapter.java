package Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.game.stock.stockapp.R;

import java.util.List;

import Bean.AddMoreLeagueModel;
import Bean.WinningBreakUpModel;

/**
 * Created by firemoon on 12/5/18.
 */

public class WinningBreakUpAdapter extends BaseAdapter {


    private Activity activity;

    private LayoutInflater inflater;
    private List<WinningBreakUpModel> DataList;

    private WinningBreakUpModel winningBreakUpModel;
    private TextView winningBreakup_rank,winningBreakup_amount,winningBreakup_rankto,winningBreakup_dash;

    public WinningBreakUpAdapter(Activity activity, List<WinningBreakUpModel> dataitem) {
        this.activity = activity;
        this.DataList = dataitem;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.winningbreakup_item, null);

        winningBreakup_dash = (TextView) convertView.findViewById(R.id.winningBreakup_dash);
        winningBreakup_rank = (TextView) convertView.findViewById(R.id.winningBreakup_rankfrom);
        winningBreakup_rankto = (TextView) convertView.findViewById(R.id.winningBreakup_rankto);
        winningBreakup_amount = (TextView) convertView.findViewById(R.id.winningBreakup_amount);

        winningBreakUpModel = DataList.get(position);


        winningBreakup_rank.setText(winningBreakUpModel.getRankFrom());
        winningBreakup_rankto.setText(winningBreakUpModel.getRankTo());
        winningBreakup_amount.setText(winningBreakUpModel.getRankFromAmount());

        if(winningBreakup_rankto.getText().toString().equalsIgnoreCase("0"))
        {
            winningBreakup_rankto.setVisibility(View.INVISIBLE);
            winningBreakup_dash.setVisibility(View.INVISIBLE);
        }
        else
        {
            winningBreakup_rankto.setVisibility(View.VISIBLE);
            winningBreakup_dash.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
