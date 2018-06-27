package com.game.stock.stockapp;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import Util.PrefUtil;
import Util.Validation;
import web.CheckInternetConnectio;


//25906637070-7jmrb0g8bqkt4u7m9lbdlsahq80gprls.apps.googleusercontent.com

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener , View.OnClickListener {

    // A progress dialog to display when the user is connecting in
    // case there is a delay in any of the dialogs being ready.
    ProgressDialog mProgressDialog;
    private android.os.Handler handler;
    String EmailAddress,Password;

    private RequestQueue requestQueue;
    TextView register, forgotpwd;
    EditText email, password;
    Button login, gmail;
    private AwesomeValidation awesomeValidation;
    private static final int RC_SIGN_IN = 0;

    private static final String TAG = "SignInTestActivity";
    public static GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private Button fb,btn_googlePlus;
    private LoginButton loginButton;
    private String fbname,fbemail,fbgender,fbbirthday,id;
    private GoogleApiClient  mGoogleApiClient;
    private SignInButton signInButton;

    SharedPreferences.Editor editor;
    private String u_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_login);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(Login.this);

       mProgressDialog = new ProgressDialog(Login.this);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        getUI();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.bakbtnresize);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText("Login");

        getSupportActionBar().setDisplayShowTitleEnabled(false);




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            }
        });




        login.setOnClickListener(this);

        boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
        fb = (Button) findViewById(R.id.fb);
        btn_googlePlus = (Button) findViewById(R.id.btn_googlePlus);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginButton.performClick();
                facebookLogin();
            }
        });
        btn_googlePlus=(Button) findViewById(R.id.btn_googlePlus);
        btn_googlePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gmailLoginIntegration();

                signInButton.performClick();


                signIn();
            }
        });
    }

    private void gmailLoginIntegration()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

         signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);

    }
    public void facebookLogin()
    {



        final String EMAIL = "email";
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        List< String > permissionNeeds = Arrays.asList("user_photos", "email", "user_birthday", "public_profile", "AccessToken");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
                public void onSuccess(LoginResult loginResult) {

                    System.out.println("onSuccess");

                    String accessToken = loginResult.getAccessToken().getToken();
                    Log.i("accessToken", accessToken);

                    final GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback()
                            {
                                @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.i("LoginActivity",response.toString());


                                    String profile_pic=null;
                                    try {
                                        id = object.getString("id");
                                         profile_pic="http://graph.facebook.com/"+id+"/picture?type=large";
                                        fbname = object.getString("name");
//                                        fbemail = object.getString("email");
                                        fbgender = object.getString("gender");
//                                        fbbirthday = object.getString("birthday");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    SharedPreferences sharedPreferences_facebook= getSharedPreferences("com.facebook.data",Context.MODE_PRIVATE);
                                    editor=sharedPreferences_facebook.edit();
                                    editor.putString("id",id);
                                    editor.putString("name",fbname);
                                    editor.putString("email",fbgender);
                                    editor.putString("profile_pic",profile_pic);
                                    editor.commit();
                                    startActivity(new Intent(Login.this,Navigationdrawer.class));

                            }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields","id,name,gender");
                    request.setParameters(parameters);
                    request.executeAsync();
                }
                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Log.v("LoginActivity", exception.getCause().toString());
                    }
                });
    }

   
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else
        {
            callbackManager.onActivityResult(requestCode, responseCode, data);

        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e(TAG, "display name: " + acct.getDisplayName());
//
           String personName = acct.getDisplayName();
            Uri personPhoto = acct.getPhotoUrl();

//            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();
            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + "personPhotoUrl");

            SharedPreferences sharedPreference_googleLogin = getSharedPreferences("com.gogleplus.login",Context.MODE_PRIVATE);
            editor=sharedPreference_googleLogin.edit();
            editor.putString("id",acct.getId().toString());
            editor.putString("name",personName);
            if(personPhoto!=null)
            {
                     editor.putString("profile_pic",personPhoto.toString());
            }else{
                String URL="http://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg";
                editor.putString("profile_pic",URL);        }

            editor.putString("email",email);
            editor.commit();

            startActivity(new Intent(getApplicationContext(),Navigationdrawer.class));
            finish();
