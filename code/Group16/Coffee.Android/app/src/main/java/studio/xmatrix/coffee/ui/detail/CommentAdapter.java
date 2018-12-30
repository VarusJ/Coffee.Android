package studio.xmatrix.coffee.ui.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.CommentItemBinding;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private  DetailActivity activity;

    public CommentAdapter(DetailActivity activity) {
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.comment_item, viewGroup, false));
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
        private final CommentItemBinding binding;
        private ReplyAdapter adapter;

        ViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind() {
            if (adapter == null) {
                adapter = new ReplyAdapter(activity);
                binding.replyList.setAdapter(adapter);
                binding.replyList.setLayoutManager(new LinearLayoutManager(activity));
            }

        }
    }
}
