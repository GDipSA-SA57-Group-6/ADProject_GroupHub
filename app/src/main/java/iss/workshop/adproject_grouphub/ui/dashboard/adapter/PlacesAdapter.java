package iss.workshop.adproject_grouphub.ui.dashboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.google.gson.Gson;

import iss.workshop.adproject_grouphub.R;
import iss.workshop.adproject_grouphub.model.Place;
import iss.workshop.adproject_grouphub.ui.dashboard.activity.MapActivity;

public class PlacesAdapter extends ArrayAdapter<Place> {
    static class ViewHolder {
        TextView placeNameTextView;
        TextView placeDescriptionTextView;
        ImageView placeImageView;
        Button btnShowMap;
    }
    private List<Place> allPlaces;

    public PlacesAdapter(Context context, List<Place> places) {
        super(context, 0, places);
        this.allPlaces = places;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 获取当前项的Place实例
        Log.d("PlacesAdapter","Get view successful");

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_place, parent, false);
            holder = new ViewHolder();
            holder.placeNameTextView = convertView.findViewById(R.id.place_name);
            holder.placeDescriptionTextView = convertView.findViewById(R.id.place_description);
            holder.placeImageView = convertView.findViewById(R.id.place_image);
            holder.btnShowMap = convertView.findViewById(R.id.place_show_map);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Place place = getItem(position);
        holder.placeNameTextView.setText(place.getName());
        holder.placeDescriptionTextView.setText(place.getDescription());
        holder.placeImageView.setImageResource(place.getImageResourceId());

        Button btnShowMap = convertView.findViewById(R.id.place_show_map);
        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动地图Activity或Fragment
                Intent intent = new Intent(getContext(), MapActivity.class);
                intent.putExtra("latitude", place.getLatitude());
                intent.putExtra("longitude", place.getLongitude());
                String placesJson = new Gson().toJson(allPlaces);
                intent.putExtra("places_list", placesJson);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    public void updatePlaces(List<Place> newPlaces) {
        allPlaces.clear();
        allPlaces.addAll(newPlaces);
        notifyDataSetChanged();
    }

}

