package com.example.telequiz.services.utilities;

import java.util.HashMap;
import java.util.Map;

public class Constant {
    private Map<String, Integer> status_codes = new HashMap<>();
    public static String BASE_URL = "https://revisionclasses.000webhostapp.com/";
    public static String APP_NAME = "Telequiz";

    /*
     * Base url to fetch question from the google sheet
     * */
    public static String GOOGLE_SCRIPT_BASE_URL = "https://script.google.com/macros/s/AKfycbzGvKKUIaqsMuCj7-A2YRhR-f7GZjl4kSxSN1YyLkS01_CfiyE/exec?";
    /*
     *User login and logout related session keys:-
     **/
    public static final String PREF_NAME = "telequiz_pref";
    public static final String IS_LOGGED_IN = "is_logged_in";
    public static final String KEY_NAME = "user_name";
    public static final String KEY_EMAIL = "user_email";

    /*
     * Keys for the use of Session which maintains the flag about the Remember me feature in the login Activity
     * */
    public static final String EMAIL_PREF_NAME = "telequiz_user_email_pref";
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
    public static final String QUESTION = "question";
    public static final String OPTION_A = "option_a";
    public static final String OPTION_B = "option_b";
    public static final String OPTION_C = "option_c";
    public static final String OPTION_D = "option_d";
    public static final String CORRECT_OPTION = "correct_option";
    public static final String USER_OPTION = "user_option";
    public static final int VERY_FIRST_QUIZ_QUESTION = 1;
    public static final int QUIZ_ANIMATION_TYPE_LEFT_TO_RIGHT = 1;
    public static final int QUIZ_ANIMATION_TYPE_RIGHT_TO_LEFT = 2;
    public static final int QUIZ_ANIMATION_TYPE_NONE = 3;
    public static final String TOTAL_VIEWS = "total_views";
    public static final String TOTAL_LIKES = "total_likes";
    public static final String TOTAL_UNLIKES = "total_unlikes";
    public static final String TOTAL_SHARES = "total_shares";
    public static final String TOTAL_COMMENTS = "total_comments";
    public static final String COMMENTS = "comments";

    /*
     * Keys to pass value from one activity to another
     * */
    public static final String UPLOADED_QUESTIONS = "upload_question";

    /*
     * App update checker keys
     * */
    public static final String UPDATE_CHECKER_URL = "http://www.mocky.io/v2/5c01f17f3500005600ad0a9c";
    public static final String UPDATE_MESSSAGE = "There is a newer version of this application available, So click on update now to upgrade the App.";
    public static final String UPDATE_MESSAGE_TITLE = "Update Available";

    /*
    * JSON (Question) reading error message:
    * Error may be: Wrong google worksheet id, wrong sheet name, google sheet permission not granted etc
    * */
    public static final String QUESTIOND_DATA_JSON_READING_ERROR_MESSSAGE_PART_A = "Unable to read the Google excel sheet, Please make sure that:- \n\n" +
            "1. Your mobile is connected to the Internet. \n\n" +
            "2. You have already changed the Google sheet access permission for public. \n\n" +
            "3. You have used the axact column field as given in the Google sheet template file. If you don't have then download it by clicking the below link and just reuse this.";

    public static final String QUESTIOND_DATA_JSON_READING_ERROR_MESSSAGE_PART_B = "4. Also check if you have entered the correct Google workbook id and worksheet name.\n\n";
    public static final String QUESTIOND_DATA_JSON_READING_ERROR_MESSSAGE_TITLE = "Error Found";


    /*
    * Mode of quiz exam
    * */
    public static final String MODE_EXAM = "exam_mode";
    public static final String MODE_PRACTICE = "practice_mode";
    public static final String MODE_REVIEW = "review_mode";
}