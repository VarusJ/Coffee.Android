package studio.xmatrix.coffee.ui.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.CommentItemBinding;
import studio.xmatrix.coffee.databinding.ReplyItemBinding;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {
    private  DetailActivity activity;

    public ReplyAdapter(DetailActivity activity) {
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.reply_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ReplyItemBinding binding;

        ViewHolder(ReplyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int pos) {
            if (pos == getItemCount() - 1) {
                binding.replyDivider.setVisibility(View.GONE);
            }
        }
    }
}

