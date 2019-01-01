package studio.xmatrix.coffee.ui.notice;

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
import com.daimajia.swipe.SwipeLayout;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.NoticeItemBinding;
import studio.xmatrix.coffee.ui.detail.DetailActivity;
import studio.xmatrix.coffee.ui.user.UserActivity;

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
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final NoticeItemBinding binding;
        private Badge badge;

        ViewHolder(NoticeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind() {
            if (badge == null) {
                badge = new QBadgeView(activity)
                        .bindTarget(binding.noticeAction)
                        .setBadgeGravity(Gravity.END | Gravity.CENTER)
                        .setShowShadow(false)
                        .setBadgeText("New");
            }
            binding.noticeLayout.setOnClickListener(v -> {

                activity.startActivity(new Intent(activity, DetailActivity.class));
            });
            binding.noticeUserAvatar.setOnClickListener(this::goToUser);
            binding.noticeUserName.setOnClickListener(this::goToUser);

            binding.noticeSwipe.setShowMode(SwipeLayout.ShowMode.PullOut);
            binding.noticeSwipe.addDrag(SwipeLayout.DragEdge.Right, binding.removeLayout);


            badge.setOnDragStateChangedListener((dragState, badge, targetView) -> {
                // todo
            });
            // badge.hide(true);
            // binding.setModel(i);
        }

        public void goToUser(View v) {

            Intent intent = new Intent(activity, UserActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity,
                            Pair.create(binding.noticeUserAvatar, "userAvatar"),
                            Pair.create(binding.noticeUserName, "userName"));
            activity.startActivity(intent, options.toBundle());
        }
    }
}
