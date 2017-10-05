package yahnenko.ua.informationaboutcity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import yahnenko.ua.informationaboutcity.response.GetCity;

public interface SearchByCity {
    @GET("wikipediaSearchJSON")
    Call<GetCity> getCity(@Query("q") String cityname, @Query("username")String USERNAME);
}
