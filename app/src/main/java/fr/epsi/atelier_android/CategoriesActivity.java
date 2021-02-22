package fr.epsi.atelier_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class CategoriesActivity extends AtelierAndroidActivity{
    static public void displayActivity(AtelierAndroidActivity activity){
        Intent intent = new Intent(activity,GroupInfosActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        // bouton retour arrière
        ImageView imageViewBack = findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //Je dois ajouter ces lignes afin de pouvoir faire mon appel asynchrone au WS dans le onCreate
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        System.out.println("backmisàjour|");
        String json = jsonGetRequest("https://djemam.com/epsi/categories.json");
        JSONObject object = null;
        JSONArray Jarray  = null;
        try {
            object = new JSONObject(json);
            Jarray = object.getJSONArray("items");
            for (int i = 0; i < Jarray.length(); i++)
            {
                JSONObject Jasonobject = Jarray.getJSONObject(i);
                String title = (String) Jasonobject.get("title");
                String cheminJson = (String) Jasonobject.get("products_url");
                // Création dynamique des boutons dans la vue
                Button newButton = new Button(this);
                // On commence pas créer l'événement qui appélera la vue avec les bons paramètres
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CategoriesActivity.this,ProductsListActivity.class);
                        intent.putExtra("cheminJson", cheminJson);
                        intent.putExtra("title", title);
                        startActivity(intent);
                    }
                });
                //Ensuite on gère l'affichage de celui-ci, la partie front
                LinearLayout l = (LinearLayout) findViewById(R.id.linearLayoutCategories);
                newButton.setText(title);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );
                newButton.setMaxWidth(200);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                params.setMargins(0,0,0,10);
                newButton.setLayoutParams(params);
                newButton.requestLayout();
                newButton.setBackgroundColor(getColor(R.color.cat_btn));
                l.addView(newButton);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }

    public static String jsonGetRequest(String urlQueryString) {
        String json = null;
        try {
            URL url = new URL(urlQueryString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inStream = connection.getInputStream();
            json = streamToString(inStream); // input stream to string
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
