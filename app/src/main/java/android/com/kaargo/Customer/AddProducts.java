package android.com.kaargo.Customer;


import android.com.kaargo.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class AddProducts extends AppCompatActivity {

    String previuosTag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additem);

        loadFragment(new productDetail());

    }


    public void loadFragment(Fragment targetFragment) {




        FragmentManager frag = getSupportFragmentManager();



            //if the fragment does not exist, add it to fragment manager.
            frag.beginTransaction().replace(R.id.frags, targetFragment).commit();




    }


}
