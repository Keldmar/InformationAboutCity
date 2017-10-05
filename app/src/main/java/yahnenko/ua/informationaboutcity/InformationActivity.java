package yahnenko.ua.informationaboutcity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import yahnenko.ua.informationaboutcity.response.GetCity;

public class InformationActivity extends AppCompatActivity {
    private TextView textMainInfo;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        imageView = (ImageView) findViewById(R.id.image);
        textMainInfo = (TextView) findViewById(R.id.text_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent i = getIntent();
        final GetCity getCity = (GetCity) i.getSerializableExtra("key");

        try {
            Uri uri = Uri.parse(getCity.geonames.get(0).thumbnailImg);
            Glide
                    .with(InformationActivity.this)
                    .load(uri)
                    .into(imageView);
        } catch (Exception e) {
            Glide
                    .with(InformationActivity.this)
                    .load("https://upload.wikimedia.org/wikipedia/commons/1/13/Q5.png?uselang=ru")
                    .into(imageView);
        }

        getSupportActionBar().setTitle(getCity.geonames.get(0).title + ", " + getCity.geonames.get(0).countryCode);
        textMainInfo.setText(getCity.geonames.get(0).summary);

        Button openBrowser = (Button) findViewById(R.id.open_browser);
        openBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://" + getCity.geonames.get(0).wikipediaUrl;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }

}
