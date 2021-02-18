package fr.epsi.atelier_android;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AtelierAndroidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Activity", "######onCreate######"+getClass().getSimpleName());

    }

    @Override
    public void finish() {
        super.finish();
        Log.d("Activity", "#######onFinish#####"+getClass().getSimpleName());
    }

}
