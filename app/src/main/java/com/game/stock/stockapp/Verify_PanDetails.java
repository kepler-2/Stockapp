package com.game.stock.stockapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;


public class Verify_PanDetails extends Fragment implements View.OnClickListener {


    Spinner stateSpinner;
    String[] stringArrayState = {"Select Your State","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat"+
            "Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal"} ;

    private TextView txt_PAN,dateView;
    private EditText edt_PAN,edt_name;

    private Button btn_submitverification,button;
    ImageView image;
    // DATE PICKER
    DatePickerDialog.OnDateSetListener date;
    private Calendar myCalendar;
    private ProgressDialog mProgressDialog;
    private String id,name;
    private SharedPreferences sharedPreferences_customLogin;
    private String pannum,datedob;
    private int IMG_RESULT=1;
    private String ImageDecode;
    private ImageView pan_image;
    private Bitmap bitmap;

    public Verify_PanDetails() {
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
        View view=inflater.inflate(R.layout.verifyaccount_pan, container, false);



        stateSpinner= (Spinner) view.findViewById(R.id.stateSpinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, stringArrayState);
        stateSpinner.setAdapter(spinnerArrayAdapter);

        edt_PAN = (EditText) view.findViewById(R.id.edt_PAN);
        edt_name = (EditText) view.findViewById(R.id.edt_name);
        txt_PAN = (TextView) view.findViewById(R.id.txt_PAN);



//        LinearLayout linear_Pan = (LinearLayout) findViewById(R.id.linear_PAN);
//        linear_Pan.setBackgroundColor(Color.parseColor("#302f2f"));
//        txt_PAN.setTextColor(Color.WHITE);

        dateView = (TextView) view.findViewById(R.id.edt_DOB);

        image=(ImageView)view.findViewById(R.id.img_profile);
        pan_image=(ImageView)view.findViewById(R.id.upload_panimage);
        dateView.setOnClickListener(this);

        button=(Button)view.findViewById(R.id.txt_uploadImageGallery);
        button.setOnClickListener(this);

        btn_submitverification = (Button) view.findViewById(R.id.btn_submitverification);
        btn_submitverification.setOnClickListener(this);


        initView();




        return  view;
    }





    @SuppressLint("ResourceAsColor")
    private void initView() {

        sharedPreferences_customLogin=getActivity().getSharedPreferences("com.custom.Login", Context.MODE_PRIVATE);
        id = sharedPreferences_customLogin.getString("id",null);
        name = sharedPreferences_customLogin.getString("PANName",null);
        pannum = sharedPreferences_customLogin.getString("pannum",null);
        datedob = sharedPreferences_customLogin.getString("dob",null);
        if(pannum==null)
        {
            pannum="";
            datedob="";
            name="";
        }
        if(pannum.equalsIgnoreCase("")) {}else{
            edt_name.setText(name);
            edt_name.setEnabled(false);
            edt_PAN.setText(pannum);
            edt_PAN.setEnabled(false);
            dateView.setText(datedob);
            dateView.setEnabled(true);
    }


}

    @Override
    public void onClick(View view) {


        switch(view.getId())
        {
            case R.id.txt_uploadImageGallery:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

//                startActivityForResult(intent, IMG_RESULT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMG_RESULT);
//
//                Toast.makeText(getApplicationContext(), "Will Upload from Gallery Working On", Toast.LENGTH_SHORT).show();
                break;
//                callImageGallery();
//                break;

            case R.id.edt_DOB:

                callDatePicker();
                break;

            case R.id.btn_submitverification:



                if(pannum.equalsIgnoreCase("")) {
                    String name = edt_name.getText().toString().trim();
                    String pannum = edt_PAN.getText().toString().trim();
                    String text_DOB = dateView.getText().toString().trim();
                    verificationResult(id, name, pannum, text_DOB);
                }else
                {

                    Toast.makeText(getApplicationContext(),"You Cann't put these details many time",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.txt_MOB:
                Intent intent1=new Intent(this.getActivity(),VerifyMobDetail.class);
                startActivity(intent1);
                break;

            case R.id.txt_Bank:
                Intent intent2=new Intent(this.getActivity(),VerifyBankDetail.class);
                startActivity(intent2);
                break;
        }

    }

    public void callDatePicker()
    {
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        new DatePickerDialog(getActivity(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        dateView.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            if (requestCode == IMG_RESULT && resultCode == RESULT_OK && data != null && data.getData() != null) {

                Uri uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                    // Log.d(TAG, String.valueOf(bitmap));

                    ImageView imageView = (ImageView) getView().findViewById(R.id.upload_panimage);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void verificationResult(final String ID , final String NAME , final String PANNUM ,final String DOB) {

        if (PANNUM.equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Please Insert PAN Card detail", Toast.LENGTH_SHORT).show();
        } else {
            if (DOB.equalsIgnoreCase("")) {
                Toast.makeText(getApplicationContext(), "Please Date Of Birth", Toast.LENGTH_SHORT).show();
            } else {

                mProgressDialog = ProgressDialog.show(this.getActivity(), null, "Please Wait...", true);
                mProgressDialog.setMessage("Please Wait...");
                mProgressDialog.show();
                mProgressDialog.setCancelable(false);

                // Creating string request with post method.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.verifyPAN, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        try {
                            JSONObject jsonObject = new JSONObject(ServerResponse);

                            if (jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))) {

                                Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);

                                SharedPreferences.Editor editor=sharedPreferences_customLogin.edit();
                                editor.putString("PANName",NAME);
                                editor.putString("pannum",PANNUM);
                                editor.putString("dob",DOB);
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
                        params.put("pnum", PANNUM);
                        params.put("birthdate", DOB);


                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
                requestQueue.add(stringRequest);

            }
        }
    }

}
