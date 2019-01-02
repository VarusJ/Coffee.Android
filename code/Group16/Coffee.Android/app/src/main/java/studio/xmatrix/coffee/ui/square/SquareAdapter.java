package studio.xmatrix.coffee.ui.square;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.data.model.Content;
import studio.xmatrix.coffee.databinding.ContentCardItemBinding;
import studio.xmatrix.coffee.ui.detail.DetailActivity;
import studio.xmatrix.coffee.ui.user.UserActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static studio.xmatrix.coffee.ui.detail.DetailHandler.getTime;

public class SquareAdapter extends RecyclerView.Adapter<SquareAdapter.ViewHolder> {
    private FragmentActivity activity;
    private List<Content> data;
    private List<String> likeData;
    private OnClickLike onClickLike;

    public void setOnClickLike(OnClickLike onClickLike) {
        this.onClickLike = onClickLike;
    }

    public interface OnClickLike{
        void onClick(String id);
    }

    SquareAdapter(FragmentActivity activity) {
        this.activity = activity;
        this.data = new ArrayList<>();
        this.likeData = new ArrayList<>();
    }

    public void setData(List<Content> data) {
        if (data == null) {
            this.data.clear();
        } else {
            this.data = data;
        }
        notifyDataSetChanged();
    }

    public void addData(List<Content> data) {
        if (data == null) return;
        int len = this.data.size();
        this.data.addAll(data);
        notifyItemRangeInserted(len, data.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.content_card_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setLikeData(List<String> likeData) {
        if (likeData == null) {
            this.likeData.clear();
        } else {
            this.likeData = likeData;
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ContentCardItemBinding binding;
        private TagAdapter adapter;

        ViewHolder(ContentCardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
        void bind(int pos) {
            Content itemData = data.get(pos);

            // Tag列表
            if (adapter == null) {
                adapter = new TagAdapter(activity);
                ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(activity)
                        .setChildGravity(Gravity.TOP)
                        .setScrollingEnabled(true)
                        .setGravityResolver(position -> Gravity.START)
                        .setOrientation(ChipsLayoutManager.HORIZONTAL)
                        .build();
                binding.tagLayout.setLayoutManager(chipsLayoutManager);
                binding.tagLayout.addItemDecoration(new SpacingItemDecoration(10, 10));
                binding.contentText.setMaxLines(3);
            }
            binding.tagLayout.setAdapter(adapter);
            adapter.setData(itemData.tags);

            // 图片列表
//            ArrayList<ImageInfo> imageInfos = new ArrayList<>();
//            for (int i = 0; i < 2; i++) {
//                ImageInfo info = new ImageInfo();
//                info.setThumbnailUrl("test" + i);
//                info.setBigImageUrl("test2" + i);
//                imageInfos.add(info);
//            }
//            binding.nineGridImage.setAdapter(new NineGridViewClickAdapter(activity, imageInfos));

            // 点击事件
            binding.userAvatar.setOnClickListener(v -> gotoUser(itemData.getOwnId()));
            binding.userName.setOnClickListener(v -> gotoUser(itemData.getOwnId()));
            binding.btnComment.setOnClickListener(v -> gotoDetail(itemData.getId()));
            binding.cardLayout.setOnClickListener(v -> gotoDetail(itemData.getId()));
            binding.btnLike.setOnClickListener(v -> onClickLike.onClick(itemData.getId()));
            // 数据
            binding.contentTime.setText(getTime(itemData.getPublishDate()));
            binding.setModel(itemData);
            if (likeData.contains(itemData.getId())) {
                binding.btnLike.setImageResource(R.drawable.ic_like);
            } else {
                binding.btnLike.setImageResource(R.drawable.ic_like_none);
            }
        }

        void gotoDetail(String id) {
            DetailActivity.start(activity, id, ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity,
                            Pair.create(binding.contentCard, "contentCard"))
                    .toBundle());
        }

        void gotoUser(String id) {
            UserActivity.start(activity, id, ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity,
                            Pair.create(binding.userAvatar, "userAvatar"),
                            Pair.create(binding.userName, "userName"))
                    .toBundle());
        }
    }
}
