package studio.xmatrix.coffee.ui.detail;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import studio.xmatrix.coffee.data.model.Content;
import studio.xmatrix.coffee.data.service.resource.CommentsResource;
import studio.xmatrix.coffee.databinding.CommentFragmentBinding;
import studio.xmatrix.coffee.databinding.DetailActivityBinding;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;
import studio.xmatrix.coffee.ui.square.SquareViewModel;
import studio.xmatrix.coffee.ui.square.TagAdapter;
import studio.xmatrix.coffee.ui.user.UserActivity;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DetailHandler implements Injectable {
    private DetailActivity activity;
    private DetailActivityBinding binding;
    private CommentFragment commentFragment;
    private CommentFragmentBinding fragmentBinding;
    private CommentAdapter commentAdapter;
    private TagAdapter tagAdapter;
    private String id;


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private DetailViewModel viewModel;

    public DetailHandler(DetailActivity activity, DetailActivityBinding binding) {
        AppInjector.Companion.inject(this);
        this.activity = activity;
        this.binding = binding;
        Bundle bundle = activity.getIntent().getExtras();
        this.id = Objects.requireNonNull(bundle).getString("id");

        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(DetailViewModel.class);
        initView();
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initView() {
        Objects.requireNonNull(activity.getSupportActionBar()).setTitle("内容详情");
        // 评论列表
        binding.commentList.setLayoutManager(new LinearLayoutManager(activity));
        commentAdapter = new CommentAdapter(activity);
        binding.commentList.setAdapter(commentAdapter);

        viewModel.getComments(id).observe(activity, resource -> {
            if (resource != null) {
                switch (resource.getStatus()) {
                    case SUCCESS:
                        List<CommentsResource.CommentForContent> content = Objects.requireNonNull(resource.getData()).getResource();
                        commentAdapter.setData(content);
                }
            }
        });


        // 底部栏
        binding.addTitle.setOnClickListener(this::onClickAddComment);

        // 详情卡片
        binding.detailContent.btnComment.setOnClickListener(this::onClickAddComment);
        binding.detailContent.btnEdit.setVisibility(View.VISIBLE);
        binding.detailContent.btnRemove.setVisibility(View.VISIBLE);
        binding.detailContent.userAvatar.setOnClickListener(this::onClickUser);
        binding.detailContent.userName.setOnClickListener(this::onClickUser);

        viewModel.getDetail(id).observe(activity, resource -> {
            if (resource != null) {
                switch (resource.getStatus()) {
                    case SUCCESS:
                        Content data = Objects.requireNonNull(resource.getData()).getResource();
                        if (data != null) {
                            binding.detailContent.setModel(data);
                            tagAdapter.setData(data.tags);
                            binding.detailContent.contentTime.setText(getTime(data.getPublishDate()));
                        }
                        break;
                }
            }
        });

        // 标签
        tagAdapter = new TagAdapter(activity);
        binding.detailContent.tagLayout.setAdapter(tagAdapter);
        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(activity)
                .setChildGravity(Gravity.TOP)
                .setScrollingEnabled(true)
                .setGravityResolver(position -> Gravity.START)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .build();
        binding.detailContent.tagLayout.setLayoutManager(chipsLayoutManager);
        binding.detailContent.tagLayout.addItemDecoration(new SpacingItemDecoration(10, 10));

        // 图片加载
//        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            ImageInfo info = new ImageInfo();
//            info.setThumbnailUrl("test" + i);
//            info.setBigImageUrl("test2" + i);
//            imageInfos.add(info);
//        }
//        binding.detailContent.nineGridImage.setAdapter(new NineGridViewClickAdapter(activity, imageInfos));
    }

    public static String getTime(Long publish) {
        long now = new Date().getTime();
        if (now - publish > 1000 * 60 * 60 * 24 * 7) {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(publish);
        } else if (now - publish > 1000 * 60 * 60 * 24) {
            return Long.toString((now - publish) / (1000 * 60 * 60 * 24)) + "天前";
        } else if (now - publish > 1000 * 60 * 60) {
            return Long.toString((now - publish) / (1000 * 60 * 60)) + "小时前";
        } else if (now - publish > 1000 * 60) {
            return Long.toString((now - publish) / (1000 * 60 * 60)) + "分钟前";
        } else {
            return "刚刚";
        }

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
