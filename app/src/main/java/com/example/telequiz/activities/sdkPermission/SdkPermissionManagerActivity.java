package com.example.telequiz.activities.sdkPermission;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.telequiz.R;

public class SdkPermissionManagerActivity extends AppCompatActivity {

    static final Integer LOCATION = 0x1;
    static final Integer CALL = 0x2;
    static final Integer WRITE_EXST = 0x3;
    static final Integer READ_EXST = 0x4;
    static final Integer CAMERA = 0x5;
    static final Integer ACCOUNTS = 0x6;
    static final Integer GPS_SETTINGS = 0x7;
    static final Integer READ_CONTACTS = 0x8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdk_permission_manager);
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(SdkPermissionManagerActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SdkPermissionManagerActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(SdkPermissionManagerActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(SdkPermissionManagerActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void ask(View v){
        switch (v.getId()){
            case R.id.location:
                askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION);
                break;
            case R.id.call:
                askForPermission(Manifest.permission.CALL_PHONE,CALL);
                break;
            case R.id.write:
                askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXST);
                break;
            case R.id.read:
                askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,READ_EXST);
                break;
            case R.id.camera:
                askForPermission(Manifest.permission.CAMERA,CAMERA);
                break;
            case R.id.accounts:
                askForPermission(Manifest.permission.GET_ACCOUNTS,ACCOUNTS);
                break;
            case R.id.contacts:
                askForPermission(Manifest.permission.READ_CONTACTS,READ_CONTACTS);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
            switch (requestCode) {
                //Location
                case 1:
//                    askForGPS();
                    Log.i("DK GPS", "Ask for GPS");
                    break;
                //Call
                case 2:
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + "{This is a telephone number}"));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        startActivity(callIntent);
                    }
                    break;
                //Write external Storage
                case 3:
                    break;
                //Read External Storage
                case 4:
                    Intent imageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imageIntent, 11);
                    break;
                //Camera
                case 5:
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, 12);
                    }
                    break;
                //Accounts
                case 6:
                    AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
                    Account[] list = manager.getAccounts();
                    Toast.makeText(this,""+list[0].name,Toast.LENGTH_SHORT).show();
                    for(int i=0; i<list.length;i++){
                        Log.e("Account "+i,""+list[i].name);
                    }
                    break;
            }

            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
