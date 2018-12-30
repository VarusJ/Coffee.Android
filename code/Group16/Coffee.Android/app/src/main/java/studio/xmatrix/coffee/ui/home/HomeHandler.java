package studio.xmatrix.coffee.ui.home;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import studio.xmatrix.coffee.databinding.HomeFragmentBinding;

public class HomeHandler {
    private FragmentActivity activity;
    private HomeFragmentBinding binding;

    public HomeHandler(FragmentActivity activity, HomeFragmentBinding binding) {
        this.activity = activity;
        this.binding = binding;
        initView();
    }

    private void initView() {
        binding.homeContent.homeList.setLayoutManager(new LinearLayoutManager(activity));
        binding.homeContent.homeList.setAdapter(new HomeAdapter(activity));
    }
}
