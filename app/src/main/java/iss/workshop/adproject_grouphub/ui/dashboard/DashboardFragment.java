package iss.workshop.adproject_grouphub.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import iss.workshop.adproject_grouphub.R;
import iss.workshop.adproject_grouphub.ui.dashboard.activity.MapActivity;
import iss.workshop.adproject_grouphub.ui.dashboard.adapter.PlacesAdapter;
import iss.workshop.adproject_grouphub.ui.dashboard.fragment.FilterFragment;
import iss.workshop.adproject_grouphub.ui.dashboard.fragment.ListViewFragment;
import iss.workshop.adproject_grouphub.ui.dashboard.fragment.SearchFragment;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;
    private PlacesAdapter placesAdapter;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        // 观察地点列表
        viewModel.getPlaces().observe(getViewLifecycleOwner(), places -> {
            if (places != null) {
                // 传递地点列表到PlacesAdapter
                placesAdapter = new PlacesAdapter(getContext(), places);
                ListView listView = view.findViewById(R.id.places_list_view); // 假设您的ListView ID是places_list_view
                listView.setAdapter(placesAdapter);
            }
        });
        // Dynamically add SearchFragment
        getChildFragmentManager().beginTransaction()
                .replace(R.id.searchFragmentContainer, new SearchFragment())
                .commit();

        // Dynamically add FilterFragment
        getChildFragmentManager().beginTransaction()
                .replace(R.id.filterFragmentContainer, new FilterFragment())
                .commit();

        // Dynamically add ListViewFragment
        getChildFragmentManager().beginTransaction()
                .replace(R.id.listViewFragmentContainer, new ListViewFragment())
                .commit();
    }
}