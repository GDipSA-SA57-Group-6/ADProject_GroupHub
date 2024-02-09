package iss.workshop.adproject_grouphub.ui.dashboard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import iss.workshop.adproject_grouphub.R;
import iss.workshop.adproject_grouphub.ui.dashboard.DashboardViewModel;

public class SearchFragment extends Fragment {

    private EditText searchInput;
    private Button searchButton;
    private OnSearchListener searchListener;

    public interface OnSearchListener {
        void onSearch(String query, String sortMode);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchListener) {
            searchListener = (OnSearchListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchInput = view.findViewById(R.id.search_input);
        searchButton = view.findViewById(R.id.search_button);

        // 设置搜索按钮的点击事件
        searchButton.setOnClickListener(v -> performSearch());

        return view;
    }

    private void performSearch() {
        String query = searchInput.getText().toString();
        DashboardViewModel viewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        viewModel.setSearchQuery(query);
    }
}
