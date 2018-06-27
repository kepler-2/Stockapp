package com.game.stock.stockapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
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
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Util.Validation;
import web.CheckInternetConnectio;

public class Register extends AppCompatActivity implements View.OnClickListener{
    Button login, register,btn_verifyid,btn_verifyotp;
    EditText username, fullname, email, password, phone,edttxt_enterOTP;
    private AwesomeValidation awesomeValidation;

    ProgressDialog mProgressDialog;
    private android.os.Handler handler;
    // Create string variable to hold the EditText Value.
    String FirstName,EmailAddress,Password ,MobileNo;
    private RequestQueue requestQueue;
    private LinearLayout linearLayout_footer,linearLayout_login,linearLayout_otpVerification,linearLayout_useridverification;

    private TextView txt_resendOtp;

    // Mobile Number OTP Verification
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;

    String MobilePattern = "[0-9]{10}";
    private static final String TAG = "PhoneAuthActivity";
    private TextView otp_phoneNumber,txt_terms;
    private Toolbar toolbar;
    private String Otp;

    public static  String phone_no;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.bakbtnresize);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText("Registration");

        getSupportActionBar().setDisplayShowTitleEnabled(false);



        //initialise values
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);
        edttxt_enterOTP = (EditText) findViewById(R.id.edttxt_enterOTP);
        otp_phoneNumber = (TextView) findViewById(R.id.otp_phoneNumber);
        txt_terms = (TextView) findViewById(R.id.txt_terms);

        String mystring=new String("*Terms & Conditions");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        txt_terms.setText(content);

        requestQueue = Volley.newRequestQueue(Register.this);

// Assigning Activity this to progress dialog.
        mProgressDialog = new ProgressDialog(Register.this);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        btn_verifyotp = (Button) findViewById(R.id.btn_verify);
        linearLayout_login = (LinearLayout) findViewById(R.id.linearlayout_login);
        linearLayout_footer = (LinearLayout) findViewById(R.id.footer);
        linearLayout_otpVerification = (LinearLayout) findViewById(R.id.linearlayout_otpVerification);
        txt_resendOtp = (TextView) findViewById(R.id.txt_resendOtp);

        btn_verifyotp.setOnClickListener(this);
        txt_terms.setOnClickListener(this);
        linearLayout_footer.setOnClickListener(this);


        txt_resendOtp.setOnClickListener(this);
        login.setOnClickListener(this);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
        register.setOnClickListener(this);
//        firebaseMobileVerification();
    }

