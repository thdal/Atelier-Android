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
        Button btnZone1 = findViewById(R.id.buttonZone1);
        btnZone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,GroupInfosActivity.class);
                startActivity(intent);
            }
        });
        Button btnZone2 = findViewById(R.id.buttonZone2);
        btnZone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });
    }
}
