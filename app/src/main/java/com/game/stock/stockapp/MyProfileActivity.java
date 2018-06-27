package com.game.stock.stockapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Util.CircleTransform;

/**
 * Created by firemoon on 19/2/18.
 */


public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences preferences_facebook, preferences_customLogin, preferences_googleLogin;
    public static int PagerPosition=0;
    private SharedPreferences.Editor editor;
    private String facebook_id, custom_id, googleplus_id;
    private EditText edt_userName;
    private ImageView img_profile;
    private TextView txt_uploadImageGallery, txt_changePsw, txt_PanVerify,txt_mobverify,edt_mobile,edt_fullName,edt_email,edt_password,edt_pan,edt_bankDetail;
    private LinearLayout linearLayout_update;

    //UPLOAD IMAGE FROM GALLERY


    private static int IMG_RESULT = 1;
    String ImageDecode;
    Intent intent;
    String[] FILE;
    private SharedPreferences sharedPreferences_customLogin;
    private String panNum, bankName,uid,usrNm,usrem,usrpsw,stateu,isVerifyMobile,mobile_num;
    private TextView txt_bankDetail;
    private ProgressDialog mProgressDialog;

    private String Name,EmailId,MobileNo,Passwd,Panno,BankD;
//    private TabLayout tabLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setContentView(R.layout.activity_myprofile);

        getUi();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.bakbtnresize);
        toolbar.setTitle("My Profile");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Navigationdrawer.class));

                finish();
            }
        });




        getSharedPreferencesValues();

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        sharedPreferences_customLogin = getSharedPreferences("com.custom.Login", Context.MODE_PRIVATE);

        uid = sharedPreferences_customLogin.getString("id", null);
        panNum = sharedPreferences_customLogin.getString("pannum", null);
        bankName = sharedPreferences_customLogin.getString("bankname", null);
        usrNm = sharedPreferences_customLogin.getString("userName", null);
        usrem = sharedPreferences_customLogin.getString("email", null);
        usrpsw = sharedPreferences_customLogin.getString("passw", null);
        stateu = sharedPreferences_customLogin.getString("State", null);
        mobile_num = sharedPreferences_customLogin.getString("mobile", null);
        isVerifyMobile = sharedPreferences_customLogin.getString("IsVerifyMobile", null);
        edt_mobile.setText(mobile_num);

        if (panNum.equalsIgnoreCase("") ) {txt_PanVerify.setText("");}
        else{
            edt_pan.setText(panNum);
            txt_PanVerify.setBackground(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));

        }
        if(isVerifyMobile.equalsIgnoreCase("0"))
        {
            txt_mobverify.setVisibility(View.VISIBLE);
            txt_mobverify.setOnClickListener(this);

        }else
        {
            txt_mobverify.setBackground(getResources().getDrawable(
                    R.drawable.ic_check_circle_black_24dp));
        }

        if (bankName.equalsIgnoreCase("") ) {}else{
            edt_bankDetail.setText(bankName);
            edt_bankDetail.setEnabled(false);
//            txt_bankDetail.setTextColor(Color.rgb(0, 146, 0));
//            txt_bankDetail.setText("ic_check_circle_black_24dp");
            txt_bankDetail.setBackground(getResources().getDrawable(
                    R.drawable.ic_check_circle_black_24dp));
        }
        if (usrNm == null){}else{

            edt_userName.setText(usrNm);
            edt_email.setText(usrem);
            edt_password.setText(usrpsw);
            edt_email.setEnabled(false);
            edt_mobile.setEnabled(false);
            edt_userName.setEnabled(false);
            edt_password.setEnabled(false);

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_uploadImageGallery:

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT);

                Toast.makeText(getApplicationContext(), "Will Upload from Gallery Working On", Toast.LENGTH_SHORT).show();
                break;

            case R.id.txt_changePsw:

                startActivity(new Intent(this, Change_password.class));
                break;

            case R.id.txt_PanVerify:
                PagerPosition=1;
                startActivity(new Intent(this,Verify_Details_MainActivity.class));

                break;

            case R.id.txt_bankDetail:
                PagerPosition=2;
