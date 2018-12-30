package studio.xmatrix.coffee.ui.detail;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;
import studio.xmatrix.coffee.databinding.DetailActivityBinding;

public class DetailHandler {
    private DetailActivity activity;
    private DetailActivityBinding binding;

    public DetailHandler(DetailActivity activity, DetailActivityBinding binding) {
        this.activity = activity;
        this.binding = binding;
        initView();
    }

    private void initView() {
        binding.commentList.setLayoutManager(new LinearLayoutManager(activity));
        binding.commentList.setAdapter(new CommentAdapter(activity));
        binding.commentList.setNestedScrollingEnabled(false);
    }

    public void onClickAddComment(View v) {
        Toast.makeText(activity, "Add", Toast.LENGTH_SHORT).show();
    }
}
