package iss.workshop.adproject_grouphub;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.adproject_grouphub.ui.dashboard.fragment.FilterFragment;
import iss.workshop.adproject_grouphub.ui.dashboard.fragment.ListViewFragment;
import iss.workshop.adproject_grouphub.ui.dashboard.fragment.SearchFragment;
import iss.workshop.adproject_grouphub.model.Place;
import iss.workshop.adproject_grouphub.ui.dashboard.DashboardViewModel;

public class MainActivity extends AppCompatActivity
        implements SearchFragment.OnSearchListener, FilterFragment.OnFilterListener {

    private SearchFragment searchFragment;
    private FilterFragment filterFragment;
    private ListViewFragment listViewFragment;

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 检查位置权限是否已经被授予
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 请求位置权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            // 权限已经被授予，可以执行需要这些权限的操作
            init();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        listViewFragment = (ListViewFragment) getSupportFragmentManager().findFragmentById(R.id.listViewFragmentContainer);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限被授予
                init();
            } else {
                // 权限被拒绝，可以在这里显示一些提醒信息，告诉用户为什么需要这个权限
            }
        }
    }

    private void init() {
        // 将原来onCreate里的初始化代码移动到这里，确保有权限时再执行这些初始化操作
        Log.d("Main Activity","should init viewmodel");
        DashboardViewModel sharedViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        Log.d("Main Activity","should init viewmodel done");
        sharedViewModel.setPlaces(createSampleData());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private List<Place> createSampleData() {
        Log.d("Main Activity","should create sample data");
        List<Place> places = new ArrayList<>();
        places.add(new Place("Place 1", "Description 1", R.drawable.ic_home_black_24dp, 1.3521, 103.8198)); // 新加坡植物园
        places.add(new Place("Place 2", "Description 2", R.drawable.ic_home_black_24dp, 1.2839, 103.8609)); // 滨海湾金沙
        places.add(new Place("Place 3", "Description 3", R.drawable.ic_home_black_24dp, 1.3448, 103.8224)); // 动物园
        places.add(new Place("Place 4", "Description 4", R.drawable.ic_home_black_24dp, 1.2897, 103.8501)); // 国家博物馆
        places.add(new Place("Place 5", "Description 5", R.drawable.ic_home_black_24dp, 1.2814, 103.8636)); // 亚洲文明博物馆
        places.add(new Place("Place 6", "Description 6", R.drawable.ic_home_black_24dp, 1.3114, 103.7786)); // 裕廊飞禽公园
        places.add(new Place("Place 7", "Description 7", R.drawable.ic_home_black_24dp, 1.3604, 103.9898)); // 樟宜机场
        places.add(new Place("Place 8", "Description 8", R.drawable.ic_home_black_24dp, 1.3030, 103.8318)); // 乌节路
        // 添加更多示例数据
        return places;
    }

    @Override
    public void onSearch(String query, String sortMode) {
        DashboardViewModel sharedViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        sharedViewModel.setSearchQuery(query); // Assuming you have such a method.
    }
    @Override
    public void onFilterSelected(String filterType) {
        ListViewFragment listViewFragment = (ListViewFragment) getSupportFragmentManager().findFragmentByTag("ListViewFragmentTag");
        if (listViewFragment != null) {
            listViewFragment.updateDataWithFilter(filterType);
        } else {
            Log.d("MainActivity", "ListViewFragment is null");
        }
    }
}