//                if (tabLayout.getSelectedTabPosition() == index) {
//                    // change edittext value
//                } else {
//                    tab.select();
//                }
                startActivity(new Intent(this,Verify_Details_MainActivity.class));
                break;
            case R.id.txt_mobverify:
                PagerPosition=0;
                startActivity(new Intent(this, Verify_Details_MainActivity.class));
                break;

            case R.id.myProfile_Footer:
//                userName=edt_userName.getText().toString().trim();
                Name=edt_fullName.getText().toString().trim();
                EmailId=edt_email.getText().toString().trim();
                MobileNo=edt_mobile.getText().toString().trim();
                Passwd=edt_password.getText().toString().trim();
                Panno=edt_pan.getText().toString().trim();
                BankD=edt_bankDetail.getText().toString().trim();
                UpdateProfile(Name,EmailId,MobileNo,Passwd,Panno,BankD);
                break;
        }


    }

    public void getUi() {
        edt_userName = (EditText) findViewById(R.id.edt_userName);
        edt_fullName = (TextView) findViewById(R.id.edt_fullName);
        edt_email = (TextView) findViewById(R.id.edt_email);
        edt_mobile = (TextView) findViewById(R.id.edt_mobile);
        edt_password = (TextView) findViewById(R.id.edt_password);
        edt_pan = (TextView) findViewById(R.id.edt_pan);
        edt_bankDetail = (TextView) findViewById(R.id.edt_bankDetail);


        img_profile = (ImageView) findViewById(R.id.img_profile);

        linearLayout_update = (LinearLayout) findViewById(R.id.myProfile_Footer);
        txt_changePsw = (TextView) findViewById(R.id.txt_changePsw);
        txt_mobverify = (TextView) findViewById(R.id.txt_mobverify);
        txt_PanVerify = (TextView) findViewById(R.id.txt_PanVerify);
        txt_bankDetail = (TextView) findViewById(R.id.txt_bankDetail);
        txt_uploadImageGallery = (TextView) findViewById(R.id.txt_uploadImageGallery);
        txt_uploadImageGallery.setOnClickListener(this);
        txt_changePsw.setOnClickListener(this);
        txt_PanVerify.setOnClickListener(this);
        txt_bankDetail.setOnClickListener(this);
        linearLayout_update.setOnClickListener(this);
        txt_mobverify.setOnClickListener(this);
    }

    private void getSharedPreferencesValues() {
        preferences_facebook = getSharedPreferences("com.facebook.data", Context.MODE_PRIVATE);
        preferences_customLogin = getSharedPreferences("com.custom.Login", Context.MODE_PRIVATE);
        preferences_googleLogin = getSharedPreferences("com.gogleplus.login", Context.MODE_PRIVATE);

        facebook_id = preferences_facebook.getString("id", null);
        custom_id = preferences_customLogin.getString("id", null);
        googleplus_id = preferences_googleLogin.getString("id", null);

        if (facebook_id != null) {
            getSharedPrefData_FacebookLogin();
        } else if (custom_id != null) {
            getSharedPrefData_CustomLogin();
        } else if (googleplus_id != null) {
            getSharedPrefData_GoogleLogin();
        }

    }

    public void getSharedPrefData_CustomLogin() {
        edt_userName.setText("");
        edt_fullName.setText(preferences_customLogin.getString("name", null));
        edt_fullName.setFocusable(false);
        edt_email.setText(preferences_customLogin.getString("email", null));
        edt_mobile.setText(preferences_customLogin.getString("mobile", null));
        edt_password.setText(preferences_customLogin.getString("password", null));
        edt_pan.setText("");
        edt_bankDetail.setText("");


        //PROFILE PICTURE

    }

    public void getSharedPrefData_FacebookLogin() {
        edt_userName.setText("");
        edt_fullName.setText(preferences_facebook.getString("name", null));
        edt_fullName.setFocusable(false);
        edt_email.setText("");
        edt_mobile.setText("");
        edt_password.setText("");
        edt_pan.setText("");
        edt_bankDetail.setText("");

//        ??FACEBOOK PROFILE PICTURE..

        String url = preferences_facebook.getString("profile_pic", null);
        Glide.with(getApplicationContext()).load(url).transform(new CircleTransform(this)).into(img_profile);

    }

    public void getSharedPrefData_GoogleLogin() {
        edt_userName.setText("");
        edt_fullName.setText(preferences_googleLogin.getString("name", null));
        edt_fullName.setFocusable(false);
        edt_email.setText(preferences_googleLogin.getString("email", null));
        edt_mobile.setText("");
        edt_password.setText("");
        edt_pan.setText("");
        edt_bankDetail.setText("");


        //        ??GMAIL LOGIN PROFILE PICTURE..

        String url = preferences_googleLogin.getString("profile_pic", null);
        Glide.with(getApplicationContext()).load(url).transform(new CircleTransform(this)).into(img_profile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_RESULT && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.img_profile);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
//        try {
//
//            if (requestCode == IMG_RESULT && resultCode == RESULT_OK
//                    && null != data) {
//
//
//                Uri URI = data.getData();
//                String[] FILE = {MediaStore.Images.Media.DATA};
//
//
//                Cursor cursor = getContentResolver().query(URI,
//                        FILE, null, null, null);
//
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(FILE[0]);
//                ImageDecode = cursor.getString(columnIndex);
//                cursor.close();
////dfsdfdsfds
////                Glide.with(getApplicationContext()).load(Uri.parse(ImageDecode)).into(img_profile);
//                img_profile.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));

//        }
//        } catch (Exception e) {
//            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
//                    .show();
//        }
//
//    }

        }}

    public void UpdateProfile( final String NAME, final String EMAIL, final String MOBILE , final String PSW
    ,final String PANNO,final String BANKACC) {

        /*if (USERNAME.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Please Insert User Name", Toast.LENGTH_SHORT).show();
        } else {*/
            if (NAME.equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please Insert Your Name", Toast.LENGTH_SHORT).show();
            } else {

                if (EMAIL.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please Insert Email Id", Toast.LENGTH_SHORT).show();
                } else {
                    if (MOBILE.equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "Please Insert Mobile", Toast.LENGTH_SHORT).show();
                    } else {
                        if(PSW.equalsIgnoreCase(""))
                        {
                            Toast.makeText(getApplicationContext(), "Please Insert Password", Toast.LENGTH_SHORT).show();

                        }else {
                            if(PANNO.equalsIgnoreCase(""))
                            {
                                Toast.makeText(getApplicationContext(), "Please Insert PAN NUMBER", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if(BANKACC.equalsIgnoreCase(""))
                                {
                                    Toast.makeText(getApplicationContext(), "Please Insert BANK Details", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    updateAllDetails(EMAIL);
                                }
                            }
                        }
                    }
                }
            }
//        }
    }


    public void updateAllDetails(final String _email) {
        mProgressDialog = ProgressDialog.show(this, null, "Please Wait...", true);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.updateProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {

                try {
                    JSONObject jsonObject = new JSONObject(ServerResponse);

                    if (jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                        Intent intent = new Intent(getApplicationContext(), Navigationdrawer.class);

                        SharedPreferences.Editor editor = sharedPreferences_customLogin.edit();
                        editor.putString("email", _email);
//                        editor.putString("userName",userName);
                        editor.putString("passw",Passwd);
                        editor.commit();

                        startActivity(intent);
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                        finish();

                    } else {
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                        Toast.makeText(getApplicationContext(), "" + jsonObject.getString("msg").toString(), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        mProgressDialog.dismiss();

                        Toast.makeText(MyProfileActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put(Config.userid, uid);
                params.put("username", Name);
                params.put("email", EmailId);
                params.put("psw", Passwd);
                params.put("mobile", MobileNo);
                params.put("pan", Panno);
                params.put("bank", BankD);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MyProfileActivity.this);
        requestQueue.add(stringRequest);

    }
}
