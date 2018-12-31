package studio.xmatrix.coffee.ui.square;

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
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.ContentCardItemBinding;
import studio.xmatrix.coffee.ui.detail.DetailActivity;
import studio.xmatrix.coffee.ui.user.UserActivity;

import java.util.ArrayList;

public class SquareAdapter extends RecyclerView.Adapter<SquareAdapter.ViewHolder> {
    private FragmentActivity activity;

    public SquareAdapter(FragmentActivity activity) {
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.content_card_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind();
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ContentCardItemBinding binding;
        private TagAdapter adapter;

        ViewHolder(ContentCardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind() {
            if (adapter == null) {
                adapter = new TagAdapter(activity);
                binding.tagLayout.setAdapter(adapter);
                ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(activity)
                        .setChildGravity(Gravity.TOP)
                        .setScrollingEnabled(true)
                        .setGravityResolver(position -> Gravity.START)
                        .setOrientation(ChipsLayoutManager.HORIZONTAL)
                        .build();
                binding.tagLayout.setLayoutManager(chipsLayoutManager);
                binding.tagLayout.addItemDecoration(new SpacingItemDecoration(10, 10));
            }

            binding.userAvatar.setOnClickListener(this);
            binding.userName.setOnClickListener(this);
            binding.cardLayout.setOnClickListener(this);
            ArrayList<ImageInfo> imageInfos = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl("test" + i);
                info.setBigImageUrl("test2" + i);
                imageInfos.add(info);
            }
            binding.nineGridImage.setAdapter(new NineGridViewClickAdapter(activity, imageInfos));
            // binding.setModel(i);
        }
        @Override
        public void onClick(View v) {
            if (v.getId() == binding.userAvatar.getId() || v.getId() == binding.userName.getId()) {
                Intent intent = new Intent(activity, UserActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity,
                                Pair.create(binding.userAvatar, "userAvatar"),
                                Pair.create(binding.userName, "userName"));
                activity.startActivity(intent, options.toBundle());
            } else if (v.getId() == binding.cardLayout.getId()) {
                Intent intent = new Intent(activity, DetailActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity,
                                Pair.create(binding.contentCard, "contentCard"));
                activity.startActivity(intent, options.toBundle());
            }
        }
    }
}
