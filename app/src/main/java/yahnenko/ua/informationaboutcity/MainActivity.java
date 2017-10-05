package yahnenko.ua.informationaboutcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import yahnenko.ua.informationaboutcity.response.GetCity;

import static yahnenko.ua.informationaboutcity.InformAboutCityApplication.getLocalDataManager;

public class MainActivity extends AppCompatActivity {

    private Button goSearch;
    SharedPreferences sp;
    Spinner spinnerCountries;
    Spinner spinnerCity;
    String selectedCountries;
    String selecedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goSearch = (Button) findViewById(R.id.search);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("select country and city");

        if (InformAboutCityApplication.getLocalDataManager().getCountries() == null) {

            InformAboutCityApplication.getApiManager().getCountries().enqueue(new Callback<Map<String, List<String>>>() {
                @Override
                public void onResponse(Call<Map<String, List<String>>> call, Response<Map<String, List<String>>> response) {

                    getLocalDataManager().setCountries(response.body());
                    addItemsOnSpinnerOne();
                }

                @Override
                public void onFailure(Call<Map<String, List<String>>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "No response from the server, check the Internet connection", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            addItemsOnSpinnerOne();
        }
        goSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InformAboutCityApplication.getApiManager().getCity(selecedCity).enqueue(new Callback<GetCity>() {
                    @Override
                    public void onResponse(Call<GetCity> call, Response<GetCity> response) {
                        GetCity geoInfo = response.body();
                        Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                        intent.putExtra("key", geoInfo);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<GetCity> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "No response from the server, check the Internet connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void addItemsOnSpinnerOne() {
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
                addItemsOnSpinnerTwo(selectedCountries);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addItemsOnSpinnerTwo(String nameCity) {
        spinnerCity = (Spinner) findViewById(R.id.sppiner_City);
        List<String> list = new ArrayList<String>();
        list.addAll(InformAboutCityApplication.getLocalDataManager().getCountries().get(nameCity));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(dataAdapter);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selecedCity = spinnerCity.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}