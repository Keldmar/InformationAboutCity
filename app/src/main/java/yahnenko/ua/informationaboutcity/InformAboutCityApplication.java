package yahnenko.ua.informationaboutcity;

import android.app.Application;

import com.ironz.binaryprefs.BinaryPreferencesBuilder;

import yahnenko.ua.informationaboutcity.api.ApiManager;
import yahnenko.ua.informationaboutcity.local.LocalDataManager;

public class InformAboutCityApplication extends Application {

    private static ApiManager apiManager;
    private static LocalDataManager localDataManager;

    public static ApiManager getApiManager() {
        return apiManager;
    }

    public static LocalDataManager getLocalDataManager() {
        return localDataManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apiManager = new ApiManager();
        localDataManager = new LocalDataManager(new BinaryPreferencesBuilder(this).build());
    }
}
