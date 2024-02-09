package iss.workshop.adproject_grouphub.ui.dashboard.activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Type;
import java.util.List;

import iss.workshop.adproject_grouphub.R;
import iss.workshop.adproject_grouphub.model.Place;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Place> places;
    private LatLng initialLocation; // 不再这里初始化
    private final float initialZoom = 15.0f; // 初始缩放级别

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // 从Intent获取地点列表的JSON字符串
        String placesJson = getIntent().getStringExtra("places_list");
        Type listType = new TypeToken<List<Place>>(){}.getType();
        places = new Gson().fromJson(placesJson, listType);

        // 从Intent获取经纬度值
        double latitude = getIntent().getDoubleExtra("latitude", 1.3521); // 使用新加坡的默认值作为备选
        double longitude = getIntent().getDoubleExtra("longitude", 103.8198); // 使用新加坡的默认值作为备选

        // 在获取经纬度之后初始化initialLocation
        initialLocation = new LatLng(latitude, longitude);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 设置按钮点击事件
        Button resetCameraButton = findViewById(R.id.button_reset_camera);
        resetCameraButton.setOnClickListener(view -> resetCameraPosition());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 为places列表中的每个地点添加标记
        if (places != null) {
            for (Place place : places) {
                LatLng location = new LatLng(place.getLatitude(), place.getLongitude());
                mMap.addMarker(new MarkerOptions().position(location).title(place.getName()));
            }
        }

        // 启用地图的缩放控件
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // 将相机移动到初始位置
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, initialZoom));
    }

    private void resetCameraPosition() {
        if (mMap != null) {
            // 将相机回退到初始位置
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, initialZoom));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 更新Activity的Intent
        setIntent(intent);

        // 从新的Intent获取地点列表的JSON字符串
        String placesJson = intent.getStringExtra("places_list");
        Type listType = new TypeToken<List<Place>>(){}.getType();
        places = new Gson().fromJson(placesJson, listType);

        // 从新的Intent获取经纬度值，并更新initialLocation
        double latitude = intent.getDoubleExtra("latitude", 1.3521); // 使用新加坡的默认值作为备选
        double longitude = intent.getDoubleExtra("longitude", 103.8198); // 使用新加坡的默认值作为备选
        initialLocation = new LatLng(latitude, longitude);

        // 如果地图已经准备好，可以在这里调用一个方法来更新地图标记
        if (mMap != null) {
            updateMapMarkers();
        }
    }

    private void updateMapMarkers() {
        mMap.clear(); // 清除旧的标记
        // 添加新的标记
        for (Place place : places) {
            LatLng location = new LatLng(place.getLatitude(), place.getLongitude());
            mMap.addMarker(new MarkerOptions().position(location).title(place.getName()));
        }
        // 重新定位到初始位置
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, initialZoom));
    }


}
