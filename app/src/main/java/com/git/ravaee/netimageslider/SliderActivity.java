package com.git.ravaee.netimageslider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.git.ravaee.netimageslider.Components.EasySwipe;
import java.util.ArrayList;


public class SliderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        ArrayList<String> strImage = new ArrayList<>();

        for (int i = 3; i>0; i--) {

            String tmp = "https://v1.mycookit.ir/Content/Images/sliders/"+i+".jpg";
            strImage.add(tmp);
        }

        EasySwipe easySwipe = findViewById(R.id.viewPage);
        easySwipe.initial(strImage,4000);

    }

}
