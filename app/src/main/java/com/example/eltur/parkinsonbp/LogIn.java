package com.example.eltur.parkinsonbp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.eltur.parkinsonbp.Utils.AsyncLoginAuth;

import java.net.MalformedURLException;
import java.net.URL;


public class LogIn extends AppCompatActivity {

    Button EnterButton;
    EditText UserId;
    EditText UserPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EnterButton = (Button) findViewById(R.id.button3);
        UserId = (EditText) findViewById(R.id.UserId);
        UserPassword = (EditText) findViewById(R.id.UserPassword);

      //  UserId.setBackgroundResource(Color.parseColor("#E8F5E9"));
       // UserPassword.setBackgroundResource(R.drawable.edittext);
        EnterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(UserId.getText().toString().isEmpty())
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(LogIn.this).create();
                    alertDialog.setTitle("הודעת מערכת");
                    alertDialog.setMessage("נא למלא שם משתמש ");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    return;
                                }
                            });
                    alertDialog.show();
                }
                else {
                   // connectToDB getUserName = new connectToDB();
                    //try {
                       // String UserDetails = getUserName.getUserDetails(UserId.getText().toString());
                    //} catch (MalformedURLException e) {
                      //  e.printStackTrace();
                   // }
                    connectToDB dd = new connectToDB();
                  // String result =  dd.InitialPublicKey();
                   // String urlToRssFeed="http://10.0.2.2:8080/BEAT-PD/encryption-parameters";
                    URL  url = null;
                    try {
                        url = new URL("http://10.0.2.2:8080/BEAT-PD/encryption-parameters");
                       Object result =  new AsyncLoginAuth().execute(url);
                        int i=0;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }




                    Intent intent = new Intent(getBaseContext(), firstpage.class);
                    intent.putExtra("EXTRA_SESSION_ID", UserId.getText().toString());
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog alertDialog = new AlertDialog.Builder(LogIn.this).create();
        alertDialog.setTitle("הודעת מערכת");
        alertDialog.setMessage("האם ברצונך לצאת?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);

                        // System.exit(0);
                       // return;
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "חזור",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        return;
                    }
                });
        alertDialog.show();
    }

}


