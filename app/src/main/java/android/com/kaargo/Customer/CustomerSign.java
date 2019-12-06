package android.com.kaargo.Customer;

import android.app.ProgressDialog;
import android.com.kaargo.AppController;
import android.com.kaargo.R;
import android.com.kaargo.Sign;
import android.com.kaargo.Singleton;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
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


public class CustomerSign extends Fragment {

    View view;
    EditText Name,Email,Password,Contact,Country,State,City,FullAddress;
    Button signup;
    ProgressDialog pro;
    JSONObject js;
    boolean isBut = false;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.csign,container,false);
        Name = view.findViewById(R.id.editText7);
        pro = new ProgressDialog(getContext());
        pro.setMessage("Loading...");
        Email = view.findViewById(R.id.editText8);
        Password = view.findViewById(R.id.editText9);
        Contact = view.findViewById(R.id.editText15);
        Country = view.findViewById(R.id.editText16);
        State = view.findViewById(R.id.editText17);
        City = view.findViewById(R.id.editText18);
        FullAddress = view.findViewById(R.id.editText19);
        signup= view.findViewById(R.id.button4);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                js = new JSONObject();

                try {
                    js.put("FullName",Name.getText().toString());
                    js.put("Contact",Contact.getText().toString());
                    js.put("Country",Country.getText().toString());
                    js.put("State",State.getText().toString());
                    js.put("City",City.getText().toString());
                    js.put("FullAddress",FullAddress.getText().toString());
                    js.put("Password",Password.getText().toString());
                    js.put("Email",Email.getText().toString());
                    int random = (int )(Math.random() * 900000 + 1);
                    js.put("CustomerID",String.valueOf(random));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                sendToServer(js);
            }
        });
        return view;

    }

    private void sendToServer(JSONObject senderDetails) {


        RequestQueue mqueue;

        mqueue = Singleton.getInstance(getContext()).getRequestQueue();


        String url="http://vaagana.com/Laravel/Marees/public/api/newCustomer";
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
        request.setShouldCache(false);

        mqueue.add(request);

    }


    void validateFields() {


        if(Name.getText().length()>0 && Email.getText().length()>0 && Password.getText().length()>0 && Contact.getText().length()>0 && Country.getText().length()>0 && State.getText().length()>0 && City.getText().length()>0 && FullAddress.getText().length()>0){

            signup.setBackgroundColor(Color.parseColor("#8AB4F8"));

            isBut = true;

        }else{
            signup.setBackgroundColor(Color.parseColor("#BDBDBE"));

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


}
