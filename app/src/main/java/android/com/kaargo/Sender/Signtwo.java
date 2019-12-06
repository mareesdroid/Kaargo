package android.com.kaargo.Sender;

import android.app.ProgressDialog;
import android.com.kaargo.AppController;
import android.com.kaargo.R;
import android.com.kaargo.SenderScreen;
import android.com.kaargo.Sign;
import android.com.kaargo.Singleton;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Signtwo extends Fragment {

View view;
ImageView back,next;
EditText Country,State,City,FullAddress;
boolean isBut = false;
JSONObject js = new JSONObject();
JSONObject SenderDetails = new JSONObject();
    ProgressDialog pro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signtwo,container,false);
        back = view.findViewById(R.id.back);
        next = view.findViewById(R.id.next);
        Country  = view.findViewById(R.id.editText10);
        State = view.findViewById(R.id.editText11);
        City = view.findViewById(R.id.editText12);
        FullAddress = view.findViewById(R.id.editText13);
        pro = new ProgressDialog(getContext());
        pro.setMessage("Loading...");
        Country.addTextChangedListener(new EditTextListener());
        State.addTextChangedListener(new EditTextListener());
        City.addTextChangedListener(new EditTextListener());
        FullAddress.addTextChangedListener(new EditTextListener());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            loadFragment(new Signone());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBut){


                    pro.show();
                    try {
                        js.put("Country",Country.getText().toString());
                        js.put("State",State.getText().toString());
                        js.put("City",City.getText().toString());
                        js.put("FullAddress",FullAddress.getText().toString());

                        int random = (int )(Math.random() * 900000 + 1);
                        AppController.setSenderID(String.valueOf(random));
                        js.put("SenderID",String.valueOf(random));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                       SenderDetails = merge(js,AppController.getFirst());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sendToServer(SenderDetails);


                }
                else{
                    Toast.makeText(getContext(),"Please Fill all the fields",Toast.LENGTH_LONG).show();
                }

            }
        });


        return view;
    }

    private void sendToServer(JSONObject senderDetails) {


        RequestQueue mqueue;

        mqueue = Singleton.getInstance(getContext()).getRequestQueue();


        String url="http://vaagana.com/Laravel/Marees/public/api/newSender";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, senderDetails, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                pro.dismiss();
                Toast.makeText(getContext(),"Success",Toast.LENGTH_LONG).show();
                Log.e("response",response.toString());
               // loadFragment(new Signthree(),"three");
                Intent i =new Intent(AppController.getAppContext(),Sign.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pro.dismiss();
                Log.e("response",error.toString());
                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mqueue.add(request);

    }

    public void loadFragment(Fragment currentFrag){

        FragmentManager myFrag =getActivity().getSupportFragmentManager();
        FragmentTransaction myTransaction = myFrag.beginTransaction();
        myTransaction.replace(R.id.myfrag,currentFrag);
        myTransaction.commit();


    }
    void validateFields() {



        if(Country.getText().length()>0 && State.getText().length()>0 && City.getText().length()>0 && FullAddress.getText().length()>0){

            next.setColorFilter(Color.parseColor("#8AB4F8"));

            isBut = true;

        }else{

            back.setColorFilter(Color.parseColor("#BDBDBE"));
            isBut = false;
        }

}
    private static JSONObject merge(JSONObject... jsonObjects) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        for(JSONObject temp : jsonObjects){
            Iterator<String> keys = temp.keys();
            while(keys.hasNext()){
                String key = keys.next();
                jsonObject.put(key, temp.get(key));
            }

        }
        return jsonObject;
    }


    class EditTextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            validateFields();
        }
    }

}
