package studio.xmatrix.coffee.ui.add;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.robertlevonyan.views.chip.Chip;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import studio.xmatrix.coffee.R;
import studio.xmatrix.coffee.databinding.AddPicItemBinding;

import static android.view.LayoutInflater.from;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    private AddActivity activity;
    private ArrayList<String> pathList;
    private RecyclerView recyclerView;

    public PictureAdapter(AddActivity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        pathList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(DataBindingUtil.inflate(from(activity), R.layout.add_pic_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try {
            viewHolder.bind(pathList.get(i));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return pathList.size();
    }
    
    public void addPics(List<LocalMedia> list) {
        for (LocalMedia localMedia: list) {
            pathList.add(localMedia.getPath());
        }
        notifyDataSetChanged();
        recyclerView.scrollToPosition(pathList.size() - 1);
    }

    public void removePic(String path) {
        pathList.remove(path);
        notifyDataSetChanged();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        private final AddPicItemBinding binding;

        public ViewHolder(AddPicItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String path) throws IOException {
            Bitmap bmp = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.fromFile(new File(path)));
            bmp = Bitmap.createScaledBitmap(bmp, binding.pic.getLayoutParams().width, binding.pic.getLayoutParams().height, false);
            binding.pic.setImageBitmap(bmp);
            binding.pic.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    removePic(path);
                    return true;
                }
            });
        }
    }
}
