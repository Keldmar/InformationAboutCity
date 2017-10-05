package yahnenko.ua.informationaboutcity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yahnenko.ua.informationaboutcity.InformAboutCityApplication;
import yahnenko.ua.informationaboutcity.R;
import yahnenko.ua.informationaboutcity.pojo.City;

import static yahnenko.ua.informationaboutcity.InformAboutCityApplication.getLocalDataManager;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCountries;
    Spinner spinnerCity;
    String selectedCountries;
    String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button goSearch = (Button) findViewById(R.id.search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.select_country);
        }

        if (InformAboutCityApplication.getLocalDataManager().getCountries() == null) {
            InformAboutCityApplication.getApiManager().getCountries().enqueue(new Callback<Map<String, List<String>>>() {
                @Override
                public void onResponse(Call<Map<String, List<String>>> call, Response<Map<String, List<String>>> response) {
                    getLocalDataManager().setCountries(response.body());
                    addCountriesOnSpinner();
                }

                @Override
                public void onFailure(Call<Map<String, List<String>>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            addCountriesOnSpinner();
        }
        goSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformAboutCityApplication.getApiManager().getCity(selectedCity).enqueue(new Callback<City>() {
                    @Override
                    public void onResponse(Call<City> call, Response<City> response) {
                        City geoInfo = response.body();
                        Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                        intent.putExtra(InformationActivity.KEY_CITY, geoInfo);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<City> call, Throwable t) {
                        Toast.makeText(MainActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void addCountriesOnSpinner() {
        spinnerCountries = (Spinner) findViewById(R.id.sppiner_Countries);
        List<String> list = new ArrayList<String>();
        Iterator it = getLocalDataManager().getCountries().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            list.add(pair.getKey().toString());
            it.remove();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountries.setAdapter(dataAdapter);

        spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountries = spinnerCountries.getSelectedItem().toString();
                addCitiesOnSpinnerByCountry(selectedCountries);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addCitiesOnSpinnerByCountry(String nameCity) {
        spinnerCity = (Spinner) findViewById(R.id.sppiner_City);
        List<String> list = new ArrayList<String>();
        list.addAll(InformAboutCityApplication.getLocalDataManager().getCountries().get(nameCity));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(dataAdapter);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = spinnerCity.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}