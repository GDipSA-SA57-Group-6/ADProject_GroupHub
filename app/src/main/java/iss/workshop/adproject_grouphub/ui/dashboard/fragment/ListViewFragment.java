package iss.workshop.adproject_grouphub.ui.dashboard.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import iss.workshop.adproject_grouphub.R;
import iss.workshop.adproject_grouphub.ui.dashboard.adapter.PlacesAdapter;
import iss.workshop.adproject_grouphub.model.Place;
import iss.workshop.adproject_grouphub.ui.dashboard.DashboardViewModel;

public class ListViewFragment extends Fragment {

    private ListView placesListView;
    private PlacesAdapter placesAdapter;
    private List<Place> placesList; // 原始数据列表
    private List<Place> filteredList; // 过滤后的数据列表

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        placesListView = view.findViewById(R.id.places_list_view);

        // 初始化数据
        initData();

        // 设置适配器
        placesAdapter = new PlacesAdapter(getContext(), placesList);
        placesListView.setAdapter(placesAdapter);

        return view;
    }

    // 初始化数据的方法，从数据库或其他数据源加载数据
    private void initData() {
        // TODO: 加载实际的数据
        placesList = new ArrayList<>();
        filteredList = new ArrayList<>(placesList);
        placesAdapter = new PlacesAdapter(getContext(), filteredList);
        placesListView.setAdapter(placesAdapter);
    }

    // 根据搜索和排序更新数据的方法
    public void updateData(String query, String sortMode) {
        filteredList.clear(); // 清空过滤列表以准备添加新的过滤结果

        // 对原始数据列表进行遍历，筛选出符合搜索条件的项
        for (Place place : placesList) {
            // 如果名字或描述包含搜索字符串，则添加到过滤列表中
            if (place.getName().toLowerCase().contains(query.toLowerCase()) || place.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(place);
            }
        }

        // 对过滤后的列表进行排序
        sortData(sortMode);
        // 通知适配器数据已经改变，更新ListView的显示
        placesAdapter.notifyDataSetChanged();
    }

    // 根据排序模式对列表进行排序
    private void sortData(String sortMode) {
        Log.d("ListViewFragment", "Sorting data: " + sortMode);
        if (sortMode.equals("asc")) {
            Collections.sort(filteredList, (p1, p2) -> p1.getName().compareTo(p2.getName()));
        } else if (sortMode.equals("desc")) {
            Collections.sort(filteredList, (p1, p2) -> p2.getName().compareTo(p1.getName()));
        }
    }

    // 根据过滤条件更新数据的方法
    public void updateDataWithFilter(String filterType) {
        Log.d("ListViewFragment", "Updating data with filter: " + filterType);
        sortData(filterType);
        placesAdapter.notifyDataSetChanged();
    }

    public void setPlacesList(List<Place> newPlacesList) {
        Log.d("ListViewFragment", "set place list ok");
        placesList.clear();
        placesList.addAll(newPlacesList); // 更新原始数据列表

        // 初始情况下显示所有数据，直到进行搜索
        filteredList.clear();
        filteredList.addAll(placesList);

        // 通知适配器数据变化
        if (placesAdapter != null) {
            placesAdapter.notifyDataSetChanged();
        } else {
            placesAdapter = new PlacesAdapter(getContext(), filteredList);
            placesListView.setAdapter(placesAdapter);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 获取 ViewModel 的实例
        DashboardViewModel viewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        // 观察搜索查询的变化
        viewModel.getSearchQuery().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String query) {
                // 根据搜索查询更新 ListView 的数据
                Log.d("ListViewFragment", "Search query changed: " + query);
                updateDataBasedOnQuery(query);
            }
        });
        viewModel.getPlaces().observe(getViewLifecycleOwner(), newPlacesList -> {
            // 当数据变化时，使用新数据更新 ListView
            setPlacesList(newPlacesList);
        });
    }
    private void updateDataBasedOnQuery(String query) {
        List<Place> filteredList = new ArrayList<>();
        for (Place place : placesList) {
            if (place.getName().toLowerCase().contains(query.toLowerCase()) ||
                    place.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(place);
            }
        }
        // 更新适配器数据
        placesAdapter.clear();
        placesAdapter.addAll(filteredList);
        placesAdapter.notifyDataSetChanged();
    }

}
