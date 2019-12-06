package android.com.kaargo.Sender;

import android.com.kaargo.AppController;
import android.com.kaargo.R;

import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;


public class AddTrips extends AppCompatActivity {
String previuosTag;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtrip);

       loadFragment(new CurrentInfo());
    }

    public void loadFragment(Fragment targetFragment) {

        previuosTag = AppController.getPreviousTag();


        FragmentManager frag = getSupportFragmentManager();

            frag.beginTransaction().replace(R.id.frags, targetFragment).commit();



    }



}
