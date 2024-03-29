package android.com.kaargo;

import android.com.kaargo.Customer.AddProducts;
import android.com.kaargo.Customer.RandomTrips;
import android.com.kaargo.Customer.productDetail;
import android.com.kaargo.Sender.SenderHome;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CustomerHome extends AppCompatActivity{
String previuosTag;
TextView newItem,logout;
TextView cusName,cusEmail;
    SharedPreferences myshare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        myshare=getSharedPreferences("first", Context.MODE_PRIVATE);


        NavigationView navigationView = (NavigationView) findViewById(R.id.cust_view);

        cusName =findViewById(R.id.textView45);
        cusEmail = findViewById(R.id.mailtxt);
        logout = findViewById(R.id.textView68);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerHome.this,StartActivity.class);
                myshare.edit().clear().apply();
                startActivity(i);
            }
        });
        newItem=navigationView.findViewById(R.id.textView61);
        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.START, true);
                Intent i = new Intent(CustomerHome.this,AddProducts.class);
                startActivity(i);
                }
        });
        loadFragment(new RandomTrips());
        setShareddata();
    }

    private void setShareddata() {
        SharedPreferences myshare;
        myshare = getSharedPreferences("first",Context.MODE_PRIVATE);

        if(myshare.contains("cName"))
        {
            cusName.setText(myshare.getString("cName",""));
        }
        if(myshare.contains("cEmail"))
        {
            cusEmail.setText(myshare.getString("cEmail",""));
        }


    }
    public void loadFragment(Fragment targetFragment) {

        previuosTag = AppController.getPreviousTag();


        FragmentManager frag = getSupportFragmentManager();



            //if the fragment does not exist, add it to fragment manager.
            frag.beginTransaction().replace(R.id.furg, targetFragment).commit();



    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
