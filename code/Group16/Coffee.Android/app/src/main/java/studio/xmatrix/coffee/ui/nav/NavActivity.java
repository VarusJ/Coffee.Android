package studio.xmatrix.coffee.ui.nav;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.data.common.network.Status;
import studio.xmatrix.coffee.data.model.User;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;
import studio.xmatrix.coffee.ui.NightModeConfig;
import studio.xmatrix.coffee.ui.add.AddActivity;
import studio.xmatrix.coffee.ui.admin.AdminActivity;
import studio.xmatrix.coffee.ui.home.HomeFragment;
import studio.xmatrix.coffee.ui.notice.NoticeFragment;
import studio.xmatrix.coffee.ui.person.PersonActivity;
import studio.xmatrix.coffee.ui.setting.SettingsActivity;
import studio.xmatrix.coffee.ui.square.SquareFragment;
import studio.xmatrix.coffee.ui.user.UserActivity;

import javax.inject.Inject;
import java.util.Objects;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Injectable {


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private NavViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppInjector.Companion.inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NavViewModel.class);

        setContentView(R.layout.nav_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(this, AddActivity.class)));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // 夜间模式菜单
        MenuItem item = navigationView.getMenu().findItem(R.id.nav_night);


        int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentMode != Configuration.UI_MODE_NIGHT_YES) {
            item.setIcon(getDrawable(R.drawable.ic_night));
            item.setTitle("夜间模式");
        } else {
            item.setIcon(getDrawable(R.drawable.ic_day));
            item.setTitle("日间模式");
        }

        View header = navigationView.getHeaderView(0);
        View headerLayout = header.findViewById(R.id.head_user_avatar);
        headerLayout.setOnClickListener(v -> startActivity(new Intent(this, AdminActivity.class)));

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.home_view);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            private HomeFragment homeActivity;
            private NoticeFragment noticeFragment;
            private SquareFragment squareFragment;

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        if (homeActivity == null) homeActivity = new HomeFragment();
                        return homeActivity;
                    case 1:
                        if (squareFragment == null) squareFragment = new SquareFragment();
                        return squareFragment;
                    case 2:
                        if (noticeFragment == null) noticeFragment = new NoticeFragment();
                        return noticeFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "我的";
                    case 1:
                        return "广场";
                    case 2:
                        return "通知";
                }
                return "";
            }

        });
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getUserInfo().observe(this, resource -> {
            if (resource != null) {
                if (resource.getStatus() == Status.SUCCESS) {
                    User userInfo = Objects.requireNonNull(resource.getData()).getResource();
                    if (userInfo == null) return;
                    TextView userNameText = findViewById(R.id.head_user_name);
                    userNameText.setText(userInfo.getName());
                    TextView userText = findViewById(R.id.head_user_text);
                    userText.setText(userInfo.getBio());
                    ImageView userAvatar = findViewById(R.id.head_user_avatar);
                    // todo
//                    viewModel.getUserAvatar(userInfo.getId()).observe(this, res -> {
//                        if (res != null && res.getStatus() == Status.SUCCESS) {
//                            userAvatar.setImageBitmap(res.getData());
//                        }
//                    });
                    userAvatar.setOnClickListener(v -> {
                        startActivity(new Intent(this, UserActivity.class));
                    });

                    // Toast.makeText(this, userInfo.getName(), Toast.LENGTH_SHORT).show();
                } else if (resource.getStatus() == Status.ERROR) {
                    Toast.makeText(this, resource.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (id == R.id.nav_night) {
            int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (currentMode != Configuration.UI_MODE_NIGHT_YES) {
                //保存夜间模式状态,Application中可以根据这个值判断是否设置夜间模式
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                //ThemeConfig主题配置，这里只是保存了是否是夜间模式的boolean值
                NightModeConfig.getInstance().setNightMode(getApplicationContext(), true);
                item.setIcon(getDrawable(R.drawable.ic_day));
                item.setTitle("日间模式");
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                NightModeConfig.getInstance().setNightMode(getApplicationContext(), false);
                item.setIcon(getDrawable(R.drawable.ic_night));
                item.setTitle("夜间模式");
            }
            //需要recreate才能生效
            drawer.closeDrawer(GravityCompat.START);
            recreate();
            // (new Handler()).postDelayed(this::recreate, 300);
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "退出登陆", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_person) {
            startActivity(new Intent(this, PersonActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, SettingsActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
