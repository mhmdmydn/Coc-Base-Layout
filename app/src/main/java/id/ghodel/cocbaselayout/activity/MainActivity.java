package id.ghodel.cocbaselayout.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import id.ghodel.cocbaselayout.R;
import id.ghodel.cocbaselayout.fragment.builderhall.BuilderHallFragment;
import id.ghodel.cocbaselayout.fragment.townhall.FarmingFragment;
import id.ghodel.cocbaselayout.fragment.townhall.HybridFragment;
import id.ghodel.cocbaselayout.fragment.townhall.TrophyFragment;
import id.ghodel.cocbaselayout.fragment.townhall.WarFragment;
import id.ghodel.cocbaselayout.utils.CenteredToolbar;
import id.ghodel.cocbaselayout.utils.MainUtils;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String ID = "id";
    public static final String IMAGE = "image";
    public static final String LAYOUT_LINK = "layout_link";
    public static final String TYPE = "type";
    public static final String HALL = "hall";
    public static final String DOWNLOAD = "download";
    public static final String VIEW = "view";
    public static final String LIKE = "like";
    public static final String BASE_STRENGTH = "base_strength";
    public static final String CC_TROOPS = "cc_troops";

    private BottomNavigationView navigation;
    private CenteredToolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initLogic();
        initListener();

    }

    @Override
    public void initView() {
        navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("COC BASE LAYOUT");

        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
    }

    @Override
    public void initLogic() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        loadFragment(new TrophyFragment());
    }

    @Override
    public void initListener() {
        navigation.setOnNavigationItemSelectedListener(bottomListener);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.nav_menu_war:
                    fragment = new WarFragment();
                    break;
                case R.id.nav_menu_trophy:
                    fragment = new TrophyFragment();
                    break;
                case R.id.nav_menu_hybrid:
                    fragment = new HybridFragment();
                    break;
                case R.id.nav_menu_farming:
                    fragment = new FarmingFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    /**
     * Fragment
     **/
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_menu_townhall:
                fragment = new TrophyFragment();
                navigation.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_menu_builder:
                fragment = new BuilderHallFragment();
                navigation.setVisibility(View.GONE);
                break;
            case R.id.nav_menu_share:
                MainUtils.shareApplication(MainActivity.this);
                break;
            case R.id.nav_menu_info:
                MainUtils.showDialogInfo(MainActivity.this);
                break;
        }
        return loadFragment(fragment);
    }

}