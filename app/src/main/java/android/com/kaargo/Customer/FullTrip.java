package android.com.kaargo.Customer;

import android.com.kaargo.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FullTrip extends AppCompatActivity {

    TextView daddress,saddress,ddate,sdate,space,detail,up,id,name;
    ImageView pro,fullImage,close;
    ConstraintLayout fullView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fulltrip);
        Intent in = getIntent();
        String taddress = in.getStringExtra("taddress");
        String caddress = in.getStringExtra("caddress");
        String tdate = in.getStringExtra("tdate");
        String cdate = in.getStringExtra("ddate");
        String aspace = in.getStringExtra("aspace");
        String ecomment = in.getStringExtra("ecomment");
        String upload = in.getStringExtra("upload");
        String tid = in.getStringExtra("tid");
        String fname = in.getStringExtra("fname");
        String profile = in.getStringExtra("profile");

        fullImage = findViewById(R.id.fillImage);
        fullView = findViewById(R.id.fullView);



        pro = findViewById(R.id.imageView20);
        close = findViewById(R.id.close);
        daddress= findViewById(R.id.textView138);
        saddress= findViewById(R.id.textView141);
        ddate= findViewById(R.id.textView139);
        sdate= findViewById(R.id.textView149);
        space= findViewById(R.id.textView145);
        detail= findViewById(R.id.textView144);
        up= findViewById(R.id.textView148);
        id= findViewById(R.id.textView146);
        name= findViewById(R.id.textView131);


        daddress.setText(taddress);
        saddress.setText(caddress);
        ddate.setText(tdate);
        sdate.setText(cdate);
        space.setText(aspace);
        detail.setText(ecomment);
        up.setText(upload);
        id.setText(tid);
        name.setText(fname);
        Log.e("",profile);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullView.setVisibility(View.GONE);
            }
        });

        Picasso.get()
                .load(profile)
                .placeholder(R.drawable.batman)
                .error(R.drawable.cartoon)
                .resize(50, 50)
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
        Picasso.get()
                .load(profile)
                .placeholder(R.drawable.batman)
                .error(R.drawable.cartoon)
                .into(fullImage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e("error",e.toString());
                            }
                        }

                );



        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullView.setVisibility(View.VISIBLE);
            }
        });
    }





}
