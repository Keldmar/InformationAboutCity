package yahnenko.ua.informationaboutcity.api;

import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yahnenko.ua.informationaboutcity.DownloadCountries;
import yahnenko.ua.informationaboutcity.SearchByCity;
import yahnenko.ua.informationaboutcity.response.GetCity;

public class ApiManager {
    private final String URL = "http://api.geonames.org/";
    private final String USERNAME = "keldmar ";

    private SearchByCity serviceSearch;
    private DownloadCountries serviceCountries;

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

        serviceSearch = retrofit.create(SearchByCity.class);
        serviceCountries = retrofit2.create(DownloadCountries.class);
    }
    public Call<GetCity> getCity (String cityname){
        return serviceSearch.getCity(cityname, USERNAME);
    }

    public Call<Map<String, List<String>>> getCountries (){
        return serviceCountries.getCountries();
    }
}
