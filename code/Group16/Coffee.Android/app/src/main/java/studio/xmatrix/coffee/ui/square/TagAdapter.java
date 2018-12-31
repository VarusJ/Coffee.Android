package studio.xmatrix.coffee.ui.square;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.TagItemBinding;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    private FragmentActivity activity;

    public TagAdapter(FragmentActivity activity) {
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.tag_item, viewGroup, false));
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
        private final TagItemBinding binding;

        ViewHolder(TagItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind() {

        }

    }
}
