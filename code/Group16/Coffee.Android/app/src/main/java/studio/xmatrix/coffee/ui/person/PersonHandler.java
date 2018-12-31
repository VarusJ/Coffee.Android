package studio.xmatrix.coffee.ui.person;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.PersonActivityBinding;
import studio.xmatrix.coffee.ui.AvatarImageBehavior;

import static studio.xmatrix.coffee.ui.AvatarImageBehavior.startAlphaAnimation;

class PersonHandler implements AppBarLayout.OnOffsetChangedListener {
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    private PersonActivity activity;
    private PersonActivityBinding binding;

    PersonHandler(PersonActivity activity, PersonActivityBinding binding) {
        this.activity = activity;
        this.binding = binding;
        initView();
    }

    private void initView() {
        Toolbar toolbar = binding.personToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);

        binding.personInclude.findViewById(R.id.person_btn_name).setOnClickListener(v -> {
            Toast.makeText(activity, "修改昵称", Toast.LENGTH_SHORT).show();
            // TODO
        });

        binding.personAppBar.addOnOffsetChangedListener(this);
        startAlphaAnimation(binding.personToolbarTitle, 0, View.INVISIBLE);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(binding.personLinear, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else if (!mIsTheTitleContainerVisible) {
            startAlphaAnimation(binding.personLinear, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
            mIsTheTitleContainerVisible = true;
        }

        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(binding.personToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else if (mIsTheTitleVisible) {
            startAlphaAnimation(binding.personToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
            mIsTheTitleVisible = false;
        }
    }

}
