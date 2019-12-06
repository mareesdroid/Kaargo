package android.com.kaargo.Sender;

import android.com.kaargo.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FullProduct extends AppCompatActivity {

    TextView pname,pweight,pdesc,pid,cname,ccontact,caddress,dname,dcontact,daddress,poffer,fstart,fend,fupload;
    Button accept;
    ImageView close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullproduct);
        Intent in = getIntent();
        String id  =in.getStringExtra("id");
        String name = in.getStringExtra("pname");
        String contact = in.getStringExtra("pcontact");
        String fullAddress= in.getStringExtra("pfull");
        String ddname = in.getStringExtra("dname");
        String ddcontact = in.getStringExtra("dcontact");
        String dfull  = in.getStringExtra("dfull");
        String iname  = in.getStringExtra("iname");
        String iweight = in.getStringExtra("iweight");
        String ioffer = in.getStringExtra("ioffer");
        String idesc  = in.getStringExtra("idesc");
        String up = in.getStringExtra("up");
        String dstart   = in.getStringExtra("dstart");
        String dend = in.getStringExtra("dend");



        pname= findViewById(R.id.textView114);
        pweight= findViewById(R.id.textView116);
        pdesc= findViewById(R.id.textView118);
        pid= findViewById(R.id.textView119);
        cname= findViewById(R.id.textView121);
        ccontact= findViewById(R.id.textView122);
        caddress= findViewById(R.id.textView123);
        dname= findViewById(R.id.textView125);
        dcontact= findViewById(R.id.textView126);
        daddress= findViewById(R.id.textView127);
        poffer= findViewById(R.id.textView128);
        fstart= findViewById(R.id.textView133);
        fend= findViewById(R.id.textView134);
        fupload= findViewById(R.id.textView137);
        close = findViewById(R.id.imageView13);



        pid.setText("ID : #"+id);
        cname.setText("Name : "+name);
        ccontact.setText("Contact : "+contact);
        caddress.setText("Address : "+fullAddress);
        dname.setText("Name : "+ddname);
        dcontact.setText("Contact : "+ddcontact);
        daddress.setText("Address : "+dfull);
        pname.setText("Item : "+iname);
        pweight.setText("Weight : "+iweight+"Kg");
        poffer.setText("Deliver Offer : "+ioffer+"₹");
        pdesc.setText(idesc);
        fupload.setText(up);
        fend.setText(dend);
        fstart.setText(dstart);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



}
