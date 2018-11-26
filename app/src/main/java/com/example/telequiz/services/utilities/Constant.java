package com.example.telequiz.services.utilities;

import java.util.HashMap;
import java.util.Map;

public class Constant {
    private Map<String, Integer> status_codes = new HashMap<>();
    public static String BASE_URL = "http://localhost:9090/telequiz/";
    public static String APP_NAME = "Telequiz";


    /*
    * Base url to fetch question from the google sheet
    * */
    public static String GOOGLE_SCRIPT_BASE_URL = "https://script.google.com/macros/s/AKfycbzGvKKUIaqsMuCj7-A2YRhR-f7GZjl4kSxSN1YyLkS01_CfiyE/exec?";


    /*
    * User detail:-
    **/
    public static final String PREF_NAME = "telequiz_pref";
    public static final String EMAIL_PREF_NAME = "telequiz_user_email_pref";

    // All Shared Preferences Keys
    public static final String IS_LOGGED_IN = "is_logged_in";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "user_name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "user_email";
    public static final String IS_REMEMBER_ME_CHECKED = "is_remember_me_checked";

    /*
     *HTTP status code
     **/

    // 1xx: Information

    // 2xx: Successful
    public static int HTTP_SUCCESS = 200;

    // 3xx: Redirection
    public static int HTTP_MOVED_PERMANENTLY = 301;
    public static int HTTP_FOUND = 302;

    // 4xx: Client Error
    public static int HTTP_BAD_REQUEST = 400;
    public static int HTTP_UNAUTHORIZED = 401;
    public static int HTTP_PAYMENT_REQUIRED = 402;
    public static int HTTP_FORBIDDEN = 403;
    public static int HTTP_NOT_FOUND = 404;

    // 5xx: Server Error

    /*
    * Database related
    * */
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "QuestionData.db";
    public static final String QUESTION_TABLE_NAME = "quesion";
    public static final String QUESTION_TABLE_COLUMN_ID = "_id";
    public static final String SL_NO = "s_no";
    public static final String QUESTION_ENGLISH = "question_english";
    public static final String QUESTION_HINDI = "question_hindi";
    public static final String OPTION_A_ENGLISH = "option_a_english";
    public static final String OPTION_A_HINDI = "option_a_hindi";
    public static final String OPTION_B_ENGLISH = "option_b_english";
    public static final String OPTION_B_HINDI = "option_b_hindi";
    public static final String OPTION_C_ENGLISH = "option_c_english";
    public static final String OPTION_C_HINDI = "option_c_hindi";
    public static final String OPTION_D_ENGLISH = "option_d_english";
    public static final String OPTION_D_HINDI = "option_d_hindi";
    public static final String CORRECT_OPTION = "correct_option";

    /*
    * Keys to pass value from one activity to another
    * */
    public static final String UPLOADED_QUESTIONS = "upload_question";
}