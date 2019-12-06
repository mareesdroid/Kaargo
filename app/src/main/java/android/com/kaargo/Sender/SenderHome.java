package android.com.kaargo.Sender;

import android.com.kaargo.AppController;
import android.com.kaargo.CustomerHome;
import android.com.kaargo.R;
import android.com.kaargo.SenderScreen;
import android.com.kaargo.Sign;
import android.com.kaargo.Singleton;
import android.com.kaargo.StartActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class SenderHome extends AppCompatActivity {

    String previuosTag;
    Button apply;
    ImageView pro;
    TextView name,logout;
    boolean isProfile = false;
    SharedPreferences myshare;
    ConstraintLayout cons;
    ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.textView16);
        cons = findViewById(R.id.fullView);
        myshare=getSharedPreferences("first", Context.MODE_PRIVATE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        pro = findViewById(R.id.imageView2);


        loadFragment(new RandomProducts());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView add = navigationView.findViewById(R.id.textView19);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SenderHome.this,AddTrips.class);
                startActivity(i);
            }
        });

        logout = findViewById(R.id.textView29);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SenderHome.this,StartActivity.class);
                myshare.edit().clear().apply();
                startActivity(i);
            }
        });
        apply = navigationView.findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SenderHome.this,SenderScreen.class);
                startActivity(i);
            }
        });
        getIsProfile();
        if(isProfile) {
            setShareddata();
        }

        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cons.setVisibility(View.GONE);
            }
        });
        pro.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        cons.setVisibility(View.VISIBLE);
        ImageView img  = findViewById(R.id.fillImage);
        if (myshare.contains("Profile")) {
            Picasso.get()
                    .load(myshare.getString("Profile", ""))
                    .placeholder(R.drawable.batman)
                    .error(R.drawable.cartoon)
                    .into(img, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.e("error",e.toString());
                                }
                            }

                    );
            }
        else{
            Toast.makeText(getApplicationContext(), "Profile Photo not Uploaded yet", Toast.LENGTH_SHORT).show();
        }
    }
});


    }

    private void getIsProfile() {

        if(myshare.contains("isProof")){

            TextView textView = findViewById(R.id.textView28);
            textView.setVisibility(View.GONE);
            apply.setVisibility(View.GONE);
        }
        if (myshare.contains("FullName")) {
            name.setText(myshare.getString("FullName", ""));
        }

        if (myshare.contains("isImage")) {

            String img = myshare.getString("isImage","");
            if(img.equals("yes")){
                isProfile = true;
            }
            else{
                isProfile = false;
            }

        }


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
    private void setShareddata() {
        SharedPreferences myshare;
        myshare = getSharedPreferences("first", Context.MODE_PRIVATE);


        if (myshare.contains("Profile")) {
            Picasso.get()
                    .load(myshare.getString("Profile", ""))
                    .placeholder(R.drawable.batman)
                    .error(R.drawable.cartoon)
                    .resize(250,250)
                    .centerCrop()
                    .into(pro, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.e("error",e.toString());
                                }
                            }

                    );

            SharedPreferences.Editor editor=myshare.edit();
            editor.putString("isProof","yes");

        }
        else{
            isSenderAvailable(myshare.getString("SenderID",""));
        }
    }

    private void isSenderAvailable(String senderID) {


        RequestQueue myQueue = Singleton.getInstance(SenderHome.this).getRequestQueue();
        JSONObject js =new JSONObject();
        try {
            js.put("SenderID",senderID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest myQuest = new JsonObjectRequest(Request.Method.POST, "http://vaagana.com/Laravel/Marees/public/api/pro", js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                SharedPreferences.Editor editor=myshare.edit();
                try {

                    response.getJSONObject("Profile");
                    Picasso.get()
                            .load(myshare.getString("Profile", ""))
                            .placeholder(R.drawable.batman)
                            .error(R.drawable.cartoon)
                            .resize(250,250)
                            .centerCrop()
                            .into(pro, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Log.e("error",e.toString());
                                        }
                                    }

                            );

                    editor.putString("Profile","yes").apply();


                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
            }
        });

        myQuest.setShouldCache(false);

        myQueue.add(myQuest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sender_home, menu);
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





    public void loadFragment(Fragment targetFragment) {




        FragmentManager frag = getSupportFragmentManager();



            frag.beginTransaction().replace(R.id.furg2, targetFragment).commit();







    }


}
