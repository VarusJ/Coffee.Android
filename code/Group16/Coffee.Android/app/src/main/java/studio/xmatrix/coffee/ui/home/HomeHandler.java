package studio.xmatrix.coffee.ui.home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import studio.xmatrix.coffee.databinding.HomeFragmentBinding;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;

import javax.inject.Inject;

public class HomeHandler implements Injectable {
    private FragmentActivity activity;
    private HomeFragmentBinding binding;
    private HomeListManger listManger;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private HomeViewModel viewModel;

    HomeHandler(FragmentActivity activity, HomeFragmentBinding binding) {
        this.activity = activity;
        this.binding = binding;
        initView();
        AppInjector.Companion.inject(this);
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(HomeViewModel.class);
        listManger = new HomeListManger(activity, binding.homeContent, viewModel);
    }

    private void initView() {
    }
}
