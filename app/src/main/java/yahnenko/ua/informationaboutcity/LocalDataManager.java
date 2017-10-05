package yahnenko.ua.informationaboutcity;


import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class LocalDataManager {
    private static final String KEY_CITY = "KEY_CITY";

    private SharedPreferences sharedPref;

    public LocalDataManager(SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;
    }

    public Map<String, List<String>> getCountries() {
        return new Gson().fromJson(sharedPref.getString(KEY_CITY, ""), Map.class);
    }

    public void setCountries(Map<String, List<String>> map) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_CITY, new Gson().toJson(map));
        editor.apply();
    }


}
