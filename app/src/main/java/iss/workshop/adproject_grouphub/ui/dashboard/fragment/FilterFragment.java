package iss.workshop.adproject_grouphub.ui.dashboard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import iss.workshop.adproject_grouphub.R;

public class FilterFragment extends Fragment {

    private Button sortAscButton;
    private Button sortDescButton;
    private OnFilterListener filterListener;

    public interface OnFilterListener {
        void onFilterSelected(String filterType);
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof OnFilterListener) {
            filterListener = (OnFilterListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFilterListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        sortAscButton = view.findViewById(R.id.sort_asc_button);
        sortDescButton = view.findViewById(R.id.sort_desc_button);

        sortAscButton.setOnClickListener(v -> {
            if (filterListener != null) {
                filterListener.onFilterSelected("asc");
            }
        });

        sortDescButton.setOnClickListener(v -> {
            if (filterListener != null) {
                filterListener.onFilterSelected("desc");
            }
        });

        return view;
    }
}
