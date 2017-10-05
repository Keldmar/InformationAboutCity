package yahnenko.ua.informationaboutcity.api;

import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yahnenko.ua.informationaboutcity.pojo.City;

public class ApiManager {
    private static final String URL = "http://api.geonames.org/";
    private static final String USERNAME = "keldmar ";

    private WikipediaService serviceSearch;
    private CountriesService serviceCountries;

    public ApiManager() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .baseUrl(URL)
                .client(httpClient.build())
                .build();

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        serviceSearch = retrofit.create(WikipediaService.class);
        serviceCountries = retrofit2.create(CountriesService.class);
    }

    public Call<City> getCity(String cityname) {
        return serviceSearch.getCity(cityname, USERNAME);
    }

    public Call<Map<String, List<String>>> getCountries() {
        return serviceCountries.getCountries();
    }
}
