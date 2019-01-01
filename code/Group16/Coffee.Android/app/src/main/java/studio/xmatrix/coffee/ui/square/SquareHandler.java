package studio.xmatrix.coffee.ui.square;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import com.scwang.smartrefresh.header.DeliveryHeader;
import studio.xmatrix.coffee.data.model.Content;
import studio.xmatrix.coffee.databinding.SquareFragmentBinding;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

public class SquareHandler implements Injectable {
    private FragmentActivity activity;
    private SquareFragmentBinding binding;
    private SquareAdapter adapter;
    private boolean hasMore;
    private static int EACH_PAGE = 7;
    private int currentPage = 1;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private SquareViewModel viewModel;

    SquareHandler(FragmentActivity activity, SquareFragmentBinding binding) {
        AppInjector.Companion.inject(this);
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(SquareViewModel.class);
        this.activity = activity;
        this.binding = binding;
        this.hasMore = true;
        initView();
        initData();
    }

    private void initView() {
        binding.squareList.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new SquareAdapter(activity);
        binding.squareList.setAdapter(adapter);
        // 刷新数据
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            currentPage = 1;
            viewModel.getPublic(currentPage, EACH_PAGE).observe(activity, resource -> {
                if (resource != null) {
                    switch (resource.getStatus()) {
                        case SUCCESS:
                            List<Content> contents = Objects.requireNonNull(resource.getData()).getResource();
                            if (contents != null) {
                                adapter.setData(contents);
                                this.hasMore = true;
                            } else {
                                this.hasMore = false;
                            }
                            binding.refreshLayout.setEnableLoadMore(this.hasMore);
                            refreshLayout.finishRefresh(200);
                            break;
                    }
                }
            });
        });
        binding.refreshLayout.setEnableLoadMore(true);
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (!hasMore) {
                refreshLayout.setEnableLoadMore(false);
            } else {
                currentPage++;
                viewModel.getPublic(currentPage, EACH_PAGE).observe(activity, resource -> {
                    if (resource != null) {
                        switch (resource.getStatus()) {
                            case SUCCESS:
                                List<Content> contents = Objects.requireNonNull(resource.getData()).getResource();
                                adapter.addData(contents);
                                if (contents == null || contents.size() != EACH_PAGE) {
                                    this.hasMore = false;
                                    binding.refreshLayout.setEnableLoadMore(false);
                                }
                                refreshLayout.finishLoadMore(200);
                                break;
                        }
                    }
                });
            }
        });
        binding.refreshLayout.setRefreshHeader(new DeliveryHeader(activity));

    }

    private void initData() {
        viewModel.getPublic(1, 7).observe(activity, resource -> {
            if (resource != null) {
                switch (resource.getStatus()) {
                    case SUCCESS:
                        List<Content> contents = Objects.requireNonNull(resource.getData()).getResource();
                        adapter.setData(contents);
                        break;
                }
            }
        });
    }

}
