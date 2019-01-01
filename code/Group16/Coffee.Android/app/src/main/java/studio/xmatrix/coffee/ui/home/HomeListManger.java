package studio.xmatrix.coffee.ui.home;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.scwang.smartrefresh.header.DeliveryHeader;
import studio.xmatrix.coffee.databinding.HomeContentBinding;
import studio.xmatrix.coffee.databinding.ListStatusBinding;
import studio.xmatrix.coffee.ui.ListStatus;

public class HomeListManger {
    private Activity activity;
    private HomeContentBinding binding;
    private HomeViewModel viewModel;
    private int currentPage = 1;
    private static int EACH_PAGE = 7;
    private boolean hasMore = true;

    public HomeListManger(Activity activity, HomeContentBinding contentBinding, HomeViewModel viewModel) {
        this.activity = activity;
        this.binding = contentBinding;
        this.viewModel = viewModel;
        initView();
        refreshData();
    }

    private void initView() {
        binding.homeList.setLayoutManager(new LinearLayoutManager(activity));
        HomeAdapter adapter  =new HomeAdapter(activity);
        binding.homeList.setAdapter(adapter);
        binding.homeRefreshLayout.setRefreshHeader(new DeliveryHeader(activity));
        binding.homeRefreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(200);
        });
        binding.homeRefreshLayout.setEnableLoadMore(true);
        binding.homeRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishLoadMore(200);
        });
    }

    public void refreshData() {

    }

    public void loadMore() {

    }


    private void setStatus(ListStatus.StatusType status) {
        ListStatus.setStatus(binding.homeStatus, status);
        binding.homeRefreshLayout.setVisibility(status == ListStatus.StatusType.Done ? View.VISIBLE : View.GONE);
    }
}
