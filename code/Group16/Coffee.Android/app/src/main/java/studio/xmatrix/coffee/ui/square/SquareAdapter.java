package studio.xmatrix.coffee.ui.square;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.ContentCardItemBinding;
import studio.xmatrix.coffee.databinding.SquareFragmentBinding;

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

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ContentCardItemBinding binding;

        ViewHolder(ContentCardItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind() {
            // binding.setModel(i);
        }
    }
}