//    public void firebaseMobileVerification()
//    {
//        mAuth = FirebaseAuth.getInstance();
//
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential credential) {
//                Log.d(TAG, "onVerificationCompleted:" + credential);
//                signInWithPhoneAuthCredential(credential);
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                Log.w(TAG, "onVerificationFailed", e);
//                if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                    phone.setError("Invalid phone number.");
//                } else if (e instanceof FirebaseTooManyRequestsException) {
//                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
//                            Snackbar.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCodeSent(String verificationId,
//                                   PhoneAuthProvider.ForceResendingToken token) {
//                Log.d(TAG, "onCodeSent:" + verificationId);
//                mVerificationId = verificationId;
//                mResendToken = token;
//            }
//        };
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = task.getResult().getUser();
//
////                            startActivity(new Intent(Register.this, Login.class));
////                            finish();
//                        } else {
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                edttxt_enterOTP.setError("Invalid code.");
//                            }
//                        }
//                    }
//                });
//    }
//
//    private void startPhoneNumberVerification(String phoneNumber) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks
//    }
//
//    private void verifyPhoneNumberWithCode(String verificationId, String code) {
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//        signInWithPhoneAuthCredential(credential);
////        register();
//
//    }
//
//    private void resendVerificationCode(String phoneNumber,PhoneAuthProvider.ForceResendingToken token) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                mCallbacks,         // OnVerificationStateChangedCallbacks
//                token);             // ForceResendingToken from callbacks
//    }
//    private boolean validatePhoneNumber() {
//        String phoneNumber = MobileNo;
//        if (TextUtils.isEmpty(phoneNumber)) {
//            phone.setError("Invalid phone number.");
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
////            startActivity(new Intent(Register.this, MainActivity.class));
////            finish();
//        }
//    }

    public void CheckEditTextIsEmptyOrNot() {


        // Getting values from EditText.
        FirstName = username.getText().toString().trim();
        this.EmailAddress = email.getText().toString().trim();
        MobileNo= phone.getText().toString().trim();
        Password= password.getText().toString().trim();

        CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(Register.this);

        if (_checkInternetConnection.checkInterntConnection()) {

            if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                Toast.makeText(Register.this, "All Field are required", Toast.LENGTH_SHORT).show();
            } else {
                if (Validation.isEmailAddress(email, true)) {
                    String Password = password.getText().toString();
                    if (Password.equalsIgnoreCase("")) {

                        Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_LONG).show();

                    } else {

                        if(phone.getText().toString().matches(MobilePattern)) {
                            register();
//                            Toast.makeText(getApplicationContext(), "phone number is valid", Toast.LENGTH_SHORT).show();

                          /*  Toast.makeText(Register.this, "Please Verify Phone Number", Toast.LENGTH_LONG).show();
                            mProgressDialog = ProgressDialog.show(Register.this, null,
                                    "Please Wait....", true);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.dismiss();

                            linearLayout_login.setVisibility(View.GONE);
                            linearLayout_footer.setVisibility(View.GONE);
//                            linearLayout_otpVerification.setVisibility(View.VISIBLE);
                            linearLayout_useridverification.setVisibility(View.VISIBLE);


                            toolbar.setTitle("OTP Verification");
                            setSupportActionBar(toolbar);

                            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    linearLayout_login.setVisibility(View.VISIBLE);
                                    linearLayout_footer.setVisibility(View.VISIBLE);
                                    linearLayout_otpVerification.setVisibility(View.GONE);
                                    linearLayout_useridverification.setVisibility(View.GONE);
                                    toolbar.setTitle("Registration");
                                    setSupportActionBar(toolbar);
                                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            finish();
                                }
                            });
                                }
                            });*/


                            ;
//                            startPhoneNumberVerification(MobileNo);

                        }
                        else {
                            Toast.makeText(Register.this, "Enter Phone Number Correct", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
//                    phone.setText("");

                    email.setText("");
                    Toast.makeText(Register.this, "Enter valid Email Id",
                            Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            Toast.makeText(Register.this, "Check Internet Connection",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void register() {

        // Showing progress dialog at user registration time.


        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.registerUrl, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    mProgressDialog.setMessage("Please Wait");
                    mProgressDialog.show();
                    //Creating the json object from the response
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))){

                        userID = jsonObject.getString("userid");

                        getMObileVerify(userID);

                    }else{
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();
                        //If not successful user may already have registered
                        Toast.makeText(Register.this, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    mProgressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.dismiss();
                        Toast.makeText(Register.this, error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put(Config.username, FirstName);
                params.put(Config.email,EmailAddress);
                params.put(Config.password, Password);
                params.put(Config.phoneno, MobileNo);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);

        requestQueue.add(stringRequest);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                CheckEditTextIsEmptyOrNot();

                break;
            case R.id.btn_verify:
                verifyotp(userID);
                break;

            case R.id.txt_resendOtp:
                sendotp(userID);
                Toast.makeText(getApplicationContext(),"OTP have been send again",Toast.LENGTH_SHORT).show();

                break;

            case R.id.txt_terms:

                txt_terms.setTextColor(R.color.cardview_shadow_start_color);
                startActivity(new Intent(Register.this,TermsAndConditions.class));
                break;

            case R.id.footer:
                startActivity(new Intent(Register.this,Login.class));
                finish();
                break;

                case R.id.login:
                startActivity(new Intent(Register.this,Login.class));
                finish();
                    break;

        }

    }

    private void sendotp(final String userID) {


        // Showing progress dialog at user registration time.

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
                        linearLayout_footer.setVisibility(View.GONE);
                        linearLayout_otpVerification.setVisibility(View.VISIBLE);


                        toolbar.setTitle("OTP Verification");
                        setSupportActionBar(toolbar);

                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                linearLayout_login.setVisibility(View.VISIBLE);
                                linearLayout_footer.setVisibility(View.VISIBLE);
                                linearLayout_otpVerification.setVisibility(View.GONE);
                                toolbar.setTitle("Registration");
                                setSupportActionBar(toolbar);
                                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finish();
                                    }
                                });
                            }
                        });

                    }else
                    {
                        Toast.makeText(getApplicationContext(),""+jsonObject.getString("msg").toString(),Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Register.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void verifyotp(final String userid) {

        Otp=edttxt_enterOTP.getText().toString().trim();
//        Toast.makeText(getApplicationContext(),""+Otp,Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(),""+userid,Toast.LENGTH_LONG).show();
        // Showing progress dialog at user registration time.
        mProgressDialog.setMessage("Please Wait");
        mProgressDialog.show();

        if(!Otp.equalsIgnoreCase("")) {

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
                            Toast.makeText(Register.this, "" + jsonObject.getString("msg").toString(), Toast.LENGTH_LONG).show();
//                        linearLayout_otpVerification.setVisibility(View.INVISIBLE);
                            // Finish the current Login activity.
//                        finish();

                            // Opening the user profile activity using intent.
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);

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
                            Toast.makeText(Register.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {

                    // Creating Map String Params.
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(Config.userid, userid);
                    params.put(Config.Otp, Otp);


                    return params;
                }
            };

            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(Register.this);

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




    private void getMObileVerify(final String user_ID) {

        // Showing progress dialog at user registration time.
        mProgressDialog.setMessage("Verifing Mobile Number...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.verifymobile, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {

                // Hiding the progress dialog after all task complete.
                mProgressDialog.cancel();
                mProgressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(ServerResponse);

                    if(jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))){

//                        Toast.makeText(Register.this, ""+jsonObject.getString("msg").toString(), Toast.LENGTH_LONG).show();
                        sendotp(user_ID);
                        otp_phoneNumber.setText(MobileNo);

                    }else
                    {
                        Toast.makeText(getApplicationContext(),""+jsonObject.getString("msg").toString(),Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    mProgressDialog.dismiss();
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
                        Toast.makeText(Register.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                params.put(Config.username, user_ID);
//                params.put(Config.email,EmailAddress);
//                params.put(Config.password, Password);
//                params.put(Config.Mobile, MobileNo);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

}

