package android.com.kaargo.Sender;

import android.com.kaargo.AppController;
import android.com.kaargo.R;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class TravelInfo extends Fragment {

    View view;
    ImageView go;
    String previuosTag;
    EditText country,state,city,address,travelDate;
    boolean isBut = false;
    JSONObject js = new JSONObject();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.travelinfo,container,false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        go = view.findViewById(R.id.go2);

        country = view.findViewById(R.id.editText27);
        state = view.findViewById(R.id.editText26);
        city = view.findViewById(R.id.editText25);
        address = view.findViewById(R.id.editText24);
        travelDate = view.findViewById(R.id.travelDate);

        showWarnings(true,false,false);
        country.addTextChangedListener(new EditTextListener());
        state.addTextChangedListener(new EditTextListener());
        city.addTextChangedListener(new EditTextListener());
        address.addTextChangedListener(new EditTextListener());
        travelDate.addTextChangedListener(new EditTextListener());
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBut){

                    try {
                        js.put("TravelCountry",country.getText().toString());
                        js.put("TravelState",state.getText().toString());
                        js.put("TravelCity",city.getText().toString());
                        js.put("TravelAddress",address.getText().toString());
                        js.put("TravelDate",travelDate.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppController.setTravel(js);
                    loadFragment(new OtherInfo());
                }
                else{
                    Toast.makeText(getContext(),"Please fill all the fields",Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }
    public void loadFragment(Fragment targetFragment) {




        FragmentManager frag = getActivity().getSupportFragmentManager();


            frag.beginTransaction().replace(R.id.frags, targetFragment).commit();



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(getContext(),SenderHome.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void showWarnings(boolean cur,boolean trav,boolean oth){
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
    void validateFields() {



        if(country.getText().length()>0 && city.getText().length()>0 && state.getText().length()>0 && address.getText().length()>0 && travelDate.getText().length()>0){

            showWarnings(true,true,false);
            go.setColorFilter(Color.parseColor("#8AB4F8"));

            isBut = true;

        }else{

            showWarnings(true,false,false);
            go.setColorFilter(Color.parseColor("#BDBDBE"));
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
