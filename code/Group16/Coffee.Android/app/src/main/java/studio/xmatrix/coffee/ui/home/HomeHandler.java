package studio.xmatrix.coffee.ui.home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import studio.xmatrix.coffee.data.store.DefaultSharedPref;
import studio.xmatrix.coffee.databinding.HomeFragmentBinding;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;
import studio.xmatrix.coffee.ui.ListStatus;

import javax.inject.Inject;

public class HomeHandler implements Injectable {
    private FragmentActivity activity;
    private HomeFragmentBinding binding;
    private HomeListManger listManger;
    private String id = "init";

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
        listManger.setStatus(ListStatus.StatusType.Loading);
    }


    public void refreshData() {
        String userId = DefaultSharedPref.INSTANCE.get(DefaultSharedPref.Key.UserId);
        if (!id.equals(userId)) {
            id = userId;
            if (!userId.equals("")) {
                listManger.setId("self");
            } else {
                listManger.setId("");
            }
        }
    }

    private void initView() {

    }


}
