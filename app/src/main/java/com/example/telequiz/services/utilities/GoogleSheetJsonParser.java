package com.example.telequiz.services.utilities;

import org.json.JSONObject;

public class GoogleSheetJsonParser {
    private String scriptBaseUrl;
    private String googleSheetId;
    private String googleSheetName;

    public GoogleSheetJsonParser() {
        this.scriptBaseUrl = "https://script.google.com/macros/s/AKfycbzGvKKUIaqsMuCj7-A2YRhR-f7GZjl4kSxSN1YyLkS01_CfiyE/exec?";
    }

    public JSONObject getJsonData() {
        JSONObject googleSheetData = new JSONObject();
        return googleSheetData;
    }

    public String getScriptUrl(String googleSheetId, String googleSheetName) {
        String scriptURL;
        this.googleSheetId = googleSheetId;
        this.googleSheetName = googleSheetName;
        scriptURL = scriptBaseUrl + "id=" + googleSheetId + "&sheet=" + googleSheetName;
        return scriptURL;
    }
}
