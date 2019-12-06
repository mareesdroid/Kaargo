package android.com.kaargo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static android.com.kaargo.AppController.getMode;

public class StartActivity extends AppCompatActivity {

Button send,carry;
ImageView forward;
boolean isChoose = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        send = findViewById(R.id.button);
        carry = findViewById(R.id.button2);
        forward = findViewById(R.id.imageView);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppController.setMode("send");
                send.setBackgroundColor(Color.parseColor("#8AB4F8"));
                carry.setBackgroundColor(Color.parseColor("#3C4043"));
                forward.setColorFilter(Color.parseColor("#8AB4F8"));
                isChoose = true;

            }
        });

        carry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.setMode("carry");
                send.setBackgroundColor(Color.parseColor("#3C4043"));
                carry.setBackgroundColor(Color.parseColor("#8AB4F8"));
                forward.setColorFilter(Color.parseColor("#8AB4F8"));
                isChoose = true;
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mode =  AppController.getMode();
                if(isChoose){
                    Intent i =new Intent(StartActivity.this,Sign.class);
                        startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Options Selected",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
