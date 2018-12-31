package studio.xmatrix.coffee.ui.add;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnCloseClickListener;

import java.util.ArrayList;

import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.AddTagItemBinding;

import static android.view.LayoutInflater.*;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    private AddActivity activity;
    private ArrayList<String> tagsList;

    public TagAdapter(AddActivity activity) {
        this.activity = activity;
        tagsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(from(activity), R.layout.add_tag_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(tagsList.get(i));
    }

    @Override
    public int getItemCount() {
        return tagsList.size();
    }

    public boolean addTag(String newtag) {
        if(tagsList.indexOf(newtag) != -1) return false;

        tagsList.add(newtag);
        notifyDataSetChanged();
        return true;
    }

    public void removeTag(String tagname) {
        tagsList.remove(tagname);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final AddTagItemBinding binding;

        public ViewHolder(AddTagItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String tagname) {
            Chip chip = binding.tag;
            chip.setChipText(tagname);
            chip.setOnCloseClickListener((View v) -> {
                removeTag(tagname);
            });
        }
    }
}
