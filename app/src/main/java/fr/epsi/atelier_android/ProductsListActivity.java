package fr.epsi.atelier_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
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
        String title = myIntent.getStringExtra("title");
        TextView textViewTitle = (TextView)findViewById(R.id.textViewTitle);
        textViewTitle.setText(title);
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
                String description = (String) Jasonobject.get("description");
                String cheminPicture = (String) Jasonobject.get("picture_url");
                // On prépare l'affichage de l'image à l'aide de la librairie Picasso et lui assigne une largeur et une hauteur
                ImageView imageView = new ImageView(this);
                Picasso.get().load(cheminPicture).into(imageView);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, 120);
                imageView.setLayoutParams(layoutParams);
                // Création dynamique des éléments dans la vue
                TextView textViewName = new TextView(this);
                TextView textViewDescription = new TextView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);
                textViewName.setLayoutParams(params);
                textViewDescription.setLayoutParams(params);
                textViewName.setText(name);
                textViewDescription.setText(description);
                textViewName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                textViewDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textViewDescription.setMaxLines(2);
                // On initialise les différents layout de la vue
                LinearLayout l1 = (LinearLayout) findViewById(R.id.linearLayoutProducts);
                LinearLayout l2 = new LinearLayout(this);
                LinearLayout l3 = new LinearLayout(this);
                l2.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT));
                l3.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
                l2.setOrientation(LinearLayout.HORIZONTAL);
                l3.setOrientation(LinearLayout.VERTICAL);
                // On fait les imbrications
                l1.addView(l2);
                l2.addView(imageView);
                l2.addView(l3);
                l3.addView(textViewName);
                l3.addView(textViewDescription);
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
