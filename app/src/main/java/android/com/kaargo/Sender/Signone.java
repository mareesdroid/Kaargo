package android.com.kaargo.Sender;

import android.com.kaargo.AppController;
import android.com.kaargo.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONException;
import org.json.JSONObject;

public class Signone extends Fragment {

View view;
ImageView forward;
TextView name,email,password,contact;
JSONObject js = new JSONObject();

boolean isBut = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.signfirst,container,false);
        forward = view.findViewById(R.id.forward);
        name = view.findViewById(R.id.editText2);
        email = view.findViewById(R.id.editText);
        password = view.findViewById(R.id.editText3);
        contact = view.findViewById(R.id.editText4);

       name.addTextChangedListener(new EditTextListener());
       email.addTextChangedListener(new EditTextListener());
       password.addTextChangedListener(new EditTextListener());
       contact.addTextChangedListener(new EditTextListener());


        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBut){
                    try {
                        js.put("FullName",name.getText().toString());
                        js.put("Email",email.getText().toString());
                        js.put("Password",password.getText().toString());
                        js.put("Contact",contact.getText().toString());




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    AppController.setFirst(js);
                    loadFragment(new Signtwo());
                }
                else{

                    Toast.makeText(getContext(),"Please Fill all the fields",Toast.LENGTH_LONG).show();
                }

            }
        });
        return view;
    }
    public void loadFragment(Fragment currentFrag){

        FragmentManager myFrag =getActivity().getSupportFragmentManager();
        FragmentTransaction myTransaction = myFrag.beginTransaction();
        myTransaction.replace(R.id.myfrag,currentFrag);
        myTransaction.commit();

    }

     void validateFields() {

        if(name.getText().length()>0 && contact.getText().length()>0 && email.getText().length()>0 && password.getText().length()>0){

            forward.setColorFilter(Color.parseColor("#8AB4F8"));

            isBut = true;

        }else{

            forward.setColorFilter(Color.parseColor("#BDBDBE"));
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
