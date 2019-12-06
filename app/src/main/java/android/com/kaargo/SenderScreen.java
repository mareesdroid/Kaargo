package android.com.kaargo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.com.kaargo.Sender.SenderHome;
import android.com.kaargo.Sender.Signtwo;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SenderScreen extends AppCompatActivity implements View.OnClickListener {

    Button signUp;
    EditText aadhar;
    String profilePath,backPath,frontPath,sidePath,numberPath;

    JSONObject js = new JSONObject();
    Bitmap profileBit,backBit,frontBit,sideBit,numberBit;
    public static final int Profile = 1;
    public static final int LicenseFront = 2;
    public static final int LicenseBack = 3;
    public static final int Side = 4;
    public static final int Number = 5;

    String[] permissions = new String[]{

            Manifest.permission.READ_EXTERNAL_STORAGE,

    };
    SharedPreferences myshare;



    ImageView profile,licenseFront,licenseBack,bikeSide,bikeNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signthree);
        checkPermissions();

        myshare=getSharedPreferences("first", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=myshare.edit();
        profilePath=backPath=frontPath=sidePath=numberPath="";
        profile =findViewById(R.id.imageView5);
        licenseFront =findViewById(R.id.imageView7);
        licenseBack = findViewById(R.id.imageView8);
        bikeNumber = findViewById(R.id.imageView3);
        bikeSide = findViewById(R.id.imageView4);
        aadhar = findViewById(R.id.editText14);
        profile.setOnClickListener(this);
        licenseBack.setOnClickListener(this);
        licenseFront.setOnClickListener(this);
        bikeNumber.setOnClickListener(this);
        bikeSide.setOnClickListener(this);





        signUp = findViewById(R.id.button7);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profilePath != null && backPath != null &&  frontPath!= null && sidePath != null && numberPath != null){

                    //   uploadToserver(js);
                    // profilePath=backPath=frontPath=sidePath=numberPath="";
                    uploadImage(profilePath,backPath,frontPath,sidePath,numberPath);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please select all fields",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imageView5 :
                ///profile
                pickImage(Profile);
                break;

            case R.id.imageView7 :
                ////license back
                pickImage(LicenseFront);


                break;
            case R.id.imageView8 :
                ////license front
                pickImage(LicenseBack);
                break;
            case R.id.imageView4 :
                /////bike side
                pickImage(Side);
                break;
            case R.id.imageView3 :
                ////bike number
                pickImage(Number);
                break;

        }
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {


        if (requestCode==Profile) {
            //TODO: action


            Uri u =getPicUri(data);

            profile.setImageURI(u);
            try {
               profilePath = getFilePath(SenderScreen.this,u);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
        if (requestCode==LicenseBack) {
            //TODO: action

            Uri u =getPicUri(data);
            try {
                backPath = getFilePath(SenderScreen.this,u);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            licenseBack.setImageURI(u);

        }
        if (requestCode==LicenseFront) {
            //TODO: action

            Uri u =getPicUri(data);
            try {
                frontPath =  getFilePath(SenderScreen.this,u);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


            licenseFront.setImageURI(u);

        }
        if (requestCode==Side) {
            //TODO: action

            Uri u =getPicUri(data);
            try {
                sidePath = getFilePath(SenderScreen.this,u);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            bikeSide.setImageURI(u);

        }
        if (requestCode==Number) {
            //TODO: action

            Uri u =getPicUri(data);


            try {
                numberPath =  getFilePath(SenderScreen.this,u);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            bikeNumber.setImageURI(u);

        }

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getPaths(SenderScreen senderScreen, Uri u) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(u);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[0];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = senderScreen.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    private Uri getPicUri(Intent data) {
        Uri picUri = data.getData();
        return picUri;
    }


    private String getPath(Uri contentUri) {
        String[] proj = { MediaStore.Audio.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    private void pickImage(int id) {

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, id);
    }

    private void uploadImage(String profilePath, String backPath, String frontPath, String sidePath, String numberPath) {
        final SharedPreferences.Editor editor=myshare.edit();

        RequestQueue mqueue;
        mqueue = Singleton.getInstance(getApplicationContext()).getRequestQueue();
        String url="http://vaagana.com/Laravel/Marees/public/api/upload";
        SimpleMultiPartRequest myReq = new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String pro ="";
                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                try {
                    JSONObject js = new JSONObject(response);
                    pro = js.getString("Profile");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                editor.putString("isImage","yes");
                editor.putString("Profile",pro);
                editor.putString("isProof","yes");
                editor.apply();
              //  pro.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("",error.toString());
                editor.putString("isImage","no");
                editor.putString("isProof","no");

                editor.apply();
               // pro.dismiss();
                Toast.makeText(getApplicationContext(),"Error"+error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        myReq.addFile("Number",numberPath);
        myReq.addFile("Side",sidePath);
        myReq.addFile("Front",frontPath);
        myReq.addFile("Back",backPath);
        myReq.addFile("Profile",profilePath);
        myReq.addStringParam("Aadhar",aadhar.getText().toString());
        myReq.addStringParam("SenderID",getShare());
        mqueue.add(myReq);
    }

    private String getShare() {
        String id="";
        if (myshare.contains("SenderID")) {
            id = myshare.getString("SenderID","");
           if(id.equals(null)) {
               id = "";
           }
        }

        return id;
    }

    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {


            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


}
