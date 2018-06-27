package com.game.stock.stockapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Verify_MobileDetails extends Fragment implements View.OnClickListener {

    private TextView txt_PAN, dateView, txt_mobNumber,otp_phoneNumber;
    private EditText edt_PAN, edt_name, edt_mob;

    private Button btn_submitverification, btn_sendOTP;
    LinearLayout linear_MOBILE;
    // DATE PICKER
    DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar;
    private ProgressDialog mProgressDialog;
    private String id, name;
    private SharedPreferences sharedPreferences_customLogin;
    private String pannum, datedob;
    private String mobile_num;
    private String isVerifyMobile, user_ID;

    private LinearLayout linearLayout_login, linearLayout_otpVerification, linearLayout_useridverification;
    private EditText edttxt_enterOTP;
    private String OTP;
    private Button btn_verifyotp;
    private TextView txt_resendOtp,txt_mobverify;


    public Verify_MobileDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.verifyaccount_mob, container, false);

        btn_verifyotp = (Button) view.findViewById(R.id.btn_verify);
        txt_resendOtp = (TextView) view.findViewById(R.id.txt_resendOtp);
        txt_mobverify = (TextView) view.findViewById(R.id.txt_mobverify);

        txt_resendOtp.setOnClickListener(this);
        btn_verifyotp.setOnClickListener(this);

        edttxt_enterOTP = (EditText) view.findViewById(R.id.edttxt_enterOTP);
        edt_mob = (EditText) view.findViewById(R.id.edt_mob);
        btn_sendOTP = (Button) view.findViewById(R.id.btn_sendOTP);
        txt_mobNumber = (TextView) view.findViewById(R.id.txt_mobNumber);
        otp_phoneNumber = (TextView) view.findViewById(R.id.otp_phoneNumber);
        btn_sendOTP.setOnClickListener(this);
        linearLayout_login = (LinearLayout) view.findViewById(R.id.linearlayout_login);
        linearLayout_otpVerification = (LinearLayout) view.findViewById(R.id.linearlayout_otpVerification);

        sharedPreferences_customLogin = getActivity().getSharedPreferences("com.custom.Login", Context.MODE_PRIVATE);
        mobile_num = sharedPreferences_customLogin.getString("mobile", null);
        isVerifyMobile = sharedPreferences_customLogin.getString("IsVerifyMobile", null);
        user_ID = sharedPreferences_customLogin.getString("id", null);
        if (mobile_num != "")
        {
            txt_mobNumber.setText(mobile_num);
        }
        if(!isVerifyMobile.equalsIgnoreCase("0"))
        {
            btn_sendOTP.setVisibility(View.GONE);
            txt_mobverify.setBackground(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
        }

        return view;
    }

    private void sendotp(final String userID) {


        // Showing progress dialog at user registration time.
        mProgressDialog = ProgressDialog.show(getActivity(), null,"Please Wait...", true);

        mProgressDialog.setMessage("Sending OTP...");

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.sendotpurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {

                // Hiding the progress dialog after all task complete.

                try {
                    JSONObject jsonObject=new JSONObject(ServerResponse);

                    if(jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))){
//                        Toast.makeText(Register.this, ""+jsonObject.getString("msg").toString(), Toast.LENGTH_LONG).show();

                        linearLayout_login.setVisibility(View.GONE);
                        linearLayout_otpVerification.setVisibility(View.VISIBLE);


//                        toolbar.setTitle("OTP Verification");
//                        setSupportActionBar(toolbar);

//                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                linearLayout_login.setVisibility(View.VISIBLE);
//                                linearLayout_footer.setVisibility(View.VISIBLE);
//                                linearLayout_otpVerification.setVisibility(View.GONE);
//                                toolbar.setTitle("Registration");
//                                setSupportActionBar(toolbar);
//                                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        finish();
//                                    }
//                                });
//                            }
//                        });
                        otp_phoneNumber.setText(mobile_num);
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();

                    }else
                    {
                        Toast.makeText(getContext(),""+jsonObject.getString("msg").toString(),Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        mProgressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                params.put(Config.userid,userID);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btn_sendOTP)
        {
            if(isVerifyMobile.equalsIgnoreCase("0"))
            {
                sendotp(user_ID);
            }
            else
            {

                btn_sendOTP.setVisibility(View.GONE);
                txt_mobverify.setBackground(getResources().getDrawable(
                        R.drawable.ic_check_circle_black_24dp));

            }
        }
        else if(v.getId()==R.id.btn_verify)
        {
            verifyotp(user_ID);
        }
        else if(v.getId()==R.id.txt_resendOtp)
        {
            sendotp(user_ID);
            Toast.makeText(getApplicationContext(),"OTP have been send again",Toast.LENGTH_SHORT).show();

        }
    }


    private void verifyotp(final String userid) {

        OTP = edttxt_enterOTP.getText().toString().trim();
//        Toast.makeText(getApplicationContext(),""+Otp,Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(),""+userid,Toast.LENGTH_LONG).show();
        // Showing progress dialog at user registration time.
        mProgressDialog.setMessage("Please Wait");
        mProgressDialog.show();

        if(!OTP.equalsIgnoreCase("")) {

            // Creating string request with post method.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.updateotpurl, new Response.Listener<String>() {
                @Override
                public void onResponse(String ServerResponse) {

                    // Hiding the progress dialog after all task complete.

                    mProgressDialog.cancel();
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(ServerResponse);

                        if (jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))) {
                            Toast.makeText(getActivity(), "" + jsonObject.getString("msg").toString(), Toast.LENGTH_LONG).show();
//                        linearLayout_otpVerification.setVisibility(View.INVISIBLE);
                            // Finish the current Login activity.
//                        finish();

                            // Opening the user profile activity using intent.
//                            Intent intent = new Intent(getActivity(), Login.class);
//                            startActivity(intent);

                            linearLayout_login.setVisibility(View.VISIBLE);
                            linearLayout_otpVerification.setVisibility(View.GONE);

                            btn_sendOTP.setVisibility(View.GONE);
                            txt_mobverify.setBackground(getResources().getDrawable(
                                    R.drawable.ic_check_circle_black_24dp));

                        } else {
                            mProgressDialog.cancel();
                            mProgressDialog.dismiss();
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

                            // Hiding the progress dialog after all task complete.
                            mProgressDialog.dismiss();

                            // Showing error message if something goes wrong.
                            Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    // Creating Map String Params.
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(Config.userid, userid);
                    params.put(Config.Otp, OTP);


                    return params;
                }
            };

            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
        else
        {
            mProgressDialog.cancel();
            mProgressDialog.dismiss();
            edttxt_enterOTP.setError("Enter OTP");
        }
    }
}