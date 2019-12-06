package android.com.kaargo.Customer;

import android.com.kaargo.AppController;
import android.com.kaargo.CustomerHome;
import android.com.kaargo.R;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class PickupDetail extends Fragment {

    View view;
    ImageView next;
    ImageView prev;
    String previuosTag;
    boolean isBut = false;
    EditText name,country,state,city,address,contact;
    JSONObject js = new JSONObject();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.pickupdetatil,container,false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        next = view.findViewById(R.id.imageView9);
        prev = view.findViewById(R.id.imv);
        name = view.findViewById(R.id.editText31);
        country = view.findViewById(R.id.editText33);
        state = view.findViewById(R.id.editText34);
        city = view.findViewById(R.id.editText35);
        address = view.findViewById(R.id.editText36);
        contact = view.findViewById(R.id.editText40);

        name.addTextChangedListener(new EditTextListener());
        country.addTextChangedListener(new EditTextListener());
        state.addTextChangedListener(new EditTextListener());
        city.addTextChangedListener(new EditTextListener());
        address.addTextChangedListener(new EditTextListener());
        contact.addTextChangedListener(new EditTextListener());


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isBut) {


                    try {
                        js.put("PickupName",name.getText().toString());
                        js.put("PickupCountry",country.getText().toString());
                        js.put("PickupState",state.getText().toString());
                        js.put("PickupCity",city.getText().toString());
                        js.put("PickupFull",address.getText().toString());
                        js.put("PickupContact",contact.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    AppController.setPickups(js);
                    loadFragment(new DeliveryDetail());
                }
                else{
                    Toast.makeText(getContext(),"Please fill all the fields",Toast.LENGTH_LONG).show();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            loadFragment(new productDetail());
            }
        });
            return view;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(getContext(),CustomerHome.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadFragment(Fragment targetFragment) {




        FragmentManager frag = getActivity().getSupportFragmentManager();


            frag.beginTransaction().replace(R.id.frags, targetFragment).commit();




    }

    void validateFields() {


        if (name.getText().length() > 0 && country.getText().length() > 0 && state.getText().length() > 0 && city.getText().length() > 0 && address.getText().length() > 0 && contact.getText().length() > 0 ) {

            next.setColorFilter(Color.parseColor("#8AB4F8"));

            isBut = true;

        } else {

            next.setColorFilter(Color.parseColor("#BDBDBE"));
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
