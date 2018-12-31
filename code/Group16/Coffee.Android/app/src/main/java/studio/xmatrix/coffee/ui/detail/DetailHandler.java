package studio.xmatrix.coffee.ui.detail;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import studio.xmatrix.coffee.databinding.CommentFragmentBinding;
import studio.xmatrix.coffee.databinding.DetailActivityBinding;

import java.util.Objects;

public class DetailHandler {
    private DetailActivity activity;
    private DetailActivityBinding binding;
    private CommentFragment commentFragment;
    private CommentFragmentBinding fragmentBinding;

    public DetailHandler(DetailActivity activity, DetailActivityBinding binding) {
        this.activity = activity;
        this.binding = binding;
        initView();
    }

    private void initView() {
        binding.commentList.setLayoutManager(new LinearLayoutManager(activity));
        binding.commentList.setAdapter(new CommentAdapter(activity));
        binding.commentList.setNestedScrollingEnabled(false);
        binding.addTitle.setOnClickListener(this::onClickAddComment);
        binding.detailContent.btnComment.setOnClickListener(this::onClickAddComment);
        binding.detailContent.btnEdit.setVisibility(View.VISIBLE);
        binding.detailContent.btnRemove.setVisibility(View.VISIBLE);
    }

    public void onClickAddComment(View v) {
        commentFragment = new CommentFragment((binding) -> {
            this.fragmentBinding = binding;
            (new Handler()).postDelayed(() -> {
                binding.editComment.setFocusable(true);
                binding.editComment.requestFocus();
                InputMethodManager inputManager = (InputMethodManager) binding.editComment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(inputManager).showSoftInput(binding.editComment, 0);
            }, 666);
        });
        commentFragment.show(activity.getSupportFragmentManager(), "addComment");
    }
}
