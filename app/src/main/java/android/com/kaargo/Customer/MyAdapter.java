package android.com.kaargo.Customer;

import android.com.kaargo.MainActivity;
import android.com.kaargo.R;
import android.com.kaargo.StartActivity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {


    Context context;
    JSONArray js;
    boolean isOn = false;
    public MyAdapter(JSONArray response, Context applicationContext) {

        this.js = response;
        this.context = applicationContext;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.travelerlayout,viewGroup,false);
        MyAdapter.MyHolder myList = new MyAdapter.MyHolder(view);
        return myList;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {


        try {
//            "S.NO": "8",
//                    "TravelCountry": "India",
//                    "TravelState": "Tamilnadu",
//                    "TravelCity": "trichy",
//                    "TravelAddress": "Myaddress",
//                    "CurrentCountry": "India",
//                    "CurrentState": "Tamilnadu",
//                    "CurrentCity": "Ooty",
//                    "CurrentAddress": "Myaddress",
//                    "TravelDate": "1/4/19",
//                    "DeliverableDate": "4/5/19",
//                    "SenderID": "56565",
//                    "AvailableSpace": "5",
//                    "ExtraComments": "i'm going to my Hometown anyone wants to send near madurai respond me",
//                    "Uploaded": "April 19, 2019, 12:07 pm",
//                    "TravelID": "55454422555545"
            myHolder.cCity.setText(js.getJSONObject(i).getString("CurrentCity")+",");
            myHolder.cState.setText(js.getJSONObject(i).getString("CurrentState")+",");
            myHolder.cCountry.setText(js.getJSONObject(i).getString("CurrentCountry")+".");
            myHolder.cDate.setText(js.getJSONObject(i).getString("TravelDate"));
            myHolder.tCity.setText(js.getJSONObject(i).getString("TravelCity")+",");
            myHolder.tState.setText(js.getJSONObject(i).getString("TravelState")+",");
            myHolder.tCountry.setText(js.getJSONObject(i).getString("TravelCountry")+".");
            myHolder.tDate.setText(js.getJSONObject(i).getString("DeliverableDate"));
            myHolder.uDate.setText(js.getJSONObject(i).getString("Uploaded"));
            myHolder.tripComments.setText(js.getJSONObject(i).getString("ExtraComments"));
            myHolder.space.setText(js.getJSONObject(i).getString("AvailableSpace")+" kg");


myHolder.goTravel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent in = new Intent(context,FullTrip.class);

        try {
            in.putExtra("taddress", js.getJSONObject(i).getString("TravelAddress"));
            in.putExtra("caddress", js.getJSONObject(i).getString("CurrentAddress"));
            in.putExtra("tdate", js.getJSONObject(i).getString("TravelDate"));
            in.putExtra("ddate", js.getJSONObject(i).getString("DeliverableDate"));
            in.putExtra("aspace", js.getJSONObject(i).getString("AvailableSpace"));
            in.putExtra("ecomment", js.getJSONObject(i).getString("ExtraComments"));
            in.putExtra("upload", js.getJSONObject(i).getString("Uploaded"));
            in.putExtra("tid", js.getJSONObject(i).getString("TravelID"));
            in.putExtra("fname", js.getJSONObject(i).getString("FullName"));
            in.putExtra("profile", js.getJSONObject(i).getString("Profile"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        context.startActivity(in);
    }
});




            myHolder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isOn){
                        isOn = false;
                        myHolder.extraCard.setVisibility(View.GONE);
                    }else{
                        isOn = true;
                        myHolder.extraCard.setVisibility(View.VISIBLE);
                    }

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    @Override
    public int getItemCount() {
        return js.length();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView cCity,cState,cCountry,cDate,tCity,tState,tCountry,tDate,uDate,tripComments,space;
        CardView extraCard,main;
        ImageView goTravel;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cCity=itemView.findViewById(R.id.textView47);
            cState=itemView.findViewById(R.id.textView46);
            cCountry=itemView.findViewById(R.id.textView54);
            cDate=itemView.findViewById(R.id.textView60);
            tCity=itemView.findViewById(R.id.textView57);
            tState=itemView.findViewById(R.id.textView56);
            tCountry=itemView.findViewById(R.id.textView58);
            tDate=itemView.findViewById(R.id.textView59);
            uDate = itemView.findViewById(R.id.uDate);
            extraCard = itemView.findViewById(R.id.extraCard);
            main = itemView.findViewById(R.id.cardView2);
            goTravel = itemView.findViewById(R.id.goTip);
            tripComments = itemView.findViewById(R.id.textView105);
            space= itemView.findViewById(R.id.textView112);

            }
    }

}


