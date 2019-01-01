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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import studio.xmatrix.coffee.data.common.network.Status;
import studio.xmatrix.coffee.data.model.Content;
import studio.xmatrix.coffee.data.service.LikeService;
import studio.xmatrix.coffee.data.service.resource.CommentsResource;
import studio.xmatrix.coffee.databinding.CommentFragmentBinding;
import studio.xmatrix.coffee.databinding.DetailActivityBinding;
import studio.xmatrix.coffee.inject.AppInjector;
import studio.xmatrix.coffee.inject.Injectable;
import studio.xmatrix.coffee.ui.ListStatus;
import studio.xmatrix.coffee.ui.add.AddActivity;
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
    private List<String> likeData;
    private String fatherId;
    private String contentId;
    private String fatherName;
    private Boolean isReply;


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private DetailViewModel viewModel;

    public DetailHandler(DetailActivity activity, DetailActivityBinding binding) {
        this.activity = activity;
        this.binding = binding;
        Bundle bundle = activity.getIntent().getExtras();
        this.id = Objects.requireNonNull(bundle).getString("id", "");
        if (this.id.equals("")) {
            activity.finish();
            return;
        }
        AppInjector.Companion.inject(this);
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(DetailViewModel.class);
        initView();
        initData();
        refreshLike();
    }


    private void refreshLike() {
        viewModel.getLikes().observe(activity, resource -> {
            if (resource != null) {
                switch (resource.getStatus()) {
                    case ERROR:
                        Toast.makeText(activity, "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS:
                        likeData = Objects.requireNonNull(resource.getData()).getResource();
                        commentAdapter.setLikeData(likeData);
                        break;
                }
            }
        });
    }

    private void initData() {
        // 刷新数据
        viewModel.getDetail(id).observe(activity, resource -> {
            if (resource != null) {
                switch (resource.getStatus()) {
                    case SUCCESS:
                        if (Objects.requireNonNull(resource.getData()).getState().equals("not_found")) {
                            Toast.makeText(activity, "内容已删除", Toast.LENGTH_SHORT).show();
                            activity.finish();
                            return;
                        }
                        Content data = Objects.requireNonNull(resource.getData()).getResource();
                        if (data != null) {
                            binding.detailContent.setModel(data);
                            tagAdapter.setData(data.tags);
                            binding.detailContent.contentTime.setText(getTime(data.getPublishDate()));
                            setStatus(ListStatus.StatusType.Done);
                        } else {
                            setStatus(ListStatus.StatusType.Nothing);
                        }
                        break;
                    case ERROR:
                        setStatus(ListStatus.StatusType.Error);
                        break;
                }
            }
        });

        viewModel.getComments(id).observe(activity, resource -> {
            if (resource != null) {
                switch (resource.getStatus()) {
                    case SUCCESS:
                        List<CommentsResource.CommentForContent> content = Objects.requireNonNull(resource.getData()).getResource();
                        if (content != null) {
                            commentAdapter.setData(content);
                            binding.commentNothing.setVisibility(View.GONE);
                            binding.commentList.setVisibility(View.VISIBLE);
                        } else {
                            binding.commentNothing.setVisibility(View.VISIBLE);
                            binding.commentList.setVisibility(View.GONE);
                        }
                }
            }
        });
    }

    private void deleteComment(String id) {
        viewModel.deleteComment(id).observe(activity, res -> {
            if (res != null) {
                switch (res.getStatus()) {
                    case ERROR:
                        Toast.makeText(activity, "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS:
                        initData();
                        break;
                }
            }
        });
    }


    private void like(String id, LikeService.LikeType type) {
        viewModel.like(id, type).observe(activity, res -> {
            if (res != null) {
                switch (res.getStatus()) {
                    case ERROR:
                        Toast.makeText(activity, "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS:
                        initData();
                        refreshLike();
                        break;
                }
            }
        });
    }


    private void unlike(String id, LikeService.LikeType type) {
        viewModel.unlike(id, type).observe(activity, res -> {
            if (res != null) {
                switch (res.getStatus()) {
                    case ERROR:
                        Toast.makeText(activity, "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS:
                        initData();
                        refreshLike();
                        break;
                }
            }
        });
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initView() {
        Objects.requireNonNull(activity.getSupportActionBar()).setTitle("内容详情");

        binding.detailStatus.statusError.setOnClickListener(v -> initData());

        // 评论列表
        binding.commentList.setLayoutManager(new LinearLayoutManager(activity));
        commentAdapter = new CommentAdapter(activity);
        commentAdapter.setOnClickComment(new CommentAdapter.OnClickComment() {
            @Override
            public void onClickDelete(String id) {
                deleteComment(id);
            }

            @Override
            public void onClickLike(String id) {
                if (likeData.contains(id)) {
                    unlike(id, LikeService.LikeType.Comment);
                } else {
                    like(id, LikeService.LikeType.Comment);
                }
            }

            @Override
            public void onClickReply(String fatherId, String fatherName, String contentId) {
                DetailHandler.this.fatherId = fatherId;
                DetailHandler.this.fatherName = fatherName;
                DetailHandler.this.contentId = contentId;
                DetailHandler.this.isReply = false;
                onClickAddComment(null);
            }
        });
        commentAdapter.setOnClickReply(new ReplyAdapter.OnClickReply() {
            @Override
            public void onClickLike(String id) {
                if (likeData.contains(id)) {
                    unlike(id, LikeService.LikeType.Reply);
                } else {
                    like(id, LikeService.LikeType.Reply);
                }
            }

            @Override
            public void onClickReply(String fatherId, String fatherName, String contentId) {
                DetailHandler.this.fatherId = fatherId;
                DetailHandler.this.fatherName = fatherName;
                DetailHandler.this.contentId = contentId;
                DetailHandler.this.isReply = true;
                onClickAddComment(null);
            }

            @Override
            public void onClickDelete(String id) {
                deleteComment(id);
            }
        });
        binding.commentList.setAdapter(commentAdapter);


        // 底部栏
        binding.addTitle.setOnClickListener(this::onClickAddComment);

        // 详情卡片
        binding.detailContent.btnComment.setOnClickListener(this::onClickAddComment);
        binding.detailContent.btnEdit.setVisibility(View.VISIBLE);
        binding.detailContent.btnRemove.setVisibility(View.VISIBLE);
        binding.detailContent.userAvatar.setOnClickListener(this::onClickUser);
        binding.detailContent.userName.setOnClickListener(this::onClickUser);
        binding.detailContent.btnRemove.setOnClickListener(this::onClickDelete);
        binding.detailContent.btnEdit.setOnClickListener(this::onClickEdit);
        binding.detailContent.btnLike.setOnClickListener(this::onClickContentLike);

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

    @SuppressLint("SetTextI18n")
    public void onClickAddComment(View v) {
        commentFragment = new CommentFragment((binding) -> {
            this.fragmentBinding = binding;
            if (isReply) {
                binding.commentFather.setText("回复: @" + fatherName);
            } else {
                binding.commentFather.setText("评论: @" + fatherName);
            }
            binding.btnAddComment.setOnClickListener(view -> {
                String content = binding.editComment.getText().toString();
                if (content.equals("")) {
                    Toast.makeText(activity, "评论不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                viewModel.addComment(contentId, fatherId, content, isReply).observe(activity, res -> {
                    if (res != null) {
                        switch (res.getStatus()){
                            case SUCCESS:
                                initData();
                                break;
                            case ERROR:
                                Toast.makeText(activity, "网络错误", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            });
            (new Handler()).postDelayed(() -> {
                binding.editComment.setFocusable(true);
                binding.editComment.requestFocus();
                InputMethodManager inputManager = (InputMethodManager) binding.editComment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(inputManager).showSoftInput(binding.editComment, 0);
            }, 333);
        });
        commentFragment.show(activity.getSupportFragmentManager(), "addComment");
    }

    public void onClickEdit(View v) {
        activity.startActivity(new Intent(activity, AddActivity.class));
    }

    public void onClickDelete(View v) {
        Toast.makeText(activity, "Delete", Toast.LENGTH_SHORT).show();
    }

    public void onClickContentLike(View v) {
        Toast.makeText(activity, "Like content", Toast.LENGTH_SHORT).show();
    }

    private void setStatus(ListStatus.StatusType status) {
        ListStatus.setStatus(binding.detailStatus, status);
        binding.detailScrollView.setVisibility(status == ListStatus.StatusType.Done ? View.VISIBLE : View.GONE);
        binding.commentList.setVisibility(status == ListStatus.StatusType.Done ? View.VISIBLE : View.GONE);
    }
}
