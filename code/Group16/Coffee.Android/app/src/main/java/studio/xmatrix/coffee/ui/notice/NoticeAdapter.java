package studio.xmatrix.coffee.ui.notice;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.NoticeItemBinding;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
    private FragmentActivity activity;

    public NoticeAdapter(FragmentActivity activity) {
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.notice_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind();
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final NoticeItemBinding binding;

        ViewHolder(NoticeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind() {
            new QBadgeView(activity)
                    .bindTarget(binding.noticeRemove)
                    .setBadgeGravity(Gravity.CENTER)
                    .setBadgeText("New")
                    .setOnDragStateChangedListener((dragState, badge, targetView) -> {
                // todo
            });
            // binding.setModel(i);
        }
    }
}