//            Log.e(TAG, "Name: " + personName + ", email: " + email
//                   /* + ", Image: " + personPhotoUrl*/);

            }}

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public static void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }


    private void login(final String user_pass) {

        mProgressDialog = ProgressDialog.show(Login.this, null,"Logging you in...", true);
        mProgressDialog.setMessage("Logging you in...");
        mProgressDialog.show();
        mProgressDialog.setCancelable(false);

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.loginUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        mProgressDialog.dismiss();
                        mProgressDialog.cancel();

//                        Toast.makeText(Login.this, ServerResponse, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject=new JSONObject(ServerResponse);

                            if(jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))){



                                jsonObject=new JSONObject(ServerResponse).getJSONObject("info");



                                String userId= jsonObject.getString("Id").toString();
                                getUserDetailbyUserId(userId,user_pass);

                                // Sending User Email to another activity using intent.
                                //intent.putExtra(Config.fullname, fullname1);


                            }else
                            {
                                Toast.makeText(getApplicationContext(),""+jsonObject.getString("msg").toString(),Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       /* // Matching server responce message to our text.
                        if (ServerResponse.equalsIgnoreCase("Data Matched")) {
                            // Launch User activity
                             // Opening the user profile activity using intent.
                            Intent intent = new Intent(Login.this, Navigationdrawer.class);
                            // If response matched then show the toast.
                            Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_LONG).show();

                            // Finish the current Login activity.
                            finish();

                            // Sending User Email to another activity using intent.
                            //intent.putExtra(Config.fullname, fullname1);

                            startActivity(intent);
                        } else {

                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(Login.this, ServerResponse, Toast.LENGTH_LONG).show();

                        }*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        mProgressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Login.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                // params.put("User_Email", EmailHolder);
                // params.put("User_Password", PasswordHolder);
                params.put(Config.emaillogin, EmailAddress);
                    params.put(Config.passwordlogin, Password);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    public void getUserDetailbyUserId(final String UserId , final String u_password)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.alldetailUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String ServerResponse) {

                try {
                    JSONObject jsonObject=new JSONObject(ServerResponse);

                    if(jsonObject.getString("status").equalsIgnoreCase(String.valueOf(1))){

                        jsonObject=new JSONObject(ServerResponse).getJSONObject("info");

                        SharedPreferences sharedPreferences_customLogin=getSharedPreferences("com.custom.Login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences_customLogin.edit();
                        editor.putString("id",UserId);
                        editor.putString("password",u_password);
                        editor.putString("name",jsonObject.getString(Config.LOGIN_FULL_NAME).toString());
                        editor.putString("mobile",jsonObject.getString(Config.LOGIN_MOBILE).toString());
                        editor.putString("email",jsonObject.getString(Config.LOGIN_EMAILID).toString());
                        editor.putString("City",jsonObject.getString(Config.LOGIN_CITY).toString());
                        editor.putString("Block",jsonObject.getString(Config.LOGIN_EMAILID).toString());
                        editor.putString("IsWithdrawal",jsonObject.getString(Config.LOGIN_IS_WITHDRAW).toString());
                        editor.putString("IsVerifyMobile",jsonObject.getString(Config.LOGIN_IS_MOBILE_VERIFY).toString());
                        editor.putString("IsverifyState",jsonObject.getString(Config.LOGIN_IS_STATE_VERIFY).toString());
                        editor.putString("IsverifyAccount",jsonObject.getString(Config.LOGIN_IS_ACCOUNT_VERIFY).toString());
                        editor.putString("IsVerifyProof",jsonObject.getString(Config.LOGIN_IS_PROOF_VERIFY).toString());
                        editor.putString("PANName",jsonObject.getString(Config.LOGIN_PAN_NAME).toString());
                        editor.putString("pannum",jsonObject.getString(Config.LOGIN_PAN_NUMBER).toString());
                        editor.putString("dob",jsonObject.getString(Config.LOGIN_DOB).toString());
                        editor.putString("PanState",jsonObject.getString(Config.LOGIN_BANKSTATE).toString());
                        editor.putString("Image",jsonObject.getString(Config.LOGIN_IMAGE).toString());
                        editor.putString("bankname",jsonObject.getString(Config.LOGIN_NAME_ON_ACCOUNT).toString());
                        editor.putString("bankaccount",jsonObject.getString(Config.LOGIN_BANK_ACCOUNT).toString());
                        editor.putString("ifsc",jsonObject.getString(Config.LOGIN_BIFSC).toString());
                        editor.putString("balance",jsonObject.getString(Config.LOGIN_BALANCE).toString());
                        editor.commit();

                        Intent intent = new Intent(Login.this, Navigationdrawer.class);
                        startActivity(intent);

                        finish();
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
//                        Toast.makeText(Login.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("UserID", UserId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    public void getUI()
    {
        email= (EditText) findViewById(R.id.login_emailid);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login);
        gmail = (Button) findViewById(R.id.gmail);
        register = (TextView) findViewById(R.id.register);
        forgotpwd = (TextView) findViewById(R.id.forgotpwd);

        // item on Click Listener

        register.setOnClickListener(this);
        forgotpwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.register:
                startActivity(new Intent(Login.this, Register.class));
                break;

                case R.id.forgotpwd:
                startActivity(new Intent(Login.this, Forgot_password.class));
                break;

                case R.id.login:
                    // Check proper validate the feilds
                    CheckForValidation();
                break;
        }
    }

    public void CheckForValidation()
    {
        EmailAddress = email.getText().toString().trim();
        Password = password.getText().toString().trim();

        CheckInternetConnectio _checkInternetConnection = new CheckInternetConnectio(Login.this);
        if (_checkInternetConnection.checkInterntConnection()) {

            if (Validation.isEmailAddress(email, true)) {

                if (!password.getText().toString().equalsIgnoreCase("")) {
                    u_password = password.getText().toString().trim();

                    login(u_password);

                } else {
//                                mobile.setError("Enter valid mobile number");
//                    email.setText("");
                    Toast.makeText(Login.this,"Enter password",Toast.LENGTH_SHORT).show();
                }
            } else {
//                            password.setError("Enter password");
                Toast.makeText(Login.this, "Enter valid Email id",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Login.this, "Check Internet Connection",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

