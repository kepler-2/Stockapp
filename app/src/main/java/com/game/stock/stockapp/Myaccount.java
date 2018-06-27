package com.game.stock.stockapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.stock.stockapp.R;


public class Myaccount extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView toolbar_title;
    private Button btn_leagueHistory;

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        Toolbar toolbar = (Toolbar) container.findViewById(R.id.toolbar_myleague);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setTitle("hidg");
        toolbar.setSubtitle("hfis");
        return inflater.inflate(R.layout.fragment_myaccount, container, false);
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_myaccount);

        btn_leagueHistory = (Button) findViewById(R.id.btn_leagueHistory);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        btn_leagueHistory.setOnClickListener(this);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("MY ACCOUNT");

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.img_back)
        {
            finish();
        }
        else if(v.getId()==R.id.btn_leagueHistory)
        {
            startActivity(new Intent(this,Leaguehistory.class));
        }
    }

    /*@Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("MY ACCOUNT");

    }*/
}
