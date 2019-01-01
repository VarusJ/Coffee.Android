package studio.xmatrix.coffee.ui.notice;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.scwang.smartrefresh.header.DeliveryHeader;
import studio.xmatrix.coffee.databinding.NoticeFragmentBinding;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;
import studio.xmatrix.coffee.ui.ListStatus;
import studio.xmatrix.coffee.ui.home.HomeViewModel;

import javax.inject.Inject;

public class NoticeHandler implements Injectable {
    private FragmentActivity activity;
    private NoticeFragmentBinding binding;
    private NoticeAdapter adapter;


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private NoticeViewModel viewModel;


    NoticeHandler(FragmentActivity activity, NoticeFragmentBinding binding) {
        this.activity = activity;
        this.binding = binding;
        AppInjector.Companion.inject(this);
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(NoticeViewModel.class);
        initView();
    }

    private void initView () {
        binding.noticeList.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new NoticeAdapter(activity);
        binding.noticeList.setAdapter(adapter);
        binding.noticeRefreshLayout.setRefreshHeader(new DeliveryHeader(activity));
        binding.noticeRefreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(true);
        });
    }

    private void setStatus(ListStatus.StatusType status) {
        ListStatus.setStatus(binding.noticeStatus, status);
        binding.noticeRefreshLayout.setVisibility(status == ListStatus.StatusType.Done ? View.VISIBLE : View.GONE);
    }
}
