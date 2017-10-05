package yahnenko.ua.informationaboutcity.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import yahnenko.ua.informationaboutcity.pojo.City;

public interface WikipediaService {
    @GET("wikipediaSearchJSON")
    Call<City> getCity(@Query("q") String cityName, @Query("username") String username);
}
