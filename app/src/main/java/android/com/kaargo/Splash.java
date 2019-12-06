package android.com.kaargo;

import android.app.Activity;
import android.com.kaargo.Sender.SenderHome;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;



public class Splash extends Activity {

    SharedPreferences myshare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.splash);

        myshare=getSharedPreferences("first", Context.MODE_PRIVATE);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        checkLogin();

                    }
                },
                6000
        );

    }

    private void checkLogin() {

        if (myshare.contains("cID")) {

            String id = myshare.getString("cID","");
            if(!id.equals(null) && !id.equals("")){
                Intent i = new Intent(Splash.this,CustomerHome.class);
                startActivity(i);
            }
            else{
                Intent i = new Intent(Splash.this,StartActivity.class);
                startActivity(i);
            }

        }
        else if (myshare.contains("SenderID")) {

            String id = myshare.getString("SenderID","");
            if(!id.equals(null) && !id.equals("")){
                Intent i = new Intent(Splash.this,SenderHome.class);
                startActivity(i);
            }
            else{
                Intent i = new Intent(Splash.this,StartActivity.class);
                startActivity(i);
            }

        }
        else{
            Intent i = new Intent(Splash.this,StartActivity.class);
            startActivity(i);
        }



    }
}
