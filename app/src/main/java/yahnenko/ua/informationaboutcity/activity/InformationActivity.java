package yahnenko.ua.informationaboutcity.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import yahnenko.ua.informationaboutcity.R;
import yahnenko.ua.informationaboutcity.pojo.City;

public class InformationActivity extends AppCompatActivity {
    public static final String KEY_CITY = "key_city";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        TextView textMainInfo = (TextView) findViewById(R.id.text_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        final City city = (City) intent.getSerializableExtra(KEY_CITY);

        Uri uri;
        final int FIRST_ELEMENT = 0;
        try {
            uri = Uri.parse(city.geonames.get(FIRST_ELEMENT).thumbnailImg);
        } catch (Exception e) {
            uri = Uri.parse("https://upload.wikimedia.org/wikipedia/commons/1/13/Q5.png?uselang=ru");
        }
        Glide.with(InformationActivity.this)
                .load(uri)
                .into(imageView);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(city.geonames.get(FIRST_ELEMENT).title + ", " + city.geonames.get(FIRST_ELEMENT).countryCode);
        }
        textMainInfo.setText(city.geonames.get(FIRST_ELEMENT).summary);

        Button openBrowserButton = (Button) findViewById(R.id.open_browser);
        openBrowserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://" + city.geonames.get(FIRST_ELEMENT).wikipediaUrl;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
