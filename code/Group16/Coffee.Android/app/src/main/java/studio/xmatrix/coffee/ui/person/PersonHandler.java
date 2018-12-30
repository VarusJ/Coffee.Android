package studio.xmatrix.coffee.ui.person;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.PersonActivityBinding;

class PersonHandler {
    private PersonActivity activity;
    private PersonActivityBinding binding;

    PersonHandler(PersonActivity activity, PersonActivityBinding binding) {
        this.activity = activity;
        this.binding = binding;
        initView();
    }

    private void initView() {
        Toolbar toolbar = binding.toolbar;
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("MegaShow");
        activity.setSupportActionBar(toolbar);

        binding.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                binding.personAvatar.setVisibility(View.GONE);
                binding.personBio.setVisibility(View.GONE);
                // Collapsed (make button visible and fab invisible)
            } else if (verticalOffset == 0) {

                // Expanded (make fab visible and toolbar button invisible)
            } else {
                binding.personAvatar.setVisibility(View.VISIBLE);
                binding.personBio.setVisibility(View.VISIBLE);

                // Somewhere in between
            }
        });

        binding.include.findViewById(R.id.person_btn_name).setOnClickListener(v -> {
            Toast.makeText(activity, "修改昵称", Toast.LENGTH_SHORT).show();
            // TODO
        });
    }
}
