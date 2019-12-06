package android.com.kaargo.Sender;

import android.com.kaargo.AppController;
import android.com.kaargo.R;
import android.com.kaargo.Singleton;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Random;

public class OtherInfo extends Fragment {


View view;
EditText space,comment,date;
Button sign;
boolean isBut = false;
JSONObject myJs = new JSONObject();
JSONObject js = new JSONObject();
    SharedPreferences myshare;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.otherinfo,container,false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myshare=getContext().getSharedPreferences("first", Context.MODE_PRIVATE);

        space = view.findViewById(R.id.editText20);
       comment = view.findViewById(R.id.editText21);
        date = view.findViewById(R.id.editText23);
        sign = view.findViewById(R.id.trip);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBut){
                    Random rand = new Random();

// Obtain a number between [0 - 49].

                    int travel = rand.nextInt(50000);
                    try {
                        js.put("AvailableSpace",space.getText().toString());
                        js.put("ExtraComments",comment.getText().toString());
                        js.put("DeliverableDate",date.getText().toString());
                        js.put("SenderID", myshare.getString("SenderID",""));
                        js.put("TravelID",String.valueOf(travel));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        myJs = merge(AppController.getCurrent(),AppController.getTravel(),js);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    uploadtoServer(myJs);


                }else{
                    Toast.makeText(getContext(),"Fill all the fields",Toast.LENGTH_LONG).show();
                }

            }
        });
        space.addTextChangedListener(new EditTextListener());
        comment.addTextChangedListener(new EditTextListener());
        date.addTextChangedListener(new EditTextListener());
        showWarnings2(true,true,false);
        return view;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(getContext(),SenderHome.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    public void showWarnings2(boolean cur,boolean trav,boolean oth){
        ConstraintLayout current,travel,other;
        current = getActivity().findViewById(R.id.constraintLayout8);
        travel = getActivity().findViewById(R.id.constraintLayout9);
        other = getActivity().findViewById(R.id.constraintLayout13);
        if(cur){
            current.setBackgroundColor(Color.parseColor("#8AB4F8"));
        }
        else{

            current.setBackgroundColor(Color.parseColor("#BDBDBE"));
        }
        if(trav){
            travel.setBackgroundColor(Color.parseColor("#8AB4F8"));
        }
        else{

            travel.setBackgroundColor(Color.parseColor("#BDBDBE"));
        }
        if(oth){
            other.setBackgroundColor(Color.parseColor("#8AB4F8"));
        }
        else{

            other.setBackgroundColor(Color.parseColor("#BDBDBE"));
        }
    }
    public void showWarnings(boolean cur,boolean trav,boolean oth){
        ConstraintLayout current,travel,other;
        current = getActivity().findViewById(R.id.constraintLayout8);
        travel = getActivity().findViewById(R.id.constraintLayout9);
        other = getActivity().findViewById(R.id.constraintLayout13);
        if(cur){
            current.setBackgroundColor(Color.parseColor("#E9967A"));
        }
        if(trav){
            travel.setBackgroundColor(Color.parseColor("#E9967A"));
        }
        if(oth){
            other.setBackgroundColor(Color.parseColor("#E9967A"));
        }
        }

        private void uploadtoServer(JSONObject js){


        String url="http://vaagana.com/Laravel/Marees/public/api/addTrip";
            RequestQueue mqueue;
            mqueue =Singleton.getInstance(getContext()).getRequestQueue();
            JsonObjectRequest myQuest = new JsonObjectRequest(Request.Method.POST, url, js, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getContext(),"Trip Added",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showWarnings(true,true,true);
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                }
            });
            myQuest.setShouldCache(false);

            myQuest.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mqueue.add(myQuest);
        }
    void validateFields() {

        if(space.getText().length()>0 && comment.getText().length()>0 && date.getText().length()>0){
            showWarnings2(true,true,true);
            sign.setBackgroundColor(Color.parseColor("#8AB4F8"));

            isBut = true;

        }else{
            showWarnings2(true,true,false);
            sign.setBackgroundColor(Color.parseColor("#BDBDBE"));
            isBut = false;
        }
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

}
