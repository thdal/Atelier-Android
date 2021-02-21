package fr.epsi.atelier_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ProductsListActivity extends AtelierAndroidActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        ImageView imageViewBack = findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent myIntent = getIntent(); // gets the previously created intent
        String cheminJson = myIntent.getStringExtra("cheminJson");
        System.out.println(cheminJson);


        //Je dois ajouter ces lignes afin de pouvoir faire mon appel asynchrone au WS dans le onCreate
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String json = jsonGetRequest(cheminJson);
        JSONObject object = null;
        JSONArray Jarray  = null;
        try {
            object = new JSONObject(json);
            Jarray = object.getJSONArray("items");
            for (int i = 0; i < Jarray.length(); i++)
            {
                JSONObject Jasonobject = Jarray.getJSONObject(i);
                String name = (String) Jasonobject.get("name");
                String cheminPicture = (String) Jasonobject.get("picture_url");
                System.out.println("boucleJson|");
                System.out.println(name);
                System.out.println(cheminPicture);

                // Création dynamique des boutons dans la vue
                Button newButton = new Button(this);
                // On commence pas créer l'événement qui appélera la vue avec les bons paramètres
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProductsListActivity.this,ProductsListActivity.class);
                        intent.putExtra("cheminJson", cheminJson);
                        startActivity(intent);
                    }
                });
                //Ensuite on gère l'affichage de celui-ci, la partie front
                LinearLayout l = (LinearLayout) findViewById(R.id.linearLayoutCategories);
                //newButton.setGravity(Gravity.CENTER);
                newButton.setText(name);
                //newButton.setBackgroundColor(0xFF99D6D6);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );
                newButton.setMaxWidth(200);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                //params.setMargins(0, 0, 0, 50);
                newButton.setLayoutParams(params);
                newButton.requestLayout();
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
