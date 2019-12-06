package android.com.kaargo;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;


    private static AppController mInstance;
    private static JSONObject firstJs = new JSONObject();
    private static JSONObject secondJs = new JSONObject();
    private static String mode="";
    private static String SenderID="";
    private static JSONObject current = new JSONObject();
    private static JSONObject travel = new JSONObject();
    private static JSONObject products = new JSONObject();
    private static JSONObject pickups = new JSONObject();
    private static JSONObject deliveries = new JSONObject();


    private static Context mContext;

    private static String previousTag;

    public void onCreate() {
        super.onCreate();
        mInstance = this;

        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public static void setFirst(JSONObject js){

        firstJs = js;
    }

    public static JSONObject getFirst(){

        return firstJs;
    }

    public static void setSecond(JSONObject js){

        secondJs = js;

    }

    public static JSONObject getSecond(){

        return secondJs;
    }

    public static void setMode(String tag){

        mode = tag;


    }


    public static String getMode(){

        return mode;
    }
    public static void setSenderID(String ID){

        SenderID = ID;

    }
    public static String getSenderID(){

        return SenderID;

    }

    public static void setPreviousTag(String tag){

        previousTag = tag;
    }

    public static String getPreviousTag(){

        return previousTag;
    }
    public static void setCurrent(JSONObject js){

        current = js;
    }
    public static JSONObject getCurrent(){
        return current;
    }
    public static void setTravel(JSONObject js){
        travel =js;
    }
    public static JSONObject getTravel(){
        return travel;
    }

    public static void setProducts(JSONObject js){products = js;}
    public static JSONObject getProducts(){return products;};

    public static void setPickups(JSONObject js){pickups = js;}
    public static JSONObject getPickups(){return pickups;}

    public static void setDeliveries(JSONObject js){deliveries = js;}
    public static JSONObject getDeliveries(){return deliveries;}
}