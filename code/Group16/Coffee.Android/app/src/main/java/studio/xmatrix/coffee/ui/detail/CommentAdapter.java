package studio.xmatrix.coffee.ui.detail;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.data.service.resource.CommentsResource;
import studio.xmatrix.coffee.databinding.CommentItemBinding;

import java.util.ArrayList;
import java.util.List;

import static studio.xmatrix.coffee.ui.detail.DetailHandler.getTime;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private DetailActivity activity;
    private List<CommentsResource.CommentForContent> data;
    private List<String> likeData;
    private OnClickComment onClickComment;
    private ReplyAdapter.OnClickReply onClickReply;

    public void setOnClickComment(OnClickComment onClickComment) {
        this.onClickComment = onClickComment;
    }

    public void setOnClickReply(ReplyAdapter.OnClickReply onClickReply) {
        this.onClickReply = onClickReply;
    }

    public void setLikeData(List<String> likeData) {
        if (likeData == null) return;
        this.likeData = likeData;
        notifyDataSetChanged();
    }

    public interface OnClickComment {
        void onClickDelete(String id);

        void onClickLike(String id);

        void onClickReply(String fatherId, String fatherName, String contentId);
    }

    public CommentAdapter(DetailActivity activity) {
        this.activity = activity;
        data = new ArrayList<>();
        this.likeData = new ArrayList<>();
    }

    public void setData(List<CommentsResource.CommentForContent> data) {
        if (data == null) return;
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.comment_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final CommentItemBinding binding;
        private ReplyAdapter adapter;

        ViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int pos) {
            if (adapter == null) {
                adapter = new ReplyAdapter(activity);
                adapter.setOnClickReply(onClickReply);
                binding.replyList.setAdapter(adapter);
                binding.replyList.setLayoutManager(new LinearLayoutManager(activity));
            }
            CommentsResource.CommentForContent itemData = data.get(pos);
            binding.commentLike.setOnClickListener(v-> {
                onClickComment.onClickLike(itemData.getComment().getId());
            });
            binding.commentContentText.setOnClickListener(v -> {
                onClickComment.onClickReply(itemData.getComment().getUserId(), itemData.getUser().getName(), itemData.getComment().getId());
            });
            binding.commentDelete.setOnClickListener(v -> {
                onClickComment.onClickDelete(itemData.getComment().getId());
            });
            binding.setModel(itemData);
            adapter.setData(itemData.getReplies());
            binding.commentTime.setText(getTime(itemData.getComment().getDate()));
            if (likeData.contains(itemData.getComment().getId())) {
                binding.commentLike.setImageResource(R.drawable.ic_like);
            } else {
                binding.commentLike.setImageResource(R.drawable.ic_like_none);
            }
        }
    }
}
