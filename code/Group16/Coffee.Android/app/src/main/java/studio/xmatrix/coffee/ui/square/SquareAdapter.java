package studio.xmatrix.coffee.ui.square;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.ContentCardItemBinding;
import studio.xmatrix.coffee.databinding.SquareFragmentBinding;
import studio.xmatrix.coffee.ui.detail.DetailActivity;
import studio.xmatrix.coffee.ui.user.UserActivity;

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

        ViewHolder(ContentCardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind() {
            binding.userAvatar.setOnClickListener(this);
            binding.userName.setOnClickListener(this);
            binding.cardLayout.setOnClickListener(this);
            // binding.setModel(i);
        }
        @Override
        public void onClick(View v) {
            if (v.getId() == binding.userAvatar.getId() || v.getId() == binding.userName.getId()) {
                activity.startActivity(new Intent(activity, UserActivity.class));
            } else if (v.getId() == binding.cardLayout.getId()) {
                activity.startActivity(new Intent(activity, DetailActivity.class));
            }
        }
    }
}
