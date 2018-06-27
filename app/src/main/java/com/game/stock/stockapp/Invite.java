package com.game.stock.stockapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.stock.stockapp.R;

import java.util.zip.Inflater;


public class Invite extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView toolbar_title;

    TextureView tv1,tv2;
    ImageView fb,gplus,more,watsapp;
/*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
       //View v= Inflater.inflate(R.layout.fragment_invite, container, false);
        View v = inflater.inflate(R.layout.fragment_invite,container,false);
        //initialise values
        TextView tv1=(TextView)v.findViewById(R.id.tv1);
        TextView tv2=(TextView)v.findViewById(R.id.tv2);

        ImageView gplus=(ImageView) v.findViewById(R.id.gplus);
        ImageView fb=(ImageView) v.findViewById(R.id.fb);
        ImageView watsapp=(ImageView)v.findViewById(R.id.watsapp);
        ImageView more=(ImageView) v.findViewById(R.id.more);



        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("INVITE");
    }*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_invite);

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("INVITE");

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.img_back)
        {
            finish();
        }
    }

}
