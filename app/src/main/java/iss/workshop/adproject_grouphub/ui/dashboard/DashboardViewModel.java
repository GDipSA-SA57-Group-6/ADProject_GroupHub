package iss.workshop.adproject_grouphub.ui.dashboard;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iss.workshop.adproject_grouphub.model.Place;

public class DashboardViewModel extends ViewModel {
    private final MutableLiveData<List<Place>> places = new MutableLiveData<>();
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>();

    // 设置搜索查询
    public void setSearchQuery(String query) {
        Log.d("DashboardViewModel", "Search query set: " + query);
        searchQuery.setValue(query);
    }

    // 获取搜索查询
    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }
    public void setPlaces(List<Place> placesList) {
        places.setValue(placesList);
    }
    public LiveData<List<Place>> getPlaces() {
        return places;
    }
}