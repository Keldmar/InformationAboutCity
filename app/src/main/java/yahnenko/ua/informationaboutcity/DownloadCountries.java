package yahnenko.ua.informationaboutcity;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DownloadCountries {
    @GET("David-Haim/CountriesToCitiesJSON/master/countriesToCities.json")
    Call<Map<String, List<String>>> getCountries();
}
