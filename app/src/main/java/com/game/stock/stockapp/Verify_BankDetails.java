package com.game.stock.stockapp;

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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Verify_BankDetails extends Fragment implements View.OnClickListener {

    private LinearLayout textView_dateView,textView_bankcolor;
    private Button btn_submitverification;
    private EditText editText_name,editText_account,editText_ifsc;

    Spinner stateSpinner;
    String[] stringArrayState = {"Select Your State","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat"+
            "Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal"} ;
    private String id,bankname,bankaccount,ifsc,stateid,spinnerpos;
    private SharedPreferences sharedPreferences_customLogin;
    private ProgressDialog mProgressDialog;
    private TextView txt_Bank;
    private ArrayAdapter<String> spinnerArrayAdapter;
    private EditText editText_state;

    public Verify_BankDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.verifyaccount_bank, container, false);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        stateSpinner= (Spinner) view.findViewById(R.id.stateSpinner);
        spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, stringArrayState);
        stateSpinner.setAdapter(spinnerArrayAdapter);



        txt_Bank= (TextView) view.findViewById(R.id.txt_Bank);
        textView_bankcolor= (LinearLayout) view.findViewById(R.id.linear_BANK);
        editText_name= (EditText) view.findViewById(R.id.edt_bankname);
        editText_account= (EditText) view.findViewById(R.id.edt_accountNum);
        editText_ifsc= (EditText) view.findViewById(R.id.edt_ifsc);
        editText_state= (EditText) view.findViewById(R.id.edt_state);
        btn_submitverification= (Button) view.findViewById(R.id.btn_submitverificationBank);
        btn_submitverification.setOnClickListener(this);
        initview();
        return view;

    }

    private void initview() {


        sharedPreferences_customLogin=getActivity().getSharedPreferences("com.custom.Login", Context.MODE_PRIVATE);
        id = sharedPreferences_customLogin.getString("id",null);
        bankname = sharedPreferences_customLogin.getString("bankname",null);
        bankaccount = sharedPreferences_customLogin.getString("bankaccount",null);
        ifsc = sharedPreferences_customLogin.getString("ifsc",null);
        stateid = sharedPreferences_customLogin.getString("State",null);
        spinnerpos = sharedPreferences_customLogin.getString("spinnerpos",null);




//        textView_bankcolor.setBackgroundColor(Color.parseColor("#302f2f"));
//        txt_Bank.setTextColor(Color.WHITE);

        if(bankname.equalsIgnoreCase(""))
        {

        }else
        {
            editText_name.setText(bankname);
            editText_account.setText(bankaccount);
            editText_ifsc.setText(ifsc);
            stateSpinner.getSelectedItemPosition();
            stateSpinner.setVisibility(View.GONE);
            editText_state.setVisibility(View.VISIBLE);
            editText_state.setText(stateid);
//            stateSpinner.setSelection(Integer.parseInt(spinnerpos));
//                    String text = mySpinner.getSelectedItem().toString();
            editText_name.setEnabled(false);
            editText_account.setEnabled(false);
            editText_ifsc.setEnabled(false);
            editText_state.setEnabled(false);

        }


    }


    @Override
    public void onClick(View view) {

        switch (view.getId())

        {
            case R.id.btn_submitverificationBank:
                if(bankname.equalsIgnoreCase("")) {

                    bankname = editText_name.getText().toString().trim();
                    bankaccount = editText_account.getText().toString().trim();
                    ifsc = editText_ifsc.getText().toString().trim();
                    stateid = stateSpinner.getSelectedItem().toString().trim();

                    verificationResult(id, bankname, bankaccount, ifsc,stateid);
                }else
                {

                    Toast.makeText(getApplicationContext(),"You Cann't put these details many time",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.txt_MOB:
                Intent intent1=new Intent(this.getActivity(),Verify_MobileDetails.class);
                startActivity(intent1);
                break;

            case R.id.txt_PAN:
                Intent intent2=new Intent(this.getActivity(),VerifyPANAccount.class);
                startActivity(intent2);
                break;

        }
    }

    private void verificationResult(final String ID , final String NAME , final String ACCOUNT ,final String IFSC,final String STATE) {

        if (NAME.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Please Insert Your Name", Toast.LENGTH_SHORT).show();
        } else {
            if (ACCOUNT.equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please Insert Your Bank Account", Toast.LENGTH_SHORT).show();
            } else {

                if (IFSC.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please Insert IFSC Code", Toast.LENGTH_SHORT).show();
                } else {
                    if (STATE.equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "Please Choose State", Toast.LENGTH_SHORT).show();
                    } else {
                        mProgressDialog = ProgressDialog.show(this.getActivity(), null, "Please Wait...", true);
                        mProgressDialog.setMessage("Please Wait...");
                        mProgressDialog.show();
                        mProgressDialog.setCancelable(false);

                        // Creating string request with post method.
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.verifyBANK, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {

                                try {
                                    JSONObject jsonObject = new JSONObject(ServerResponse);

                                    if (jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                                        Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);

                                        SharedPreferences.Editor editor = sharedPreferences_customLogin.edit();
                                        editor.putString("bankname", NAME);
                                        editor.putString("bankaccount", ACCOUNT);
                                        editor.putString("ifsc", IFSC);
                                        editor.putString("State", STATE);
                                        editor.putString("spinnerpos", spinnerpos);
                                        editor.commit();

                                        startActivity(intent);
                                        mProgressDialog.dismiss();
                                        mProgressDialog.cancel();
                                        getActivity().finish();

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

                                        Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {

                                Map<String, String> params = new HashMap<String, String>();

                                params.put(Config.userid, ID);
                                params.put("name", NAME);
                                params.put("account", ACCOUNT);
                                params.put("code", IFSC);
                                params.put("stateid", STATE);
                                params.put("bankstate", STATE);


                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
                        requestQueue.add(stringRequest);

                    }
                }
            }
        }
    }
}
