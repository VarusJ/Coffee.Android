package studio.xmatrix.coffee.ui.user;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.UserActivityBinding;
import studio.xmatrix.coffee.ui.home.HomeAdapter;

class UserHandler {
    private UserActivity activity;
    private UserActivityBinding binding;

    UserHandler(UserActivity activity, UserActivityBinding binding) {
        this.activity = activity;
        this.binding = binding;
        initView();
    }

    private void initView() {
        binding.userInclude.homeList.setLayoutManager(new LinearLayoutManager(activity));
        binding.userInclude.homeList.setAdapter(new HomeAdapter(activity));

        Toolbar toolbar = binding.userToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("MegaShow");
        activity.setSupportActionBar(toolbar);

        binding.userAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                binding.userBio.setVisibility(View.GONE);
                // Collapsed (make button visible and fab invisible)
            } else if (verticalOffset == 0) {

                // Expanded (make fab visible and toolbar button invisible)
            } else {
                binding.userBio.setVisibility(View.VISIBLE);

                // Somewhere in between
            }
        });
    }
}
