package com.example.telequiz.services;

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
    * HTTP status code
    * */

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
}