package android.com.kaargo;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONArray;

public class Background extends AsyncTask<Bitmap, Void, String> {

    public String base64String;
    JSONArray js = new JSONArray();
    @Override
    protected String doInBackground(Bitmap... bitmaps) {

        for(int i=0;i<4;i++){

        }


      return "Success";
    }

    }

