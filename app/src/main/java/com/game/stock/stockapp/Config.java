package com.game.stock.stockapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by firemoon on 12/1/18.
 */

public class Config {

    /// SHAREDPREFRENCES VALUES



//    http://theroyalbullindia.com/app_aboutus.html
    public static String PRIVACY_POLICY= "http://theroyalbullindia.com/app_privacypolicy.html" ;
    public static String TERM_TECHNOLOGY= "http://theroyalbullindia.com/app_terms&conditions.html" ;
    public static String HELP_DESk= "http://theroyalbullindia.com/app_help.html" ;
    public static String ABOUT_US= "http://theroyalbullindia.com/app_aboutus.html" ;

//WEBVIEW URL


// BASE URL

    private static String BASEURL= "http://www.bstarsw.in/royalbull71/web2/user.php?act=" ;
    private static String BASE= "http://www.bstarsw.in/royalbull71/web2/" ;
    private static String PAYUINTEGRATION= "payments.php?view=single2" ;
    private static String BASEVIEW= "http://www.bstarsw.in/royalbull71/web2/myleagues.php?view=" ;




    ///   APPLICATION URL

//    public static String registerUrl="http://bstarsw.in/Royalbull/web/Registration.php";
    public static String registerUrl=BASEURL+"add";

    public static String verifymobile= BASE+"verifymob.php?view=all";
    public static String sendotpurl= BASE+"sendotp.php?act=all";
    public static String updateotpurl= BASE+"updateotp.php?act=all";
    public static String getallleague= BASE+"league.php?view=all";
    public static String portfolioleaguehistory= BASE+"portfolioteams.php?view=single1";


    public static String loginUrl= BASE+"log.php";
    public static String EDIT_LEAGUE= BASE+"myteamsdetails.php?view=single3";
    public static String leaguelist= BASE+"myteamsdetails.php?view=single2";

//    getplayertypebymtid
    public static String verifyPAN= BASEURL+"updatep";
    public static String verifyBANK= BASEURL+"updateb";
    public static String updateProfile= BASEURL+"update";
    public static String alldetailUser= "http://www.bstarsw.in/royalbull71/web2/user.php?view=single";
    public static String changePassword= BASEURL+"changep";
    public static String forgetPassword= BASE+"forgotpaswd.php";
//    http://www.bstarsw.in/royalbull71/web2/forgotpaswd.php


    // FILTER API


    public static String winningBreakup= BASE+"leaguerankers.php?view=single";

    //LEAGUE API's


    public static String getbetweenteamrange= BASE+"league.php?view=single1";
    public static String getbetweenamountrange= BASE+"league.php?view=single2";
    public static String getallstocksbytype= BASE+"stocklist.php?view=type";
    public static String addcurrentteams= BASE+"currentteams.php?act=add";
    public static String addmyteams= BASE+"myteams.php?act=add";
    public static String addmyteamsdetails= BASE+"myteamsdetails.php?act=add";

    public static String deletemyteamsdetails= BASE+"myteamsdetails.php?act=delete";
    public static String get_allcount= BASE+"count.php?view=single";


    public static String addmyteamsdetailslistNextbtn= BASE+"myteamsdetails.php?view=single1";
    public static String updatebymyteamsdetailsid= BASE+"myteamsdetails.php?act=update";
    public static String updatePlayerByPlayerID= BASE+"myteamsdetails.php?act=update1";
    public static String editLeague= BASE+"myteamsdetails.php?view=single1";
    public static String editLeague2= BASE+"myteamsdetails.php?view=single3";

    public static final String RANK_FROM = "rankfrom";
    public static final String RANK_TO = "rankto";
    public static final String AMOUNT_FROM = "amountfrom";
    public static final String AMOUNT_TO = "amountto";
    public static final String TYPE = "type";

    //ADD MY TEAMS


    public static final String LEAGUE_ID = "leagueid";
    public static final String LEAGUE_NAME = "leaguename";
    public static final String ENTRY_FEES = "entryfees";
    public static final String USER_ID = "userid";
    public static final String WINNIG_AMOUNT = "winningamount";
    public static final String PLAY_DATE = "playedate";
    public static final String RANK = "rank";
    public static final String SHARE_TYPE = "sharetype";

// ADD MY TEAM DETAILS

    public static final String MTID = "MTID";
    public static final String MTD_MTID = "mtid";
    public static final String MTD_SMID = "smid";
    public static final String MTD_TOKEN = "token";
    public static final String MTD_PLAYERNAME = "playername";
    public static final String MTD_PLAYERTYPE = "playertype";

    // DELETE MY TEAM DETAILS
    public static final String PLID = "PLID";


    //for login
    public static final String emaillogin = "email";
    public static final String passwordlogin = "password";
    //for register
    public static final String userid = "userid";
    public static final String username = "username";
    public static final String email = "email";
    public static final String password = "password";

    public static final String phoneno = "mobile";
    public static final String Otp = "otp";


    //LOGIN PARAMETER

    public static final String LOGIN_FULL_NAME = "Full Name";
    public static final String LOGIN_EMAILID = "EmailId";
    public static final String LOGIN_MOBILE = "MobileNo";
    public static final String LOGIN_CITY = "City";
    public static final String LOGIN_BLOCK = "Block";
    public static final String LOGIN_IS_WITHDRAW = "IsWithdrawal";
    public static final String LOGIN_IS_MOBILE_VERIFY = "IsVerifyMobile";
    public static final String LOGIN_IS_STATE_VERIFY = "IsverifyState";
    public static final String LOGIN_IS_ACCOUNT_VERIFY = "IsverifyAccount";
    public static final String LOGIN_IS_PROOF_VERIFY = "IsVerifyProof";
    public static final String LOGIN_PAN_NAME = "PANName";
    public static final String LOGIN_PAN_NUMBER  = "PANNumber";
    public static final String LOGIN_DOB  = "DOB";
    public static final String LOGIN_BANKSTATE  = "BankState";
    public static final String LOGIN_IMAGE  = "Image";
    public static final String LOGIN_NAME_ON_ACCOUNT  = "NameOnAccount";
    public static final String LOGIN_BANK_ACCOUNT  = "BankAccountNO";
    public static final String LOGIN_BIFSC  = "BIFSC";
    public static final String LOGIN_BALANCE = "Balance";
}
