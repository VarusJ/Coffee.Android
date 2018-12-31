package studio.xmatrix.coffee.ui.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.vipulasri.timelineview.TimelineView;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.HomeItemBinding;
import studio.xmatrix.coffee.ui.detail.DetailActivity;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private FragmentActivity activity;

    public HomeAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind();
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.home_item, parent, false);
        return new ViewHolder(binding, viewType);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TimelineView mTimelineView;
        HomeItemBinding binding;

        ViewHolder(HomeItemBinding binding, int viewType) {
            super(binding.getRoot());
            mTimelineView = binding.timeline;
            this.binding = binding;
            mTimelineView.initLine(viewType);
            if (viewType == 1 || viewType == 3) {
                binding.homeFather.setPadding(0, 30, 0, 0);
            }
        }

        public void bind() {
            binding.homeClickLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == binding.homeClickLayout.getId()) {
                activity.startActivity(new Intent(activity, DetailActivity.class));
            }
        }
    }
}
