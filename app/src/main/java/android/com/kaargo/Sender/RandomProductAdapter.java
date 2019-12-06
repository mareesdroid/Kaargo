package android.com.kaargo.Sender;

import android.com.kaargo.Customer.MyAdapter;
import android.com.kaargo.R;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class RandomProductAdapter extends RecyclerView.Adapter<RandomProductAdapter.MyHolder> {

    JSONArray myjs = new JSONArray();
    Context Urcont;
    boolean isOn = false;


    public RandomProductAdapter(JSONArray response, Context context) {

        myjs = response;
        Urcont = context;


    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.productinfo,viewGroup,false);
        RandomProductAdapter.MyHolder myHold = new RandomProductAdapter.MyHolder(view);

        return myHold;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {


        try {

            myHolder.name.setText("Item : "+myjs.getJSONObject(i).getString("ProductName"));
            myHolder.pickupCity.setText(myjs.getJSONObject(i).getString("PickupCity"));
            myHolder.pickupState.setText(myjs.getJSONObject(i).getString("PickupState"));
            myHolder.pickupCountry.setText(myjs.getJSONObject(i).getString("PickupCountry"));
            myHolder.deliveryCity.setText(myjs.getJSONObject(i).getString("DeliverCity"));
            myHolder.deliveryCountry.setText(myjs.getJSONObject(i).getString("DeliveryCountry"));
            myHolder.deliveryState.setText(myjs.getJSONObject(i).getString("DeliveryState"));
            myHolder.postedDate.setText(myjs.getJSONObject(i).getString("uploadedAt"));
            myHolder.minDate.setText(myjs.getJSONObject(i).getString("DeliverStart"));
            myHolder.maxDate.setText(myjs.getJSONObject(i).getString("DeliverEnd"));
            myHolder.weight.setText(myjs.getJSONObject(i).getString("ProductWeight")+" kg");
            myHolder.offer.setText(myjs.getJSONObject(i).getString("ProductOffer")+" ₹");
            myHolder.Comment.setText(myjs.getJSONObject(i).getString("ProductDescription"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        myHolder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOn){
                    isOn = false;
                    myHolder.extraCard.setVisibility(View.GONE);
                }
                else{
                    isOn = true;
                    myHolder.extraCard.setVisibility(View.VISIBLE);
                }
            }
        });


        myHolder.go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Intent in = new Intent(Urcont,FullProduct.class);
                    in.putExtra("id", myjs.getJSONObject(i).getString("ProductID"));
                    in.putExtra("pname", myjs.getJSONObject(i).getString("PickupName"));
                    in.putExtra("pcontact", myjs.getJSONObject(i).getString("PickupContact"));
                    in.putExtra("pfull",myjs.getJSONObject(i).getString("PickupFull"));
                    in.putExtra("dname",myjs.getJSONObject(i).getString("DeliverName"));
                    in.putExtra("dcontact",myjs.getJSONObject(i).getString("DeliverContact"));
                    in.putExtra("dfull",myjs.getJSONObject(i).getString("DeliveryFull"));
                    in.putExtra("iname",myjs.getJSONObject(i).getString("ProductName"));
                    in.putExtra("iweight",myjs.getJSONObject(i).getString("ProductWeight"));
                    in.putExtra("ioffer",myjs.getJSONObject(i).getString("ProductOffer"));
                    in.putExtra("idesc",myjs.getJSONObject(i).getString("ProductDescription"));
                    in.putExtra("up",myjs.getJSONObject(i).getString("uploadedAt"));
                    in.putExtra("dstart",myjs.getJSONObject(i).getString("DeliverStart"));
                    in.putExtra("dend",myjs.getJSONObject(i).getString("DeliverEnd"));
                    Urcont.startActivity(in);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });




    }

    @Override
    public int getItemCount() {
        return myjs.length();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView name,pickupCity,pickupState,pickupCountry,deliveryCity,deliveryState,deliveryCountry,postedDate,minDate,maxDate,weight,offer,Comment;
        ImageView go;
        CardView main,extraCard;
        ///full detail


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textView87);
            pickupCity=itemView.findViewById(R.id.textView93);
            pickupState=itemView.findViewById(R.id.textView89);
            pickupCountry=itemView.findViewById(R.id.textView94);
            deliveryCity=itemView.findViewById(R.id.textView96);
            deliveryState=itemView.findViewById(R.id.textView95);
            deliveryCountry=itemView.findViewById(R.id.textView97);
            postedDate=itemView.findViewById(R.id.textView102);
            minDate=itemView.findViewById(R.id.textView109);
            maxDate=itemView.findViewById(R.id.textView110);
            weight=itemView.findViewById(R.id.textView111);
            offer=itemView.findViewById(R.id.textView98);
            go =itemView.findViewById(R.id.imageView12);
            main =itemView.findViewById(R.id.cardView);
            extraCard =itemView.findViewById(R.id.extraCard);
            Comment =itemView.findViewById(R.id.textView104);
            }
    }
}
