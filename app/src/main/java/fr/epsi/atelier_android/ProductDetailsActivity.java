package fr.epsi.atelier_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AtelierAndroidActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        // bouton retour arrière
        ImageView imageViewBack = findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // On récupére les variables passées dans les paramètres
        Intent myIntent = getIntent();
        String name = myIntent.getStringExtra("name");
        String description = myIntent.getStringExtra("description");
        String cheminPicture = myIntent.getStringExtra("cheminPicture");
        // On initialise la vue
        TextView textViewTitle = (TextView)findViewById(R.id.textViewTitle);
        textViewTitle.setText(name);
        TextView textViewDescriptionContent = (TextView)findViewById(R.id.textViewDescriptionContent);
        textViewDescriptionContent.setText(description);
        // On affiche l'image avec picasso
        ImageView imageViewProduct = (ImageView)findViewById(R.id.imageViewProduct);
        Picasso.get().load(cheminPicture).into(imageViewProduct);
    }
}
