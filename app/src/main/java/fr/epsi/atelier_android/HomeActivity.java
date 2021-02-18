package fr.epsi.atelier_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AtelierAndroidActivity{
    static public void displayActivity(AtelierAndroidActivity activity){
        Intent intent = new Intent(activity,HomeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button btnNature = findViewById(R.id.buttonZone1);
        btnNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(HomeActivity.this,NatureActivity.class);
               // startActivity(intent);
            }
        });
        Button btnSpace = findViewById(R.id.buttonZone2);
        btnSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(HomeActivity.this, SpaceActivity.class);
               // startActivity(intent);
            }
        });
    }
}
