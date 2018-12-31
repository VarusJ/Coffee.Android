package studio.xmatrix.coffee.ui.user;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.UserActivityBinding;
import studio.xmatrix.coffee.ui.home.HomeAdapter;

import static studio.xmatrix.coffee.ui.AvatarImageBehavior.startAlphaAnimation;

class UserHandler implements AppBarLayout.OnOffsetChangedListener {
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
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
        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);

        binding.userAppBar.addOnOffsetChangedListener(this);
        startAlphaAnimation(binding.userToolbarTitle, 0, View.INVISIBLE);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(binding.userToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else if (mIsTheTitleVisible) {
            startAlphaAnimation(binding.userToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
            mIsTheTitleVisible = false;
        }
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(binding.userLinear, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else if (!mIsTheTitleContainerVisible) {
            startAlphaAnimation(binding.userLinear, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
            mIsTheTitleContainerVisible = true;
        }
    }
}
