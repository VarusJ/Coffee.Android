package studio.xmatrix.coffee.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import studio.xmatrix.coffee.databinding.CommentFragmentBinding;
import studio.xmatrix.coffee.databinding.DetailActivityBinding;
import studio.xmatrix.coffee.ui.square.TagAdapter;
import studio.xmatrix.coffee.ui.user.UserActivity;

import java.util.ArrayList;
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
        binding.detailContent.userAvatar.setOnClickListener(this::onClickUser);
        binding.detailContent.userName.setOnClickListener(this::onClickUser);

        TagAdapter adapter = new TagAdapter(activity);
        binding.detailContent.tagLayout.setAdapter(adapter);
        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(activity)
                .setChildGravity(Gravity.TOP)
                .setScrollingEnabled(true)
                .setGravityResolver(position -> Gravity.START)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();
        binding.detailContent.tagLayout.setLayoutManager(chipsLayoutManager);
        binding.detailContent.tagLayout.addItemDecoration(new SpacingItemDecoration(10, 10));

        // 图片加载
        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl("test" + i);
            info.setBigImageUrl("test2" + i);
            imageInfos.add(info);
        }
        binding.detailContent.nineGridImage.setAdapter(new NineGridViewClickAdapter(activity, imageInfos));
    }

    public void onClickUser(View v) {
        activity.startActivity(new Intent(activity, UserActivity.class));
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
