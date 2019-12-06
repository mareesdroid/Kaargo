package android.com.kaargo.Customer;

import android.app.DownloadManager;
import android.com.kaargo.R;
import android.com.kaargo.Singleton;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;


import org.json.JSONArray;
import org.json.JSONObject;

public class RandomTrips extends Fragment {

RecyclerView myCycle;
View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getServerData();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.randomtrip,container,false);
        myCycle = view.findViewById(R.id.myCycle);
        LinearLayoutManager myLine = new LinearLayoutManager(getActivity().getApplicationContext());
        myCycle.setLayoutManager(myLine);




        return view;
    }


    private void getServerData() {

        String url = "http://vaagana.com/Laravel/Marees/public/api/allTrip";
        JSONObject js =new JSONObject();
        RequestQueue mqueue;
        mqueue = Singleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
       JsonArrayRequest myQuest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {
            MyAdapter adap= new MyAdapter(response,getContext());
            myCycle.setAdapter(adap);
            }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
           }
       });
        myQuest.setShouldCache(false);

        myQuest.setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       mqueue.add(myQuest);
    }
}
