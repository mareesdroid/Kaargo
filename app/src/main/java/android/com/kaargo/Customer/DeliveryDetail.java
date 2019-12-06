package android.com.kaargo.Customer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.com.kaargo.AppController;
import android.com.kaargo.CustomerHome;
import android.com.kaargo.R;
import android.com.kaargo.Singleton;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeliveryDetail extends Fragment {
ImageView prev;
Button post;
EditText name,country,state,city,address,contact;
Button startDate,endDate;
boolean isBut = false;
String First,Second;
JSONObject js = new JSONObject();
JSONObject productData = new JSONObject();
    Calendar dateSelected = Calendar.getInstance();
    DatePickerDialog datePickerDialog;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_CONTACT = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.deliverydetail,container,false);

        prev = view.findViewById(R.id.imv2);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        name= view.findViewById(R.id.editText37);
        country= view.findViewById(R.id.editText41);
        state= view.findViewById(R.id.editText42);
        city= view.findViewById(R.id.editText43);
        address= view.findViewById(R.id.editText44);
        contact= view.findViewById(R.id.editText45);
        startDate= view.findViewById(R.id.editText46);
        endDate= view.findViewById(R.id.editText47);
        post = view.findViewById(R.id.button5);



        name.addTextChangedListener(new EditTextListener());
        country.addTextChangedListener(new EditTextListener());
        state.addTextChangedListener(new EditTextListener());
        city.addTextChangedListener(new EditTextListener());
        address.addTextChangedListener(new EditTextListener());
        contact.addTextChangedListener(new EditTextListener());

      startDate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             // String sdate = setDatePicker();
            //  startDate.setText(sdate);
             createDatePicker(startDate,"First");
          }
      });
      endDate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
//              String ddate = setDatePicker();
//              endDate.setText(ddate);
             createDatePicker(endDate,"Second");
             }
      });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               boolean test =  checkdateData();
               if(test) {


                   Toast.makeText(getContext(), "true", Toast.LENGTH_LONG).show();
               }
                boolean reg = checkRegexes();


                if(isBut&&test&&reg){

                    int productID = (int )(Math.random() * 900000 + 1);
                    int senderID =(int)(Math.random() * 9000000 + 1);
                    try {
                        js.put("DeliverName",name.getText().toString());
                        js.put("DeliveryCountry",country.getText().toString());
                        js.put("DeliveryState",state.getText().toString());
                        js.put("DeliverCity",city.getText().toString());
                        js.put("DeliveryFull",address.getText().toString());
                        js.put("DeliverContact",contact.getText().toString());
                        js.put("DeliverStart",startDate.getText().toString());
                        js.put("DeliverEnd",endDate.getText().toString());
                        js.put("ProductID",String.valueOf(productID));
                        js.put("SenderID",String.valueOf(senderID));
                        productData = merge(AppController.getProducts(),AppController.getPickups(),js);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    addProduct(productData);
                }
                else{
                    Toast.makeText(getContext(),"Please Check all the fields",Toast.LENGTH_LONG).show();
                }

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            loadFragment(new PickupDetail());
            }
        });


        return view;
    }

    private boolean checkRegexes() {

            Matcher matcher = VALID_CONTACT .matcher(contact.getText().toString());
            return matcher.find();



    }

    private boolean checkdateData() {
        boolean dateChecker = false;
        Date date1=null;
        Date date2 = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(First);
            date2 =new SimpleDateFormat("dd/MM/yyyy").parse(Second);
            if(date1.after(date) || date1.equals(date)){
                dateChecker = true;
                Toast.makeText(getContext(),"crct first date",Toast.LENGTH_LONG).show();
                if(date1.after(date2)){
                    dateChecker = false;
                    System.out.println("Date1 is after Date2");
                    //////wrong date
                }

                if(date1.before(date2)){
                    dateChecker = true;
                    System.out.println("Date1 is before Date2");
                    ////crct date
                }

                if(date1.equals(date2)){
                    dateChecker = true;
                    System.out.println("Date1 is equal Date2");
                }

            }else{
                dateChecker = false;
                Toast.makeText(getContext(),"wrong first date",Toast.LENGTH_LONG).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

return dateChecker;

    }


    private void createDatePicker( final Button tv_arrival,final String txt )
    {





        final View dialogView = View.inflate( getActivity( ), R.layout.date_picker, null );
        final AlertDialog alertDialog = new AlertDialog.Builder( getActivity( ) ).create( );

//        TextView tv = ( TextView ) dialogView.findViewById( R.id.date_time_title );
//        tv.setText("Start"); // set title here
        dialogView.findViewById( R.id.date_time_set ).setOnClickListener( new View.OnClickListener( )
        {
            @Override
            public void onClick( View view )
            {
               setDatePicker( dialogView, tv_arrival,txt );
                alertDialog.dismiss( );

            }
        } );
        dialogView.findViewById( R.id.date_time_not_set ).setOnClickListener( new View.OnClickListener( )
        {
            @Override
            public void onClick( View v )
            {
                alertDialog.dismiss( );

            }
        } );
        alertDialog.setView( dialogView );
        alertDialog.show( );

    }


    private void setDatePicker ( View dialogView, TextView tv_arrival,String txt )
    {
        DatePicker datePicker = ( DatePicker ) dialogView.findViewById( R.id.date );
        Calendar calendar;
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy", Locale.ENGLISH );
        calendar = new GregorianCalendar( datePicker.getYear( ), datePicker.getMonth( ), datePicker.getDayOfMonth( ) );
        tv_arrival.setText( sdf.format( calendar.getTime( ) ) );
        if(txt.equals("First")){
           First = sdf.format( calendar.getTime( ) );
        }else{
            Second = sdf.format( calendar.getTime( ) );
        }

    }

//    private String setDatePicker ()
//    {
//        final View dialogView = View.inflate( getActivity( ), R.layout.date_picker, null );
//        final AlertDialog alertDialog = new AlertDialog.Builder( getActivity( ) ).create( );
//        DatePicker datePicker = ( DatePicker ) dialogView.findViewById( R.id.date );
//        Calendar calendar;
//        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy", Locale.ENGLISH );
//        calendar = new GregorianCalendar( datePicker.getYear( ), datePicker.getMonth( ), datePicker.getDayOfMonth( ) );
//        return sdf.format( calendar.getTime( ) );
//    }


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

    private void addProduct(JSONObject js) {


        RequestQueue mqueue;
        mqueue = Singleton.getInstance(getContext()).getRequestQueue();
        String url = "http://vaagana.com/Laravel/Marees/public/api/addProduct";
        JsonObjectRequest myQuest = new JsonObjectRequest(Request.Method.POST, url, js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(),"Your Request Added Successfully",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });

        myQuest.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mqueue.add(myQuest);

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


    void validateFields() {


        if (name.getText().length() > 0 && country.getText().length() > 0 && state.getText().length() > 0 && city.getText().length() > 0 && address.getText().length() > 0 && contact.getText().length() > 0 && startDate.getText().length() > 0 && endDate.getText().length() > 0 ) {

            post.setBackgroundColor(Color.parseColor("#8AB4F8"));

            isBut = true;

        } else {

            post.setBackgroundColor(Color.parseColor("#BDBDBE"));
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
