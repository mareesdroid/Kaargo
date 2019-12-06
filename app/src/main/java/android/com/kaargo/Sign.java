package android.com.kaargo;

import android.app.Activity;
import android.com.kaargo.Customer.CustomerSign;
import android.com.kaargo.Sender.SenderHome;
import android.com.kaargo.Sender.Signone;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class Sign extends AppCompatActivity {





        ConstraintLayout fragHolder;
        ConstraintLayout signHolder;
        ConstraintLayout signUp,signIn;
        Button slogin;
        TextView inText,upText;
        EditText mail,pass;
        String mode;
    SharedPreferences myshare;



    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.signup);
            signUp = findViewById(R.id.constraintLayout);
            signIn = findViewById(R.id.constraintLayout2);
            fragHolder = findViewById(R.id.mylayout);
            signHolder = findViewById(R.id.signin);
            slogin = findViewById(R.id.slogin);
            inText = findViewById(R.id.inText);
            upText = findViewById(R.id.upText);
            mail = findViewById(R.id.editText5);
            pass = findViewById(R.id.editText6);
        myshare=getSharedPreferences("first", Context.MODE_PRIVATE);
        getSupportActionBar().setTitle("Sign in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportActionBar().setTitle("Sign up");

                    fragHolder.setVisibility(View.VISIBLE);
                    signHolder.setVisibility(View.GONE);

                    signUp.setBackgroundColor(getResources().getColor(R.color.googleBlue));
                    upText.setTextColor(Color.WHITE);
                    signIn.setBackgroundColor(getResources().getColor(R.color.googleDisable));
                    inText.setTextColor(Color.BLACK);
                    if(AppController.getMode().equals("send")){

                        loadFragment(new CustomerSign());
                    }
                    if(AppController.getMode().equals("carry")){


                        loadFragment(new Signone());

                    }

                }
            });

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    getSupportActionBar().setTitle("Sign in");

                    fragHolder.setVisibility(View.GONE);
                    signHolder.setVisibility(View.VISIBLE);
                    signIn.setBackgroundColor(getResources().getColor(R.color.googleBlue));
                    inText.setTextColor(Color.WHITE);
                    signUp.setBackgroundColor(getResources().getColor(R.color.googleDisable));
                    upText.setTextColor(Color.BLACK);

                }
            });

            slogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = mail.getText().toString();
                    String myPass = pass.getText().toString();
                    String url = "";

                    if(AppController.getMode().equals("carry")) {
                        url="http://vaagana.com/Laravel/Marees/public/api/loginSender";
                        mode = "carry";
                       loginMars(email,myPass,url);

                    }
                    if(AppController.getMode().equals("send")){
                        mode = "send";
                        url = "http://vaagana.com/Laravel/Marees/public/api/loginCustomer";

                        loginMars(email,myPass,url);

                    }





                }
            });
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void loginMars(final String email, final String myPass, String url) {
        RequestQueue myQueue = Singleton.getInstance(Sign.this).getRequestQueue();
        JSONObject js =new JSONObject();
        try {
            js.put("Email",email);
            js.put("Password",myPass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest myQuest = new JsonObjectRequest(Request.Method.POST, url, js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                SharedPreferences.Editor editor=myshare.edit();
                try {


                  if(mode.equals("send")){

                      editor.putString("Mode","send");
                      editor.putString("cName",response.getJSONObject("Success").getString("FullName"));
                      editor.putString("cEmail",response.getJSONObject("Success").getString("Email"));
                      editor.putString("cContact",response.getJSONObject("Success").getString("Contact"));
                      editor.putString("cAddress",response.getJSONObject("Success").getString("FullAddress"));
                      editor.putString("cID",response.getJSONObject("Success").getString("CustomerID"));
                      editor.apply();
                      Intent i = new Intent(Sign.this, CustomerHome.class);
                      startActivity(i);
                  }
                  else{
                      editor.putString("Mode","carry");
                      editor.putString("FullName",response.getJSONObject("Success").getString("FullName"));
                      editor.putString("Email",response.getJSONObject("Success").getString("Email"));
                      editor.putString("Contact",response.getJSONObject("Success").getString("Contact"));
                      editor.putString("FullAddress",response.getJSONObject("Success").getString("FullAddress"));
                      editor.putString("SenderID",response.getJSONObject("Success").getString("SenderID"));

                      editor.apply();
                      Intent i = new Intent(Sign.this, SenderHome.class);
                      startActivity(i);

                  }

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


    public void loadFragment(Fragment currentFrag){

            FragmentManager myFrag =getSupportFragmentManager();
            FragmentTransaction myTransaction = myFrag.beginTransaction();
            myTransaction.replace(R.id.myfrag,currentFrag);
            myTransaction.commit();


        }

    }


