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

public class productDetail extends Fragment {

    ImageView next;
    String previuosTag;
    boolean isBut = false;
    View view;
    EditText name, weight, offer, comment;

    JSONObject js = new JSONObject();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.productdetail, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        name = view.findViewById(R.id.editText22);
        weight = view.findViewById(R.id.editText29);
        offer = view.findViewById(R.id.editText30);
        comment = view.findViewById(R.id.editText32);


        name.addTextChangedListener(new EditTextListener());
        weight.addTextChangedListener(new EditTextListener());
        offer.addTextChangedListener(new EditTextListener());
        comment.addTextChangedListener(new EditTextListener());

        next = view.findViewById(R.id.imv1);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if(isBut){

    try {
        js.put("ProductName",name.getText().toString());
        js.put("ProductWeight",weight.getText().toString());
        js.put("ProductOffer",offer.getText().toString());
        js.put("ProductDescription",comment.getText().toString());
    } catch (JSONException e) {
        e.printStackTrace();
    }

    AppController.setProducts(js);
    loadFragment(new PickupDetail());
}else{
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
            Intent i = new Intent(getContext(),CustomerHome.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    void validateFields() {


        if (name.getText().length() > 0 && weight.getText().length() > 0 && offer.getText().length() > 0 && comment.getText().length() > 0) {

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

