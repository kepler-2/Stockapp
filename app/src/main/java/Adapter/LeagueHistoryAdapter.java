package Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.game.stock.stockapp.R;

import java.util.List;

import Bean.DataSet;
import Bean.LeaugeHistoryBean;


/**
 * Created by firemoon on 16/4/18.
 */

public class LeagueHistoryAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<LeaugeHistoryBean> DataList;
    private TextView leagueHistoryDate,leagueHistoryAmount,leagueHistoryWinnigAmount,leagueHistoryRank;
//    ImageLoader imageLoader = Controller.getPermission().getImageLoader();

    public LeagueHistoryAdapter(Activity activity, List<LeaugeHistoryBean> leaugeHistoryItems) {
        this.activity = activity;
        this.DataList = leaugeHistoryItems;
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
            convertView = inflater.inflate(R.layout.adapter_league_history, null);

       /* if (imageLoader == null)
            imageLoader = Controller.getPermission().getImageLoader();*/
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);

//        getUiId(convertView);
        leagueHistoryDate = (TextView) convertView.findViewById(R.id.txt_lhDate);
        leagueHistoryAmount = (TextView) convertView.findViewById(R.id.txt_lhleagueAmount);
        leagueHistoryWinnigAmount = (TextView) convertView.findViewById(R.id.txt_winningAmount);
        leagueHistoryRank = (TextView) convertView.findViewById(R.id.txt_lhRank);

        LeaugeHistoryBean m = DataList.get(position);

        leagueHistoryDate.setText(m.getLeagueHistorydate());
        leagueHistoryAmount.setText(m.getEntryFees());
        leagueHistoryWinnigAmount.setText(m.getWiningAmount());
        leagueHistoryRank.setText(m.getRank());

        return convertView;
    }

    public void getUiId(View convertView)
    {

    }
}
