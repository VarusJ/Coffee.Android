package studio.xmatrix.coffee.ui.notice;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import studio.xmatrix.coffee.databinding.NoticeFragmentBinding;

public class NoticeHandler {
    private FragmentActivity activity;
    private NoticeFragmentBinding binding;


    NoticeHandler(FragmentActivity activity, NoticeFragmentBinding binding) {
        this.activity = activity;
        this.binding = binding;
        initView();
    }

    private void initView () {
        binding.noticeList.setLayoutManager(new LinearLayoutManager(activity));
        binding.noticeList.setAdapter(new NoticeAdapter(activity));
    }
}
